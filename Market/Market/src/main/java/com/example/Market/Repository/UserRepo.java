package com.example.Market.Repository;

import com.example.Market.Model.Commande;
import com.example.Market.Model.Product;
import com.example.Market.Model.User;
import jakarta.persistence.OneToMany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {
    Optional<Product> findUserById(Long id);
    void deleteUserById(Long id);
    Optional<User> findByEmail(String email);

}
