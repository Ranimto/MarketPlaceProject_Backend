package com.example.Market.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name ="ligneCommande")
public class LigneCommande {

    @EmbeddedId
    private LigneCommandeId id ;

    @Column(name = "quantityCommanded")
    private Integer quantityCommanded;

    @Column(name = "codeBar")
    private String codeBar;

    @ManyToOne
    @MapsId("productId")
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @MapsId("commandeId")
    @JoinColumn(name = "commande_id")
    private Commande commande;
}
