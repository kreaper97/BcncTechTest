package com.bcnc.techtest.infraestructure.adapter;

import com.bcnc.techtest.domain.ports.out.PriceDatabasePort;
import com.bcnc.techtest.infraestructure.entities.Price;
import com.bcnc.techtest.infraestructure.repositories.PriceRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryAdapter implements PriceDatabasePort {
	
    @Autowired
	private PriceRepository priceRepository;

    @Override
    public List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                brandId, productId, applicationDate, applicationDate);
    }
}
