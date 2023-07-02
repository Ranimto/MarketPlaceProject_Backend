package com.example.Market.Controller;

import com.example.Market.Dto.ProductDto;
import com.example.Market.Services.ProductService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product")
public class ProductController {
    private  final ProductService productService ;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/findProduct/{id}")
    public ResponseEntity<ProductDto> findProduct(@PathVariable("id") Long id){
        Optional<ProductDto> product=productService.findProductById(id);
        //orElse
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/findProductByCodeBar/{codeBar}")
    public ResponseEntity<ProductDto> findProductByCodeBar(@PathVariable("codeBar") String codeBar){
        Optional<ProductDto> product=productService.findProductByCodeBar(codeBar);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/allProducts")
    public  ResponseEntity<List<ProductDto>> FindAllProduct(){
        List<ProductDto> products = productService.findAllProduct();
        return new ResponseEntity<>(products ,HttpStatus.OK);
    }
    @PostMapping("/add")
    public  ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto, HttpServletResponse response){
        ProductDto newProduct =productService.addProduct(productDto)  ;
        return new ResponseEntity<>(newProduct,HttpStatus.CREATED );
    }

    @PutMapping("/Update")
    public  ResponseEntity<ProductDto> UpdateProduct(@RequestBody ProductDto productDto, HttpServletResponse response){
        ProductDto UpdateProduct =productService.UpdateProduct(productDto)  ;
        return new ResponseEntity<>(UpdateProduct,HttpStatus.CREATED );
    }
    @DeleteMapping("/delete/{id}")
   public ResponseEntity<?> DeleteProduct(@PathVariable("id") Long id){
        productService.DeleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //InfoProd
    @GetMapping("/InfosProduct/{idproduct}")
    public ResponseEntity<ProductDto> GetInfoProduct(@PathVariable("idproduct") Long idproduct){

        return  ResponseEntity.ok(productService.getProductInfo(idproduct));
    }

}
