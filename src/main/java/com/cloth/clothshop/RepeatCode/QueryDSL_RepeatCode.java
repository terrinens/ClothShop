package com.cloth.clothshop.RepeatCode;

import com.cloth.clothshop.Member.Member;
import com.querydsl.core.QueryFactory;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.concurrent.locks.Condition;

public class QueryDSL_RepeatCode {

    @PersistenceContext
    private EntityManager entityManager;

    public <T> PageImpl<T> keywordIsEmpty(EntityPath<T> table, BooleanExpression condition, String keyword, Pageable pageable) {

        JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);

        JPAQuery<T> query = queryFactory.selectFrom(table);
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

        long total = query.fetch().size();

        return new PageImpl<>(page, pageable, total);

    }
}
