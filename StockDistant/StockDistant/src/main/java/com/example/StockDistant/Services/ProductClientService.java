package com.example.StockDistant.Services;

import com.example.StockDistant.Dto.ProductClientDto;
import com.example.StockDistant.Model.Availability;
import com.example.StockDistant.Model.PartenerName;
import com.example.StockDistant.Model.ProductClient;
import com.example.StockDistant.Repository.ProductClientRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
public class ProductClientService {
    private  final ProductClientRepo productClientRepo ;
    private final ModelMapper modelMapper ;

    public Optional<ProductClientDto> findProductClientById(long id){
        if (id==0 ){log.error("productId is null");}
        Optional<ProductClient> product= productClientRepo.findById(id);
        return product.map(u->modelMapper.map(u,ProductClientDto.class));
    }

    public Optional<ProductClientDto> findProductClientByCodeBar(String codeBar){
        if (codeBar==null ){log.error("Product codeBar is null");}
        Optional<ProductClient> product= productClientRepo.findByCodeBar(codeBar);
        return product.map(u->modelMapper.map(u,ProductClientDto.class));
    }

    public List<ProductClientDto> findAllProductClient(){
        List<ProductClient> products= productClientRepo.findAll() ;
        return products.stream().map(p->modelMapper.map(p,ProductClientDto.class)).collect(Collectors.toList()) ;

    }

    public  List<ProductClientDto> findAllProductsByPartenerName(PartenerName partenerName){
        List<ProductClient> products= productClientRepo.findAllByPartenerName(partenerName) ;
        return products.stream().map(p->modelMapper.map(p,ProductClientDto.class)).collect(Collectors.toList()) ;
    }
    public Boolean isAvailable(String codeBar){
        if (codeBar==null) return null ;
        Optional<ProductClient> productClient= productClientRepo.findByCodeBar(codeBar);
        if (productClient.isPresent()) {
                  if ( productClient.get().getStatus()== Availability.AVAILABLE) return true;
                  else return false ;
        } else {
            return null;
        }
    }

    public ProductClientDto addProduct (ProductClientDto productDto){
        ProductClient product=modelMapper.map(productDto,ProductClient.class);
        ProductClient savedProduct= productClientRepo.save(product);
        return modelMapper.map(savedProduct,ProductClientDto.class);
    }

    public ProductClientDto UpdatedProductQuantity(Optional<ProductClientDto> productClientDto, int qte){

     productClientDto.get().setQuantity(qte);
        ProductClient productClient= productClientDto.map(p->modelMapper.map(p,ProductClient.class)).orElse(null);
        productClientRepo.save(productClient);
        return modelMapper.map(productClient,ProductClientDto.class);
    }

    public Boolean isProductVerified( PartenerName partenerName ,String codeBar  ,int QteCmd){

        Optional<ProductClientDto> product = findProductClientByCodeBar(codeBar);
        if (!product.isPresent()) {
            log.error("Product not Found in " + partenerName + " stock");
            return false;
        }
        else if (product.get().getQuantity() ==0 || !isAvailable(codeBar)) {
            log.error("Product " + codeBar + " is OUT-OF-STOCK");
            product.get().setStatus(Availability.OUT_OF_STOCK);
           Optional <ProductClient> productClient=product.map(p->modelMapper.map(p,ProductClient.class));
            ProductClient save = productClientRepo.save(productClient.get());
            return false;
        }
        else if  (product.get().getQuantity()-QteCmd< 0) {

            return false;
        }
        else if  (product.get().getQuantity()-QteCmd== 0) {
            product.get().setStatus(Availability.OUT_OF_STOCK);
            Optional <ProductClient> productClient=product.map(p->modelMapper.map(p,ProductClient.class));
            ProductClient save = productClientRepo.save(productClient.get());
            return true;
        }

        return true;
    }



    }


