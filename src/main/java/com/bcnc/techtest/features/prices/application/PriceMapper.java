package com.bcnc.techtest.features.prices.application;

import com.bcnc.techtest.features.prices.domain.model.PriceResponseDTO;
import com.bcnc.techtest.features.prices.infraestructure.Price;

public interface PriceMapper {

	public static PriceResponseDTO toPriceResponseDto(Price price) {
        return new PriceResponseDTO(
                price.getProduct().getId(),
                price.getBrand().getId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrecio()
        );
    }
}
