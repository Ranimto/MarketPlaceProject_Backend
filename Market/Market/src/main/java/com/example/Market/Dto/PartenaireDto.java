package com.example.Market.Dto;
import com.example.Market.Model.PartenerName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;
@Data
@NoArgsConstructor
public class PartenaireDto {
    private PartenerName Name;
    private  String manager ;
    private String address ;
    @JsonIgnore
    private List<ProductDto> products;
}
