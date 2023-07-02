package com.example.StockDistant.Dto;

import com.example.StockDistant.Model.Availability;
import com.example.StockDistant.Model.PartenerName;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductClientDto {
    private Long id;
    private String codeBar;
    private String designation ;
    private String description ;
    private double price ;
    private int Quantity ;
    private String imageUrl ;
    private Availability status ;
    private PartenerName partenerName ;

    //private StockDto stock ;  //this cirvul create a problem in the serializability ->problem in json format
}
