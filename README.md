# BCNC Price Service

Este es un servicio Spring Boot que proporciona un endpoint REST para consultar el precio aplicable de un producto en una cadena específica en una fecha determinada.

## Tecnologías Utilizadas

- Spring Boot
- Arquitectura Hexagonal (Puertos y Adaptadores)
- H2 Database (en memoria)
- JPA (Java Persistence API)
- Jacoco (Cobertura de Código)
- JUnit 5 y Mockito (Pruebas Unitarias y de Integración)

## Ejecución

1. Clona el repositorio `git clone https://github.com/kreaper97/BcncTechTest.git`.
2. Ejecuta `mvnw clean install` para construir el proyecto.
3. Ejecuta `mvnw spring-boot:run` para iniciar la aplicación.

## Pruebas

Ejecuta `mvnw test` para ejecutar las pruebas unitarias y de integración.

## Cobertura de Código

Ejecuta `mvnw jacoco:report` para generar el informe de cobertura de código.

## Hacer un Request a la API de Precios

Para obtener el precio aplicable de un producto en una cadena específica en una fecha determinada, puedes hacer una solicitud HTTP al siguiente endpoint:

### Endpoint
GET /api/v1/prices

### Parámetros de la Solicitud

- `applicationDate` (requerido): Fecha y hora de la consulta en formato `yyyy-MM-dd'T'HH:mm:ss`.
- `productId` (requerido): ID del producto.
- `brandId` (requerido): ID de la cadena.

### Ejemplo de Request

Puedes hacer una solicitud usando `curl` o cualquier cliente HTTP, como Postman.

#### Usando cURL:

```bash
curl -X GET "http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1"
```

### Usando Postman

1. **Método**: `GET`
2. **URL**: `http://localhost:8080/api/v1/prices?applicationDate=2020-06-14T10:00:00&productId=35455&brandId=1`

## Respuesta Esperada
La respuesta será un JSON que contiene la información del precio aplicable:

Ejemplo de Respuesta:

```json
{
    "productId": 35455,
    "brandId": 1,
    "priceList": 1,
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T23:59:59",
    "price": 35.5
}
```

- **`productId`**: El ID del producto.
- **`brandId`**: El ID de la cadena.
- **`priceList`**: El ID de la lista de precios.
- **`startDate`**: La fecha y hora de inicio del precio aplicable.
- **`endDate`**: La fecha y hora de fin del precio aplicable.
- **`price`**: El precio aplicable para el producto.

---

## Errores Comunes

1. 404 Not Found: Si no se encuentra un precio aplicable para los parámetros proporcionados.
2. 400 Bad Request: Si alguno de los parámetros (como applicationDate, productId, o brandId) no es válido o falta.

### Ejemplo de Respuesta de Error (404):

```json
{
    "timestamp": "2025-02-02T22:03:40.1920662",
    "status": 404,
    "error": "Not Found",
    "message": "Price not found"
}
```

### Ejemplo de Respuesta de Error (400):

```json
{
    "timestamp": "2025-02-02T22:04:02.4800183",
    "status": 400,
    "error": "Bad Request",
    "message": "Application Date, Product ID and Brand ID cannot be null"
}
```