package com.cloth.clothshop.RepeatCode;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RequiredArgsConstructor @Configuration
public class QueryDSL_RepeatCode {

    private final  JPAQueryFactory queryFactory;

    public <T> PageImpl<T> keywordIsEmpty(EntityPath<T> table, BooleanExpression condition, String searchKeyword, Pageable pageable) {

        JPAQuery<T> query = queryFactory.selectFrom(table);
        long total = query.fetch().size();
        List<T> page;
        if (condition != null && !searchKeyword.trim().isEmpty()) {
            total = query
                    .where(condition)
                    .fetch().size();

            page = query
                    .where(condition)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {

            page = query
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }
        return new PageImpl<>(page, pageable, total);
    }
}
