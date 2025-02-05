package com.bcnc.techtest.features.prices.infraestructure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcnc.techtest.features.prices.application.ports.out.PriceDatabasePort;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class PriceRepositoryAdapter implements PriceDatabasePort {
	
	private PriceRepository priceRepository;

    @Override
    public List<Price> findApplicablePrices(LocalDateTime applicationDate, Long productId, Long brandId) {
        return priceRepository.findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
                brandId, productId, applicationDate, applicationDate);
    }
    
    @Autowired
    public void setPriceRepository(PriceRepository priceRepository) {
		this.priceRepository = priceRepository;
	}
}
