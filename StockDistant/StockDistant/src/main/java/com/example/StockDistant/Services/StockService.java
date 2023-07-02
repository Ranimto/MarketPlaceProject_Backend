package com.example.StockDistant.Services;

import com.example.StockDistant.Dto.ProductClientCommandedDto;
import com.example.StockDistant.Dto.ProductClientDto;
import com.example.StockDistant.Dto.StockDto;
import com.example.StockDistant.Model.PartenerName;
import com.example.StockDistant.Model.Stock;
import com.example.StockDistant.Repository.ProductClientRepo;
import com.example.StockDistant.Repository.StockRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class StockService {
 private  final StockRepo stockRepo;
 private final ModelMapper modelMapper ;
 private  final RestTemplate restTemplate;
 private final ProductClientService productClientService;
 private  final ProductClientRepo productClientRepo;
 private String URL="http://localhost:8080/ligneCommande/TotalQuantityProduct/{idProduct}/";


    public List<ProductClientDto> findAllProductsByStockId(Long id ){
        if (id==0) return null ;
        Optional<Stock> stock=stockRepo.findById(id);
        StockDto stockDto=stock.map(s->modelMapper.map(s,StockDto.class)).orElse(null);
        List<ProductClientDto> productClientsDto=stockDto.getProductClients();
       return productClientsDto;
    }

    public  Optional<StockDto> findStockById (Long id) {
        Optional<Stock> stock= stockRepo.findById(id) ;
        return  stock.map(s->modelMapper.map(s,StockDto.class)) ;
    }

    public Stock getStockByName(PartenerName partenerName){
        Stock stock=stockRepo.findByPartenerName(partenerName) ;
        return stock ;
     }

    public Stock UpdateStockQuantity(Stock stock, int qte){
        stock.setProductsQuantity(qte);
        Stock updatedStock= stockRepo.save(stock);
        return updatedStock ;
    }
    public void decrementProductsQuantity( List<ProductClientCommandedDto> ProductsCommanded , PartenerName partenerName) {

        for (ProductClientCommandedDto product : ProductsCommanded) {

            Optional<ProductClientDto> productClient= productClientService.findProductClientByCodeBar(product.getCodeBar());
            int QteCmd = product.getQuantityCommanded();
            int newQte=productClient.get().getQuantity()- QteCmd;

            // update la quantité dans table produit
            productClientService.UpdatedProductQuantity(productClient,newQte);


            Stock stock= getStockByName(partenerName);       // get stock by name
            int newQteStock = stock.getProductsQuantity()-QteCmd ;
            UpdateStockQuantity(stock,newQteStock);         //update quantité dans dans le bd de stock
            stockRepo.save(modelMapper.map(stock,Stock.class));

        }
    }


}
