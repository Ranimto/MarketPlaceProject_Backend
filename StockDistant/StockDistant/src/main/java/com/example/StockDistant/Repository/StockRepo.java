package com.example.StockDistant.Repository;

import com.example.StockDistant.Model.PartenerName;
import com.example.StockDistant.Model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepo extends JpaRepository<Stock,Long> {
    Stock findByPartenerName(PartenerName partenerName);
}
