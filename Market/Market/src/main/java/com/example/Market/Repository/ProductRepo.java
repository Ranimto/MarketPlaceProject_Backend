package com.example.Market.Repository;

import com.example.Market.Model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepo extends JpaRepository<Product,Long> {
// Les méthodes save, findById , deleteById , count et existsById
// sont definies puisque ils sont fournies par l'interface JpaRepository
//Optional object, allowing you to handle the case where the product may be absent.


    Optional<Product> findProductById(Long id);
    void deleteProductById(Long id);

}
