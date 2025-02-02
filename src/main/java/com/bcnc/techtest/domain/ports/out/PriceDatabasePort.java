package com.bcnc.techtest.domain.ports.out;

import java.time.LocalDateTime;
import java.util.List;

import com.bcnc.techtest.infraestructure.entities.Price;

public interface PriceDatabasePort{
    List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId);
}