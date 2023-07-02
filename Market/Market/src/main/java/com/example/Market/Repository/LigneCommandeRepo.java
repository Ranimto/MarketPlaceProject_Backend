package com.example.Market.Repository;

import com.example.Market.Model.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneCommandeRepo extends JpaRepository<LigneCommande,Long> {

}
