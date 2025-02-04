package com.bcnc.techtest.features.brands.infraestructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface BrandRepository extends JpaRepository<Brand, Long> {

}