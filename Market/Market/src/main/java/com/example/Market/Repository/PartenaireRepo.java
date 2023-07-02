package com.example.Market.Repository;

import com.example.Market.Model.Partenaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartenaireRepo extends JpaRepository<Partenaire,Long> {
}
