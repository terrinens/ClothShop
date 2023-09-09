package com.cloth.clothshop.Products.ProductsQueryDSL;

import com.cloth.clothshop.Products.Products;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductsRepositoryCustom {

    Page<Products> findByOptionAndKeyword(String searchOption, String searchKeyword, Pageable pageable);
    List<Products> findByRecommendations();
    Page<Products> findBySpecificKindOR(Pageable pageable, char [] specificKind);

    Page<Products> findBySpecificKindOR(Pageable pageable, List<Products> productsList);

    List<Products> findBySpecificKindOR(char[] specificKind);
}
