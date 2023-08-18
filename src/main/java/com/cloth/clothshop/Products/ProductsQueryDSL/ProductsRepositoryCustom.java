package com.cloth.clothshop.Products.ProductsQueryDSL;

import com.cloth.clothshop.Products.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductsRepositoryCustom {

    Page<Products> findByOptionAndKeyword(String searchOption, String searchKeyword, Pageable pageable);
}
