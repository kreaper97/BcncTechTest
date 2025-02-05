CREATE TABLE IF NOT EXISTS BRAND (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS PRODUCT (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS PRICE (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand_id BIGINT NOT NULL,
    start_date TIMESTAMP NOT NULL,
    end_date TIMESTAMP NOT NULL,
    price_list INT NOT NULL,
    product_id BIGINT NOT NULL,
    priority INT NOT NULL,
    precio DECIMAL(10,2) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    CONSTRAINT fk_brand FOREIGN KEY (brand_id) REFERENCES BRAND(id),
    CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES PRODUCT(id)
);

-- Insertar la marca (ejemplo: ZARA)
INSERT INTO BRAND (id, name) VALUES (1, 'ZARA');

-- Insertar productos
INSERT INTO PRODUCT (id, name) VALUES (35455, 'Camiseta de Algodón');
INSERT INTO PRODUCT (id, name) VALUES (67890, 'Pantalón Vaquero');
INSERT INTO PRODUCT (id, name) VALUES (12345, 'Chaqueta de Cuero');
INSERT INTO PRODUCT (id, name) VALUES (98765, 'Zapatos Deportivos');
INSERT INTO PRODUCT (id, name) VALUES (54321, 'Bolso de Cuero');

-- Insertar precios asociados a ZARA
INSERT INTO PRICE (id, brand_id, product_id, start_date, end_date, price_list, priority, precio, currency) VALUES
(1, 1, 35455, '2020-06-14 00:00:00', '2020-12-31 23:59:59', 1, 0, 35.50, 'EUR'),
(2, 1, 35455, '2020-06-14 15:00:00', '2020-06-14 18:30:00', 2, 1, 25.45, 'EUR'),
(3, 1, 35455, '2020-06-15 00:00:00', '2020-06-15 11:00:00', 3, 1, 30.50, 'EUR'),
(4, 1, 35455, '2020-06-15 16:00:00', '2020-12-31 23:59:59', 4, 1, 38.95, 'EUR');