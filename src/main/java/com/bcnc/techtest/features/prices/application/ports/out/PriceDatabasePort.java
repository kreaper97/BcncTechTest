package com.bcnc.techtest.features.prices.application.ports.out;

import java.time.LocalDateTime;
import java.util.List;

import com.bcnc.techtest.features.prices.infraestructure.Price;

public interface PriceDatabasePort{
    List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId);
}