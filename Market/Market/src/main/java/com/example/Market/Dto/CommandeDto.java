package com.example.Market.Dto;

import com.example.Market.Model.CommandeStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@NoArgsConstructor
@Data
public class CommandeDto {
    private Long id;
    private Date date ;
    private CommandeStatus status ;
    private UserDto user ;
    private List<LigneCommandeDto> lignesCommande;


}
