package com.bcnc.techtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import com.bcnc.techtest.application.services.PriceService;
import com.bcnc.techtest.constants.ErrorConstants;
import com.bcnc.techtest.domain.model.PriceResponseDTO;
import com.bcnc.techtest.infraestructure.controller.PriceController;
import com.bcnc.techtest.infraestructure.exceptions.PriceNotFoundException;

@ExtendWith(MockitoExtension.class)
public class PriceControllerUnitTest {

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    private Long productId = 35455L;
    private Long brandId = 1L;
    private LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);

    @Test
    public void testGetApplicablePriceNotFound() {
        // Configuración del mock para simular que no se encontró el precio
        when(priceService.findApplicablePrice(applicationDate, productId, brandId))
            .thenReturn(Optional.empty());

        // Llamada al método del controlador y verificación de la excepción
        PriceNotFoundException exception = assertThrows(PriceNotFoundException.class, () -> {
            priceController.getApplicablePrice(applicationDate, productId, brandId);
        });
        
        // Verificación del mensaje de la excepción
        assertEquals(ErrorConstants.ERROR_PRICE_NOT_FOUND, exception.getMessage());
    }

    @Test
    public void testGetApplicablePriceAllParametersNull() {
        // Llamada al método del controlador y verificación de la excepción
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            priceController.getApplicablePrice(null, null, null);
        });

        verifyExceptionParamNull(exception);
    }

    @Test
    public void testGetApplicablePriceApplicationDateNull() {
        // Llamada al método del controlador y verificación de la excepción
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            priceController.getApplicablePrice(null, productId, brandId);
        });

        verifyExceptionParamNull(exception);
    }

    @Test
    public void testGetApplicablePriceProductIdNull() {
        // Llamada al método del controlador y verificación de la excepción
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            priceController.getApplicablePrice(applicationDate, null, brandId);
        });

        verifyExceptionParamNull(exception);
    }

    @Test
    public void testGetApplicablePriceBrandIdNull() {
        // Llamada al método del controlador y verificación de la excepción
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            priceController.getApplicablePrice(applicationDate, productId, null);
        });

        verifyExceptionParamNull(exception);
    }

    @Test
    public void testGetApplicablePriceApplicationDateAndProductIdNull() {
        // Llamada al método del controlador y verificación de la excepción
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            priceController.getApplicablePrice(null, null, brandId);
        });

        verifyExceptionParamNull(exception);
    }

    @Test
    public void testGetApplicablePriceApplicationDateAndBrandIdNull() {
        // Llamada al método del controlador y verificación de la excepción
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            priceController.getApplicablePrice(null, productId, null);
        });

        verifyExceptionParamNull(exception);
    }

    @Test
    public void testGetApplicablePriceProductIdAndBrandIdNull() {
        // Llamada al método del controlador y verificación de la excepción
    	IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            priceController.getApplicablePrice(applicationDate, null, null);
        });

        verifyExceptionParamNull(exception);
    }
    
    private void verifyExceptionParamNull(IllegalArgumentException exception) {
    	// Verificación del mensaje de la excepción
        assertEquals(ErrorConstants.ERROR_PARAM_NULL, exception.getMessage());
    }
    
    @Test
    public void testGetApplicablePrice_Success() {
        // Datos de prueba
        PriceResponseDTO mockResponse = new PriceResponseDTO();
        mockResponse.setBrandId(brandId);
        mockResponse.setPrice(35.50);
        mockResponse.setProductId(productId);
        mockResponse.setPriceList(1);
        // Simular que el servicio devuelve un precio
        when(priceService.findApplicablePrice(applicationDate, productId, brandId))
            .thenReturn(Optional.of(mockResponse));

        // Llamada al método del controlador
        ResponseEntity<PriceResponseDTO> response = priceController.getApplicablePrice(applicationDate, productId, brandId);

        // Verificaciones
        assertEquals(200, response.getStatusCode().value());
        assertEquals(mockResponse, response.getBody());
        verify(priceService).findApplicablePrice(applicationDate, productId, brandId);
    }

}