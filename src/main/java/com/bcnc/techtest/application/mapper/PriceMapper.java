package com.bcnc.techtest.application.mapper;

import com.bcnc.techtest.domain.model.PriceResponseDTO;
import com.bcnc.techtest.infraestructure.entities.Price;

public interface PriceMapper {

	public static PriceResponseDTO toPriceResponseDto(Price price) {
        return new PriceResponseDTO(
                price.getProductId(),
                price.getBrand().getId(),
                price.getPriceList(),
                price.getStartDate(),
                price.getEndDate(),
                price.getPrice()
        );
    }
}
