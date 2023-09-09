package com.cloth.clothshop.Products.ProductsQueryDSL;


import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import com.cloth.clothshop.Products.ProductsSetting.ProductsRecsStatus;
import com.cloth.clothshop.RepeatCode.QueryDSL_RepeatCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
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

    private final QueryDSL_RepeatCode queryDSLRepeatCode;
    private final JPAQueryFactory queryFactory;
    private BooleanExpression condition = null;

    @Override
    public Page<Products> findByOptionAndKeyword(String searchOption, String searchKeyword, Pageable pageable) {
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
        condition = products.prodRecsStatus.eq(ProductsRecsStatus.fromStatus(1))
                .and(products.useyn.eq('Y'));
        return queryFactory.selectFrom(products)
                .where(condition)
                .fetch();
    }

    @Override
    public Page<Products> findBySpecificKindOR(Pageable pageable, char[] specificKind) {
        getConditionSpecifickKind(specificKind);
        JPAQuery<Products> query = queryFactory.selectFrom(products)
                .where(condition)
                .orderBy(products.prodRecsStatus.desc(), products.indate.asc());

        List<Products> page = query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();
        long total = query.fetch().size();

        return new PageImpl<>(page, pageable, total);
    }

    @Override
    public List<Products> findBySpecificKindOR(char[] specificKind) {
        getConditionSpecifickKind(specificKind);
        return queryFactory.selectFrom(products)
                .where(condition)
                .fetch();
    }

    private void getConditionSpecifickKind(char[] specificKind) {
        condition = products.kind.eq(ProductsKind.fromChar(specificKind[0]));
        for (int i = 1; i < specificKind.length; i++) {
            condition .or(products.kind.eq(ProductsKind.fromChar(specificKind[i])));
        }
        condition.and(products.prodRecsStatus.eq(ProductsRecsStatus.fromStatus(1)));
    }
}
