package com.example.Market.Dto;
import com.example.Market.Model.Availability;
import com.example.Market.Model.PartenerName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class ProductDto {
    private Long id;
    private  String codeBar;
    private String designation ;
    private String description ;
    private double price ;
    private int Quantity ;
    private String imageUrl ;
    private Availability status ;
    private PartenerName partenerName ;
    @JsonIgnore
    private List<LigneCommandeDto> lignesCommande;

}
