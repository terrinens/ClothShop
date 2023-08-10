package com.cloth.clothshop.Member.MemberQueryDSL;

import com.cloth.clothshop.Member.Member;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositroyImpl implements MemberRepostitoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Member findByKeyword(String keyword, String searchOption) {

        return null;
    }
}
