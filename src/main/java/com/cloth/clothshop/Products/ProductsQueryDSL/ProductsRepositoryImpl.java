package com.cloth.clothshop.Products.ProductsQueryDSL;


import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import com.cloth.clothshop.Products.ProductsSetting.ProductsRecsStatus;
import com.cloth.clothshop.RepeatCode.QueryDSL_RepeatCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cloth.clothshop.Products.QProducts.products;

@Repository
@RequiredArgsConstructor
public class ProductsRepositoryImpl implements ProductsRepositoryCustom {

    private final QueryDSL_RepeatCode queryDSLRepeatCode;
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Products> findByOptionAndKeyword(String searchOption, String searchKeyword, Pageable pageable) {

        BooleanExpression condition = null;
        String likeKeyword = "%" + searchKeyword + "%";

        if ("code".equals(searchOption)) {
            condition = products.code.like(likeKeyword);
        } else if ("name".equals(searchOption)) {
            condition = products.name.like(likeKeyword);
        } else if ("kind".equals(searchOption)) {
            condition = products.kind.eq(ProductsKind.valueOf(searchKeyword.toUpperCase()));
        } else if ("all".equals(searchOption)) {
            if (searchKeyword.length() == 1) {
                condition = products.code.like(likeKeyword)
                        .or(products.name.like(likeKeyword))
                        .or(products.kind.eq(ProductsKind.valueOf(searchKeyword.toUpperCase())))
                ;
            } else {
                condition = products.code.like(likeKeyword)
                        .or(products.name.like(likeKeyword))
                ;
            }
        }
        return queryDSLRepeatCode.keywordIsEmpty(products, condition, searchKeyword, pageable);
    }

    @Override
    public List<Products> findByRecommendations() {
        BooleanExpression condition =
                products.prodRecsStatus.eq(ProductsRecsStatus.fromStatus(1))
                        .and(products.useyn.eq('Y'));
        return queryFactory.selectFrom(products)
                .where(condition)
                .fetch();
    }
}
