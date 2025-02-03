package com.bcnc.techtest.infraestructure.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.bcnc.techtest.application.services.PriceService;
import com.bcnc.techtest.constants.ErrorConstants;
import com.bcnc.techtest.domain.model.PriceResponseDTO;
import com.bcnc.techtest.infraestructure.exceptions.PriceNotFoundException;

import java.time.LocalDateTime;
import java.util.Optional;

@RestController
@RequestMapping("/prices")
public class PriceController {

    @Autowired
    private PriceService priceService;

    @GetMapping
    public ResponseEntity<PriceResponseDTO> getApplicablePrice(
    		@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime applicationDate,
    		@RequestParam Long productId,
    		@RequestParam Long brandId) {

        if (productId == null || brandId == null || applicationDate == null) {
            throw new IllegalArgumentException(ErrorConstants.ERROR_PARAM_NULL);
        }

        Optional<PriceResponseDTO> price = priceService.findApplicablePrice(applicationDate, productId, brandId);
        return price.map(ResponseEntity::ok)
                    .orElseThrow(() -> new PriceNotFoundException());
    }
}
