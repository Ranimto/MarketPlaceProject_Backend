package com.example.Market.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Commande")
@Entity

public class Commande {

   @Id
   @GeneratedValue
   private Long id ;


    @Column(name = "date")
    private Date date ;
    @Enumerated(EnumType.STRING)
    @Column(name = "commandeStatus")
    private CommandeStatus CommandeStatus ;
   @ManyToOne
   @JoinColumn(name="idUser")
   private User user ;


    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private List<LigneCommande> lignesCommande = new ArrayList<>();
}
