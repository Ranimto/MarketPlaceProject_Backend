package com.example.Market.Services;

import com.example.Market.ApplicationURL;
import com.example.Market.Dto.*;
import com.example.Market.Exceptions.EntityNotFoundException;
import com.example.Market.Exceptions.ErrorsCode;
import com.example.Market.Model.*;
import com.example.Market.Repository.CommandeRepo;
import com.example.Market.Repository.LigneCommandeRepo;
import com.example.Market.Repository.ProductRepo;
import com.example.Market.Repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.util.Strftime;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.*;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j  //for log the error or any message...
@Service
public class CommandeService {
  private final CommandeRepo commandeRepo;
  private final ModelMapper modelMapper;
 private final  ProductService productService ;
private final UserService userService;
private final  UserRepo userRepo;
 private final RestTemplate restTemplate;
 private final LigneCommandeRepo ligneCommandeRepo ;
 private final LigneCommandeService ligneCommandeService ;
 private final ProductRepo productRepo;


    public Optional<CommandeDto> findCommandeById(Long id) {
        if (id==0 ){log.error("l'id est null");}
        Optional<Commande> commande = commandeRepo.findById(id);
        return Optional.ofNullable(commande.map(u -> modelMapper.map(u, CommandeDto.class)).orElseThrow(() -> new EntityNotFoundException("commande " + id + "not found", ErrorsCode.COMMANDE_NOT_FOUND)));
    }

   public List<ProductDto> getListProductsByPartener(PartenerName partenerName){
       RestTemplate restTemplate = new RestTemplate();
       String url = ApplicationURL.APP_URL+ "/ProductClient/allProductsByPartenerName/"+partenerName;
       ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(url, ProductDto[].class);
       ProductDto[] productDtosPartener = response.getBody();
         return  Arrays.asList(productDtosPartener);
   }

   public Boolean getProductVerificationFromSPartener(PartenerName partenerName , String codeBar , int Qtecmd){
       String url = ApplicationURL.APP_URL+ "/ProductClient/ProductVerification/"+partenerName +"/"+ codeBar+"/"+ Qtecmd ;
       ResponseEntity<Boolean> verif = restTemplate.exchange(url, HttpMethod.GET, null, Boolean.class);
       log.error("Verification done !!"+verif.getBody());
      return verif.getBody();
  }

   public CommandeDto UpdateCommande (CommandeDto commandeDto , UserDto userDto){
       commandeDto.setUser(userDto);
       commandeDto.setDate(new Date());
       commandeDto.setStatus(CommandeStatus.PENDING);
       Commande commande = modelMapper.map(commandeDto, Commande.class);
       Commande savedCommande = commandeRepo.save(commande);
       return modelMapper.map(savedCommande, CommandeDto.class);
   }
   //retourner un HashMap <Partener, liste de LC >
    public static Map<PartenerName, List<LigneCommande>> LignesCommandeByPartenerName(List<LigneCommande> ligneCommandes) {
        Map<PartenerName, List<LigneCommande>> lignesCommandeParPartenaire = new HashMap<>();

        for (LigneCommande ligneCommande : ligneCommandes) {
            PartenerName partenerName = ligneCommande.getProduct().getPartenerName();
            List<LigneCommande> lignesCommande = lignesCommandeParPartenaire.getOrDefault(partenerName, new ArrayList<>());
            lignesCommande.add(ligneCommande);
            lignesCommandeParPartenaire.put(partenerName, lignesCommande);
        }
        return lignesCommandeParPartenaire;
    }


    public  void decrementProductsQuantityByPartener(List<ProductClientCommandedDto> ProductsCommanded ,PartenerName partenerName){
        String url = ApplicationURL.APP_URL+"/Stock/decrementQuantity/"+partenerName;
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<List<ProductClientCommandedDto>> requestEntity = new HttpEntity<>(ProductsCommanded, headers);
        ResponseEntity<Boolean> response = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Boolean.class);
        Boolean isSuccess = response.getBody();
    }


    public CommandeDto adddCommande(List<ProductCmd> productCmds, Long userId) {

            int V = 1;
            Commande commande1 = new Commande();

            commandeRepo.save(commande1);
           Long commandeId =commande1.getId();

            for (ProductCmd productCmd :productCmds) {
                //Long ProductId = productCmd.getProductId();
                String codeBar= productCmd.getCodeBar();
                int QteCmd = productCmd.getQteCommanded();

               // Optional<ProductDto> product1= productService.findProductById(ProductId);
                Optional<ProductDto> product1= productService.findProductByCodeBar(codeBar);

                Optional<Product> product = product1.map(p->modelMapper.map(p,Product.class));
                product.get().setId(product1.get().getId());  //productId --> product1Id
                productRepo.save(product.get());  //save product in database local

                Boolean Verification = getProductVerificationFromSPartener(product.get().getPartenerName(), codeBar, QteCmd);
                if (!Verification) {
                    V = 0;
                    break;
                }
                else {
                    V=1;
                    LigneCommande ligneCommande = new LigneCommande();

                    ligneCommande.setId(new LigneCommandeId(product1.get().getId(), commandeId));
                    ligneCommande.setQuantityCommanded(QteCmd);
                    ligneCommande.setProduct(product.get()); // Utilisez l'instance de Product récupérée précédemment
                    ligneCommande.setCodeBar(codeBar);
                    ligneCommande.setCommande(commande1);
                    LigneCommande ligneCommande1 = ligneCommandeRepo.save(ligneCommande);
                    commande1.getLignesCommande().add(modelMapper.map(ligneCommande1,LigneCommande.class));
               }
            }
            if (V == 0)
                commande1.setCommandeStatus(CommandeStatus.PENDING);
            else
            {
                commande1.setCommandeStatus(CommandeStatus.VALIDATED);
              //decrementation
            List<LigneCommande>  ligneCommandes=commande1.getLignesCommande();
            Map<PartenerName, List<LigneCommande>> ligneCommandeByPartener = LignesCommandeByPartenerName(ligneCommandes); //retourner un HashMap <Partener, liste de LC >
            for (Map.Entry<PartenerName, List<LigneCommande>> entry : ligneCommandeByPartener.entrySet()) { // parcourir le HashMap
                PartenerName partenerName = entry.getKey();  // partenerName
                log.error(""+partenerName);
                List<LigneCommande> lignesCommande = entry.getValue(); // lignesCommande

                List<LigneCommandeDto> ligneCommandeDtos=lignesCommande.stream().map(l -> modelMapper.map(l, LigneCommandeDto.class)).collect(Collectors.toList());

                List<ProductClientCommandedDto> productsClientCommanded= ligneCommandeService.transferToProductClientCommandes(ligneCommandeDtos);

                decrementProductsQuantityByPartener(productsClientCommanded , partenerName);
            }
           }
            User user = userRepo.findById(userId).orElse(null);
            commande1.setUser(user);
            commande1.setDate(new Date());
            Commande commande= commandeRepo.save(commande1);
         return modelMapper.map(commande , CommandeDto.class);
        }


  public List<CommandeDto> findAllCommande() {
    List<Commande> commandes = commandeRepo.findAll();
    return commandes.stream().map(c -> modelMapper.map(c, CommandeDto.class)).collect(Collectors.toList());
  }

  public CommandeDto UpdateCommande(CommandeDto commandeDto) {
    Commande commande = modelMapper.map(commandeDto, Commande.class);
    Commande savedCommande = commandeRepo.save(commande);
    return modelMapper.map(savedCommande, CommandeDto.class);
  }

  public void DeleteCommandeById(long id) {
    if (id == 0) {
      log.error("l'id est null");
    }
    commandeRepo.deleteCommandeById(id);
  }

  public boolean isCommandeValid(Long id) {
    Optional<Commande> commande=commandeRepo.findCommandeById(id);
    CommandeStatus status = commande.get().getCommandeStatus();
    return status == CommandeStatus.VALIDATED;
  }

}