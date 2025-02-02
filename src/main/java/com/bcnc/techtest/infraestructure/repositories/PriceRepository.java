package com.bcnc.techtest.infraestructure.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bcnc.techtest.infraestructure.entities.Price;

import jakarta.transaction.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional
public interface PriceRepository extends JpaRepository<Price, Long> {
    List<Price> findByBrandIdAndProductIdAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            Long brandId, Long productId, LocalDateTime applicationDate, LocalDateTime applicationDate2);
}