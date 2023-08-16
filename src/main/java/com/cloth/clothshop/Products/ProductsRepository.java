package com.cloth.clothshop.Products;

import com.cloth.clothshop.Products.ProductsQueryDSL.ProductsRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductsRepository extends JpaRepository<Products, String>, ProductsRepositoryCustom {

    @Query("select p from Products p where p.kind=:kindOption1 or p.kind=:kindOption2")
    Page<Products> findByKindList(@Param("kindOption1") char kindOption1, @Param("kindOption2") char kindOption2, Pageable pageable);
}
