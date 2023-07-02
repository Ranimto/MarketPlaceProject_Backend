package com.example.StockDistant.Dto;

import com.example.StockDistant.Model.PartenerName;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProductClientCommandedDto {

   // private Long id;
    private String codeBar ;
    private int QuantityCommanded ;

}
