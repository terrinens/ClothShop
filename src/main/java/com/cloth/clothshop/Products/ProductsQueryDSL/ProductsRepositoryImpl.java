package com.cloth.clothshop.Products.ProductsQueryDSL;


import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.RepeatCode.QueryDSL_RepeatCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.cloth.clothshop.Products.QProducts.products;

@Repository
@RequiredArgsConstructor
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {

    private final QueryDSL_RepeatCode queryDSLRepeatCode;

    @Override
    public Page<Products> findByOptionAndKeyword(String searchOption, String keyword, Pageable pageable) {

        BooleanExpression condition = null;

        if ("code".equals(searchOption)) {

            condition = products.code.like("%" + keyword + "%");
        } else if ("name".equals(searchOption)) {

            condition = products.name.like("%" + keyword + "%");
        } else if ("kind".equals(searchOption)) {

            condition = products.kind.eq(keyword.toUpperCase().charAt(0));
        }

        return queryDSLRepeatCode.keywordIsEmpty(products, condition, keyword, pageable);
    }
}
