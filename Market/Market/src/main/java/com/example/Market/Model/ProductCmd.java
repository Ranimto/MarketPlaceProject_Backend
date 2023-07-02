package com.example.Market.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import lombok.Data;

@Data
public class ProductCmd {
//    @JsonProperty("ProductId")
//    private  Long ProductId ;
    @JsonProperty("codeBar")
    private  String codeBar ;
    @JsonProperty("QteCommanded")
     private int QteCommanded;

}
