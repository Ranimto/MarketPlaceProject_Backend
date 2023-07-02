package com.example.Market.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class LigneCommandeDto {

    private Long productId;
    private Long commandeId;
    private String codeBar ;
    private int quantityCommanded;

}
