package com.example.Market.Services;

import com.example.Market.ApplicationURL;
import com.example.Market.Dto.ProductDto;
import com.example.Market.Exceptions.EntityNotFoundException;
import com.example.Market.Exceptions.ErrorsCode;
import com.example.Market.Model.Product;
import com.example.Market.Repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.Arrays;
import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@Slf4j
@Service
public class ProductService {
 private  final ProductRepo productRepo ;
 private final ModelMapper modelMapper ;
 private  final RestTemplate restTemplate;


    public Optional<ProductDto> findProductById(long idProduct){
        if (idProduct==0 ){log.error("l'id est null");}
        RestTemplate restTemplate = new RestTemplate();
        String url = ApplicationURL.APP_URL+ "/ProductClient/findProduct/" + idProduct;
        ResponseEntity<ProductDto> response = restTemplate.exchange(url, HttpMethod.GET, null, ProductDto.class);
        ProductDto productDto = response.getBody();

        return Optional.ofNullable(Optional.ofNullable(productDto).orElseThrow(() -> new EntityNotFoundException("product " + idProduct + "not found", ErrorsCode.PRODUCT_NOT_FOUND)));
    }

    public Optional<ProductDto> findProductByCodeBar(String codeBar){
        if (codeBar==null){log.error("codeBar is null");}
        RestTemplate restTemplate = new RestTemplate();
        String url = ApplicationURL.APP_URL+ "/ProductClient/findProductByCodeBar/" + codeBar;
        ResponseEntity<ProductDto> response = restTemplate.exchange(url, HttpMethod.GET, null, ProductDto.class);
        ProductDto productDto = response.getBody();
        return Optional.ofNullable(Optional.ofNullable(productDto).orElseThrow(() -> new EntityNotFoundException("product " + codeBar  + "not found", ErrorsCode.PRODUCT_NOT_FOUND)));
    }
    public List<ProductDto> findAllProduct(){
        RestTemplate restTemplate = new RestTemplate();
        String url = ApplicationURL.APP_URL+ "/ProductClient/allProducts";
        ResponseEntity<ProductDto[]> response = restTemplate.getForEntity(url, ProductDto[].class);
        ProductDto[] productDtos = response.getBody();

        return Arrays.asList(productDtos);
    }


    public ProductDto addProduct (ProductDto productDto){
        Product product=modelMapper.map(productDto,Product.class);
        Product savedProduct= productRepo.save(product);
        return modelMapper.map(savedProduct,ProductDto.class);
    }


    public ProductDto UpdateProduct(ProductDto productDto){
        Product product= modelMapper.map(productDto,Product.class);
        product.setDesignation(productDto.getDesignation());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());
        product.setImageUrl(productDto.getImageUrl());
        product.setPartenerName(productDto.getPartenerName());
        Product UpdateProduct=productRepo.save(product);
        return modelMapper.map(UpdateProduct,ProductDto.class);
    }
    public void DeleteById(long id){
        if (id==0 ){log.error("l'id est null");}
        productRepo.deleteProductById(id);}

    public ProductDto getProductInfo(Long id)
    {
        Product product1 = productRepo.findById(id).get();
        ProductDto productDto = modelMapper.map(product1 , ProductDto.class);

        return productDto;

    }
    //*** method isDispo() -- call method in the other app
    public boolean isProductAvailable(Long productId) {
        String url = ApplicationURL.APP_URL+ "ProductClient/isAvailable/" + productId;
        ResponseEntity<Boolean> response = restTemplate.getForEntity(url, Boolean.class);
        return response.getBody();
    }


}
