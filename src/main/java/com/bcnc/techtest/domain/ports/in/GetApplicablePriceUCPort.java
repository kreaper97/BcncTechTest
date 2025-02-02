package com.bcnc.techtest.domain.ports.in;

import java.time.LocalDateTime;
import java.util.Optional;

import com.bcnc.techtest.domain.model.PriceResponseDTO;

public interface GetApplicablePriceUCPort{
	Optional<PriceResponseDTO> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId);
}