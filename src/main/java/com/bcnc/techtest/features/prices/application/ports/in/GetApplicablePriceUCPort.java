package com.bcnc.techtest.features.prices.application.ports.in;

import java.time.LocalDateTime;
import java.util.Optional;

import com.bcnc.techtest.features.prices.domain.model.PriceResponseDTO;

public interface GetApplicablePriceUCPort{
	Optional<PriceResponseDTO> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}