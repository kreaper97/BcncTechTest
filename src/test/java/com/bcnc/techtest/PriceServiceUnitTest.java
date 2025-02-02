package com.bcnc.techtest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.bcnc.techtest.application.services.PriceService;
import com.bcnc.techtest.domain.model.PriceResponseDTO;
import com.bcnc.techtest.domain.ports.in.GetApplicablePricePort;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PriceServiceUnitTest {

    @Mock
    private GetApplicablePricePort getApplicablePricePort;

    @InjectMocks
    private PriceService priceService;

    @Test
    public void testFindApplicablePrice() {
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        Long productId = 35455L;
        Long brandId = 1L;

        PriceResponseDTO expectedResponse = new PriceResponseDTO(productId, brandId, 1, applicationDate, applicationDate.plusDays(1), 35.50);

        when(getApplicablePricePort.findApplicablePrice(applicationDate, productId, brandId))
            .thenReturn(Optional.of(expectedResponse));

        Optional<PriceResponseDTO> result = priceService.findApplicablePrice(applicationDate, productId, brandId);

        assertEquals(35.50, result.get().getPrice(), 0.01); // Tolerancia de 0.01 ya que devuelve 35.5
        
        // Verificamos que ha llamado al método
        verify(getApplicablePricePort).findApplicablePrice(applicationDate, productId, brandId);
    }
}
