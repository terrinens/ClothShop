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
        System.out.println("jpa 리펙트코드 테이블 :::: " + table);
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

            System.out.println("jpa 리펙트코드 Listpage 저장 비어있지 않음 사이즈 :::: " + page.size());
            System.out.println("jpa 리펙트코드 Listpage 저장 비어있지 않음 0번 저장값 :::: " + page.toString());
            System.out.println("jpa 리펙트코드 total 값 :::: " + total);
        } else {

            page = query
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();

            System.out.println("jpa 리펙트코드 Listpage  저장 비어있음 저장 사이즈 :::: " + page.size());
            System.out.println("jpa 리펙트코드 Listpage  저장 비어있음 0번 저장값 :::: " + page.toString());
            System.out.println("jpa 리펙트코드 total 값 :::: " + total);
        }

        return new PageImpl<>(page, pageable, total);
    }
}
