package com.example.StockDistant.Controller;
import com.example.StockDistant.Dto.ProductClientCommandedDto;
import com.example.StockDistant.Dto.ProductClientDto;
import com.example.StockDistant.Dto.StockDto;
import com.example.StockDistant.Model.PartenerName;
import com.example.StockDistant.Model.Stock;
import com.example.StockDistant.Services.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor  // POUR LES DECLARATIONS
@RestController
@RequestMapping("/Stock")
public class StockController {
    private final StockService stockService ;


    @GetMapping("/AllProductsInStock/{id}")
   public ResponseEntity<List<ProductClientDto>> GetAllProductsByStockId( @PathVariable ("id") Long id){
        List<ProductClientDto> products= stockService.findAllProductsByStockId(id);
        return new ResponseEntity<>(products, HttpStatus.OK);
   }


   @GetMapping("/stock/{id}")
    public ResponseEntity<StockDto> GetStockById (@PathVariable("id") Long id ){
       Optional<StockDto> stock=stockService.findStockById(id);
       if (stock.isPresent()) {
           return new ResponseEntity<>(stock.get(), HttpStatus.OK);
       } else {
           return new ResponseEntity<>(HttpStatus.NOT_FOUND);
       }
   }

    @GetMapping("/{partenerName}")
    public ResponseEntity<Stock> GetStockByPartenerName (@PathVariable("partenerName") PartenerName partenerName){
        Stock stock=stockService.getStockByName(partenerName);
        return new ResponseEntity<>(stock, HttpStatus.OK);
    }

    @PutMapping("/decrementQuantity/{partenerName}")
    public  ResponseEntity<?> decrementQuantityInStock(@RequestBody List<ProductClientCommandedDto> ProductsCommanded ,@PathVariable("partenerName")PartenerName partenerName){
        stockService.decrementProductsQuantity(ProductsCommanded,partenerName);
        return new ResponseEntity<>(HttpStatus.OK) ;
    }



}
