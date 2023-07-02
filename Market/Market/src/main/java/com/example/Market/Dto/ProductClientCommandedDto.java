package com.example.Market.Dto;

import com.example.Market.Model.PartenerName;
import jakarta.persistence.Id;
import lombok.Data;

@Data
public class ProductClientCommandedDto {

    //private Long id;
    private  String codeBar ;
    private int QuantityCommanded ;

}
