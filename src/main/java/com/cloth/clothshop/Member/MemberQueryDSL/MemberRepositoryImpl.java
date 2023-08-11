package com.cloth.clothshop.Member.MemberQueryDSL;

import com.cloth.clothshop.Member.Member;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.cloth.clothshop.Member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Member> findByOptionAndKeyword(String searchOption, String keyword, Pageable pageable) {

        BooleanExpression condition = null;

        if ("id".equals(searchOption)) {

            condition = member.id.like("%" + keyword + "%");
        } else if ("name".equals(searchOption)) {

            condition = member.name.like("%" + keyword + "%");
        } else if ("role".equals(searchOption)) {

            condition = member.role.like("%" + keyword + "%");
        } else if ("all".equals(searchOption)) {

            condition = member.id.like("%" + keyword + "%")
                    .or(member.name.like("%" + keyword + "%"))
                    .or(member.role.like("%" + keyword + "%"))
                    .or(member.tel.like("%" + keyword + "%"))
            ;
        } else if ("tel".equals(searchOption)) {

            condition = member.tel.like("%" + keyword + "%");
        }

        List<Member> memberPage;
        if (condition != null && !keyword.isEmpty()) {

            memberPage = queryFactory
                    .selectFrom(member)
                    .where(condition)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        } else {

            memberPage = queryFactory
                    .selectFrom(member)
                    .offset(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .fetch();
        }

        long total = queryFactory.selectFrom(member).fetch().size();

        return new PageImpl<>(memberPage, pageable, total);
    }
}