package com.bcnc.techtest.application.usecases;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bcnc.techtest.application.mapper.PriceMapper;
import com.bcnc.techtest.domain.model.PriceResponseDTO;
import com.bcnc.techtest.domain.ports.in.GetApplicablePricePort;
import com.bcnc.techtest.domain.ports.out.PriceDatabasePort;
import com.bcnc.techtest.infraestructure.entities.Price;

@Component
public class GetApplicablePriceUC implements GetApplicablePricePort{
	
	@Autowired
	private PriceDatabasePort priceDatabasePort;
	
	public Optional<PriceResponseDTO> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId){
		 List<Price> prices = priceDatabasePort.findApplicablePrices(applicationDate, productId, brandId);
		 
		 return prices.stream()
	        		.max(Comparator.comparing(Price::getPriority)) // Devuelve el de maxima prioridad
	        		.map(PriceMapper::toPriceResponseDto); // Convertimos la entity Price en el DTO de respuesta con los campos necesarios
	}
}