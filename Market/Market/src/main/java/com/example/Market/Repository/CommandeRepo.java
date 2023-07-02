package com.example.Market.Repository;

import com.example.Market.Dto.CommandeDto;
import com.example.Market.Model.Commande;
import com.example.Market.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommandeRepo extends JpaRepository<Commande,Long> {
    Optional<Commande> findCommandeById(Long id);
    void deleteCommandeById(Long id);
}
