package com.example.StockDistant.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "productClient")
public class ProductClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;
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
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private  Availability status ;

    @Enumerated(EnumType.STRING)
    @Column(name = "partenerName")
    private PartenerName partenerName ;


    @ManyToOne
    @JoinColumn(name="idStock")
    private Stock  stock ;
}
