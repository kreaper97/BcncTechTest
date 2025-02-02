package com.bcnc.techtest.application.services;


import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bcnc.techtest.domain.model.PriceResponseDTO;
import com.bcnc.techtest.domain.ports.in.GetApplicablePricePort;

@Service
public class PriceService implements GetApplicablePricePort{

	@Autowired
	private GetApplicablePricePort getApplicablePricePort;

	@Override
    public Optional<PriceResponseDTO> findApplicablePrice(LocalDateTime applicationDate, Long productId, Long brandId) {
        return getApplicablePricePort.findApplicablePrice(applicationDate, productId, brandId);
        
    }

	

}