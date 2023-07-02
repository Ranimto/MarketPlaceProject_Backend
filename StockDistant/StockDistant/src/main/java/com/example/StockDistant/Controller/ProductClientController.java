package com.example.StockDistant.Controller;

import com.example.StockDistant.Dto.ProductClientDto;

import com.example.StockDistant.Model.PartenerName;

import com.example.StockDistant.Services.ProductClientService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RequiredArgsConstructor
@RestController
@RequestMapping("/ProductClient")
public class ProductClientController {
    private final ProductClientService productClientService ;


    @GetMapping("/findProduct/{id}")
    public ResponseEntity<ProductClientDto> findProductById(@PathVariable("id") Long id){
       Optional<ProductClientDto> product= productClientService.findProductClientById(id);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }}

    @GetMapping("/findProductByCodeBar/{codeBar}")
    public ResponseEntity<ProductClientDto> findProductByCodeBar(@PathVariable("codeBar") String codeBar){
        Optional<ProductClientDto> product= productClientService.findProductClientByCodeBar(codeBar);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }}


    @GetMapping("/allProducts")
    public  ResponseEntity<List<ProductClientDto>> findAllProducts(){
        List<ProductClientDto> products = productClientService.findAllProductClient();
        return new ResponseEntity<>(products ,HttpStatus.OK);
    }

    @GetMapping("/allProductsByPartenerName/{partenerName}")
    public  ResponseEntity<List<ProductClientDto>> findAllProductsByPartenerName(@PathVariable("partenerName") PartenerName partenerName){
        List<ProductClientDto> products = productClientService.findAllProductsByPartenerName(partenerName);
        return new ResponseEntity<>(products ,HttpStatus.OK);
    }

    @GetMapping("/ProductVerification/{partenerName}/{codeBar}/{QteCmd}")
    public  ResponseEntity<Boolean> getProductVerification(@PathVariable("partenerName")PartenerName partenerName ,@PathVariable("codeBar") String codeBar, @PathVariable("QteCmd")int QteCmd){
        return new ResponseEntity<Boolean>(productClientService.isProductVerified(partenerName,codeBar,QteCmd),HttpStatus.OK);
    }

    @GetMapping("/isAvailable/{codeBar}")
    public  ResponseEntity<Boolean> IsAvailable(@PathVariable("codeBar") String codeBar){
        return new ResponseEntity<Boolean>(productClientService.isAvailable(codeBar),HttpStatus.OK);
    }

    @PostMapping("/add")
    public  ResponseEntity<ProductClientDto> addProduct(@RequestBody ProductClientDto productDto, HttpServletResponse response){
        ProductClientDto newProduct =productClientService.addProduct(productDto)  ;
        return new ResponseEntity<>(newProduct,HttpStatus.CREATED );
    }


    }

