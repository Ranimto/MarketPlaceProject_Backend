package com.example.Market.Services;
import com.example.Market.Dto.CommandeDto;
import com.example.Market.Dto.LigneCommandeDto;
import com.example.Market.Dto.ProductClientCommandedDto;
import com.example.Market.Model.LigneCommande;
import com.example.Market.Model.LigneCommandeId;
import com.example.Market.Repository.LigneCommandeRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@RequiredArgsConstructor
@Slf4j
@Service
public class LigneCommandeService {

    private  final LigneCommandeRepo ligneCommandeRepo ;
    private final ModelMapper modelMapper;

    public List<LigneCommandeDto> findAllLigneCommande(){
       List <LigneCommande> ligneCommandes= ligneCommandeRepo.findAll();
       return ligneCommandes.stream().map(l->modelMapper.map(l,LigneCommandeDto.class)).collect(Collectors.toList());

    }

    public LigneCommandeDto saveLigneCommande(LigneCommandeDto ligneCommandeDto) {
        LigneCommande ligneCommande = modelMapper.map(ligneCommandeDto, LigneCommande.class);
        LigneCommande savedLigneCommande = ligneCommandeRepo.save(ligneCommande);
        return modelMapper.map(savedLigneCommande, LigneCommandeDto.class);
    }


    public  List<ProductClientCommandedDto> transferToProductClientCommandes(List<LigneCommandeDto> ligneCommandes) {
        return ligneCommandes.stream()
                .map(ligneCommande -> {
                    ProductClientCommandedDto productClientCommande = new ProductClientCommandedDto();
                    productClientCommande.setCodeBar(ligneCommande.getCodeBar());
                    productClientCommande.setQuantityCommanded(ligneCommande.getQuantityCommanded());
                    return productClientCommande;
                })
                .collect(Collectors.toList());
    }



}
