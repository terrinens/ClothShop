package com.cloth.clothshop.Products.ProductsQueryDSL;


import com.cloth.clothshop.Products.Products;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cloth.clothshop.Products.QProducts.products;

@Repository
@RequiredArgsConstructor
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Products> findByOptionAndKeyword(String searchOption, String keyword, Pageable pageable) {

        BooleanExpression condition = null;

        if ("id".equals(searchOption)) {

            condition = products.price1.like("%" + keyword + "%");
        }

        List<Products> productsPage;
        if (condition != null && !keyword.isEmpty()) {

            productsPage = queryFactory
                    .selectFrom(products)
                    .where(condition)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {

            productsPage = queryFactory
                    .selectFrom(products)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        long total = queryFactory.selectFrom(products).fetch().size();

        return new PageImpl<>(productsPage, pageable, total);
    }
}
