package com.example.StockDistant.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@Entity
@Table(name = "Stock")
public class Stock {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private  Long id ;
    @Column (name = "StockName")
    private  String StockName ;
    @Column(name = "ProductsQuantity")
    private int ProductsQuantity ;

    @Column(name = "partenerName",unique = true)
    @Enumerated(EnumType.STRING)
    private PartenerName partenerName ;

    @OneToMany(mappedBy = "stock")
    @JsonIgnore
    private List<ProductClient> productClients ;
}
