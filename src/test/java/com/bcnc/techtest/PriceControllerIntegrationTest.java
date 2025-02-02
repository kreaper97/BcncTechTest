package com.bcnc.techtest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

import com.bcnc.techtest.constants.ErrorConstants;
import com.bcnc.techtest.domain.model.ErrorDetailsDTO;
import com.bcnc.techtest.domain.model.PriceResponseDTO;

@SpringBootTest(classes = BCNCApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(MockitoExtension.class)
public class PriceControllerIntegrationTest {
	
	@Autowired
    private TestRestTemplate restTemplate;

	@LocalServerPort
    private int port;
    
    Long productId = 35455L;
    Long brandId = 1L;
    
    String BASE_URL;
    
    @BeforeEach
    void initBaseURL() {
    	BASE_URL = "http://localhost:" + port + "/api/v1/prices?";
    }
    

    // Método común para realizar la solicitud y obtener el precio mediante rest
    private void assertPriceForDate(LocalDateTime applicationDate, double expectedPrice) {
    	
    	String url = BASE_URL + "applicationDate=" + applicationDate.toString() + 
                "&productId=" + productId + "&brandId=" + brandId;
        ResponseEntity<PriceResponseDTO> response = restTemplate.getForEntity(
        		url, 
                PriceResponseDTO.class);

        assertEquals(200, response.getStatusCode().value());
        assertEquals(expectedPrice, response.getBody().getPrice(), 0.01);
    }
    
    @Test
    public void test1Price() {
        // Petición a las 10:00 del día 14 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 10, 0);
        
        assertPriceForDate(applicationDate, 35.50); // Asume que el precio esperado es 35.50
    }

    @Test
    public void test2Price() {
        // Petición a las 16:00 del día 14 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 16, 0);
        assertPriceForDate(applicationDate, 25.45); // Asume que el precio esperado es 25.45
    }

    @Test
    public void test3Price() {
        // Petición a las 21:00 del día 14 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 14, 21, 0);
        assertPriceForDate(applicationDate, 35.50); // Asume que el precio esperado es 35.50
    }

    @Test
    public void test4Price() {
        // Petición a las 10:00 del día 15 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 15, 10, 0);
        assertPriceForDate(applicationDate, 30.50); // Asume que el precio esperado es 30.50
    }

    @Test
    public void test5Price() {
        // Petición a las 21:00 del día 16 del producto 35455 para la brand 1 (ZARA)
        LocalDateTime applicationDate = LocalDateTime.of(2020, 6, 16, 21, 0);
        assertPriceForDate(applicationDate, 38.95); // Asume que el precio esperado es 38.95
    }
    
    @Test
    public void testPriceNotFoundException() {
        // Usamos una fecha en el futuro y un ID de producto y marca inexistentes
        LocalDateTime applicationDate = LocalDateTime.of(2030, 1, 1, 0, 0); // Fecha futura
        
        String url = BASE_URL 
        		+ "applicationDate=" + applicationDate.toString() 
        		+ "&productId=" + productId 
        		+ "&brandId=" + brandId;
        
        ResponseEntity<ErrorDetailsDTO> response = restTemplate.getForEntity(
        		url, 
        		ErrorDetailsDTO.class
        );

        // Verificamos que el código de estado sea 404
        assertEquals(404, response.getStatusCode().value());

        // Verificamos que el mensaje de error contenga la razón de la excepción
        ErrorDetailsDTO errorDetails = response.getBody();
        assertNotNull(errorDetails);
        assertEquals(ErrorConstants.ERROR_NOT_FOUND, errorDetails.getError());
        assertTrue(errorDetails.getMessage().contains(ErrorConstants.ERROR_PRICE_NOT_FOUND));
    }
    
    @Test
    public void testMissingProductIdBrandIdShouldReturnBadRequest() {
    	// Usamos una fecha en el futuro y un ID de producto y marca inexistentes
        LocalDateTime applicationDate = LocalDateTime.of(2030, 1, 1, 0, 0); // Fecha futura
        
        String url = BASE_URL 
        		+ "applicationDate=" + applicationDate.toString() 
        		+ "&productId="
        		+ "&brandId=";
        
        ResponseEntity<ErrorDetailsDTO> response = restTemplate.getForEntity(
        		url, 
        		ErrorDetailsDTO.class
        );
        
        // Verificamos que el código de estado sea 400 (Bad Request)
        assertEquals(400, response.getStatusCode().value());

        // Verificamos que el mensaje de error sea el esperado
        ErrorDetailsDTO responseBody = response.getBody();
        assertNotNull(responseBody);
        assertEquals(ErrorConstants.ERROR_BAD_REQUEST, responseBody.getError());
        assertTrue(responseBody.getMessage().contains(ErrorConstants.ERROR_REQUIRED_PARAM));     
    }

}
