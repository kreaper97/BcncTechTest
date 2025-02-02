package com.bcnc.techtest.application.services;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcnc.techtest.domain.model.PriceResponseDTO;
import com.bcnc.techtest.domain.ports.in.GetApplicablePriceUCPort;

@Service
public class PriceService implements GetApplicablePriceUCPort{

	@Autowired
	private GetApplicablePriceUCPort getApplicablePriceUCPort;

	@Override
    public Optional<PriceResponseDTO> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return getApplicablePriceUCPort.findApplicablePrice(applicationDate, productId, brandId);
        
    }

	

}