package com.example.Market.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

        private String firstName ;
        private String lastName ;
        private String address ;
        private String email ;
        private String phoneNum ;
        @JsonIgnore
        private List<CommandeDto> commande ;
}
