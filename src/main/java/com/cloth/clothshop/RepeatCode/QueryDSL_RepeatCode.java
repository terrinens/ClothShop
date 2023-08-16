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

    public <T> PageImpl<T> keywordIsEmpty(EntityPath<T> table, BooleanExpression condition, String keyword, Pageable pageable) {

        JPAQuery<T> query = queryFactory.selectFrom(table);
        long total = query.fetch().size();
        List<T> page;
        if (condition != null && !keyword.isEmpty()) {

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


        System.out.println("리펙트 코드로부터 total value::::: " + total);
        System.out.println("리펙트 코드로부터 리스트 개수 ::::: " + page.size());
        System.out.println("리펙트 코드로부터 출력됨 페이져블 사이즈 ::::: " + pageable.getPageSize());
        System.out.println("리펙트 코드로부터 출력됨 페이져블 오프셋::::: " + pageable.getOffset());

        return new PageImpl<>(page, pageable, total);
    }
}
