package com.example.StockDistant.Repository;

import com.example.StockDistant.Model.PartenerName;
import com.example.StockDistant.Model.ProductClient;
import com.example.StockDistant.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductClientRepo extends JpaRepository<ProductClient,Long> {
   List<ProductClient> findAllByPartenerName(PartenerName partenerName);
   Optional <ProductClient> findByCodeBar(String codeBar);

}
