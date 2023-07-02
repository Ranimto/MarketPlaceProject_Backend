package com.example.Market.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Product")
public class Product {
    @Id

    private Long id;
    @Column( name = "codeBar", unique = true)
    private  String codeBar;
    @Column( name = "ref_prod")
    private  String ref_prod;
    @Column(name = "designation")
    private String designation ;
    @Column(name = "description")
    private String description ;
    @Column(name = "price")
    private double price ;
    @Column(name = "Quantity")
    private int Quantity ;
    @Column(name = "imageUrl")
    private String imageUrl ;
    @Enumerated (EnumType.STRING)
    @Column(name = "status")
    private  Availability status ;

    @Enumerated (EnumType.STRING)
    @Column(name = "partenerName")
    private PartenerName partenerName ;

  @OneToMany(mappedBy = "product" )
  private List<LigneCommande> lignesCommande;

}
