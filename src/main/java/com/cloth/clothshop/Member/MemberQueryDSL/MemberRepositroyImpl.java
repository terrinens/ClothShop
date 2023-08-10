package com.cloth.clothshop.Member.MemberQueryDSL;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.QMember;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepositroyImpl implements MemberRepostitoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Member> findByOptionAndKeyword(String searchOption, String keyword, Pageable pageable) {

        final QMember member = QMember.member;
        BooleanExpression condition = null;

        if("id".equals(searchOption)) {

            condition = member.id.like(keyword);
        } else if("name".equals(searchOption)) {

            condition = member.name.like(keyword);
        } else if("role".equals(searchOption)) {

            condition = member.role.like(keyword);
        }

        List<Member> memberPage = queryFactory
                .selectFrom(member)
                .where(condition)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(memberPage, pageable, memberPage.size());
    }
}
