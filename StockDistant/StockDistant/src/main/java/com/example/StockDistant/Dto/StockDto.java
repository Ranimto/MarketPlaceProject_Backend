package com.example.StockDistant.Dto;

import com.example.StockDistant.Model.PartenerName;
import com.example.StockDistant.Model.ProductClient;
import com.example.StockDistant.Model.Stock;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class StockDto {
private Long id ;
private  String StockName ;
private int ProductsQuantity ;
private PartenerName partenerName;
@JsonIgnore
private List<ProductClientDto> productClients ;
}
