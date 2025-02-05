package com.bcnc.techtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import com.bcnc.techtest.common.ErrorConstants;
import com.bcnc.techtest.common.exceptions.ErrorDetailsDTO;
import com.bcnc.techtest.features.prices.domain.model.PriceResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(classes = BCNCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
class PriceControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;
	
	@Autowired
	private ObjectMapper objectMapper;

	@LocalServerPort
    private int port;
    
    Long productId = 35455L;
    Long brandId = 1L;
    
    String baseUrl;
    
    @BeforeEach
    void initBaseURL() {
    	baseUrl = "http://localhost:" + port + "/api/v1/prices?";
    }
    

    // Método común para realizar la solicitud y obtener el precio mediante rest
    private void assertPriceForDate(LocalDateTime applicationDate, double expectedPrice) {
    	
    	String url = baseUrl + "applicationDate=" + applicationDate.toString() + 
                "&productId=" + productId + "&brandId=" + brandId;
        ResponseEntity<Object> response = restTemplate.getForEntity(
        		url, 
        		Object.class);
        
    	
        // Deserializar el LinkedHashMap al PriceResponseDTO (Si no se puede es que la estructura es distinta a este DTO)
		PriceResponseDTO priceResponseDTO = objectMapper.convertValue(response.getBody(), PriceResponseDTO.class);
        
		assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedPrice, priceResponseDTO.getPrice(), 0.01);
    	
    
    }
    
    @Test
    void test1Price() {
        // Petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        
        assertPriceForDate(applicationDate, 35.50); // Asume que el precio esperado es 35.50
    }

    @Test
    void test2Price() {
        // Petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        assertPriceForDate(applicationDate, 25.45); // Asume que el precio esperado es 25.45
    }

    @Test
    void test3Price() {
        // Petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        assertPriceForDate(applicationDate, 35.50); // Asume que el precio esperado es 35.50
    }

    @Test
    void test4Price() {
        // Petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        assertPriceForDate(applicationDate, 30.50); // Asume que el precio esperado es 30.50
    }

    @Test
    void test5Price() {
        // Petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        assertPriceForDate(applicationDate, 38.95); // Asume que el precio esperado es 38.95
    }
    
	@Test
    void testPriceNotFoundException() {
        // Usamos una fecha en el futuro y un ID de producto y marca inexistentes
        LocalDateTime applicationDate = LocalDateTime.of(2030, 1, 1, 0, 0); // Fecha futura
        
        String url = baseUrl 
        		+ "applicationDate=" + applicationDate.toString() 
        		+ "&productId=" + productId 
        		+ "&brandId=" + brandId;
        
        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        // Verificamos que el código de estado sea 404
        assertEquals(404, response.getStatusCode().value());

    	
        // Deserializar el LinkedHashMap al ErrorDetailsDTO (Si no se puede es que la estructura es distinta a este DTO)
		ErrorDetailsDTO errorDetails = objectMapper.convertValue(response.getBody(), ErrorDetailsDTO.class);
        
        assertEquals(ErrorConstants.ERROR_NOT_FOUND, errorDetails.getError());
        assertEquals(ErrorConstants.ERROR_PRICE_NOT_FOUND, errorDetails.getMessage());
    	
    }
    
	@Test
    void testMissingProductIdBrandIdShouldReturnBadRequest() {
    	// Usamos una fecha en el futuro y un ID de producto y marca inexistentes
        LocalDateTime applicationDate = LocalDateTime.of(2030, 1, 1, 0, 0); // Fecha futura
        
        String url = baseUrl 
        		+ "applicationDate=" + applicationDate.toString() 
        		+ "&productId="
        		+ "&brandId=";
        
        ResponseEntity<Object> response = restTemplate.getForEntity(
        		url, 
        		Object.class
        );
        
        // Verificamos que el código de estado sea 400 (Bad Request)
        assertEquals(400, response.getStatusCode().value());

    	
        // Deserializar el LinkedHashMap al ErrorDetailsDTO (Si no se puede es que la estructura es distinta a este DTO)
		ErrorDetailsDTO errorDetails = objectMapper.convertValue(response.getBody(), ErrorDetailsDTO.class);
        
        assertEquals(ErrorConstants.ERROR_BAD_REQUEST, errorDetails.getError());
        assertTrue(errorDetails.getMessage().contains(ErrorConstants.ERROR_REQUIRED_PARAM));
    	
    }
    
	@Test
    void testIllegalArgumentExceptionHandler() {
        String url = baseUrl; // Sin parámetros para provocar IllegalArgumentException

        ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

        assertEquals(400, response.getStatusCode().value());
        
    	
        // Deserializar el LinkedHashMap al ErrorDetailsDTO (Si no se puede es que la estructura es distinta a este DTO)
		ErrorDetailsDTO errorDetails = objectMapper.convertValue(response.getBody(), ErrorDetailsDTO.class);
        
        assertEquals(ErrorConstants.ERROR_BAD_REQUEST, errorDetails.getError());
        assertTrue(errorDetails.getMessage().contains(ErrorConstants.ERROR_REQUIRED_PARAM));
    	
    }

}
