package com.bcnc.techtest.features.prices.application.usecases;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcnc.techtest.features.prices.application.PriceMapper;
import com.bcnc.techtest.features.prices.application.ports.in.GetApplicablePriceUCPort;
import com.bcnc.techtest.features.prices.application.ports.out.PriceDatabasePort;
import com.bcnc.techtest.features.prices.domain.model.PriceResponseDTO;
import com.bcnc.techtest.features.prices.infraestructure.Price;



@Service
public class GetApplicablePriceUC implements GetApplicablePriceUCPort{
	
	@Autowired
	private PriceDatabasePort priceDatabasePort;
	
	@Override
	public Optional<PriceResponseDTO> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId){
		 List<Price> prices = priceDatabasePort.findApplicablePrices(applicationDate, productId, brandId);
		 
		 return prices.stream()
	        		.max(Comparator.comparing(Price::getPriority)) // Devuelve el de maxima prioridad
	        		.map(PriceMapper::toPriceResponseDto); // Convertimos la entity Price en el DTO de respuesta con los campos necesarios
	}
}