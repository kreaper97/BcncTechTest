package com.bcnc.techtest.features.prices.domain.services;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcnc.techtest.features.prices.application.ports.in.GetApplicablePriceUCPort;
import com.bcnc.techtest.features.prices.domain.model.PriceResponseDTO;



@Service
public class PriceService {

	private GetApplicablePriceUCPort getApplicablePriceUCPort;

    public Optional<PriceResponseDTO> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
    	// Se puede añadir más lógica de negocio antes de devolver los datos. 
    	// En caso de que no se quiera añadir lçogica extra se puede prescindir de esta capa de servicio
    	return getApplicablePriceUCPort.findApplicablePrice(applicationDate, productId, brandId);
    }

    @Autowired
	public void setGetApplicablePriceUCPort(GetApplicablePriceUCPort getApplicablePriceUCPort) {
		this.getApplicablePriceUCPort = getApplicablePriceUCPort;
	}

}