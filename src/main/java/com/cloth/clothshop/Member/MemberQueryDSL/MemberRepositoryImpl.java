package com.cloth.clothshop.Member.MemberQueryDSL;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.RepeatCode.QueryDSL_RepeatCode;
import com.querydsl.core.types.dsl.BooleanExpression;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import static com.cloth.clothshop.Member.QMember.member;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

    private final QueryDSL_RepeatCode queryDSLRepeatCode;

    @Override
    public Page<Member> findByOptionAndKeyword(String searchOption, String searchKeyword, Pageable pageable) {

        BooleanExpression condition = null;
        String likeKeyword = "%" + searchKeyword + "%";

        if ("id".equals(searchOption)) {
            condition = member.id.like(likeKeyword);
        } else if ("name".equals(searchOption)) {
            condition = member.name.like(likeKeyword);
        } else if ("role".equals(searchOption)) {
            condition = member.role.like(likeKeyword);
        } else if ("all".equals(searchOption)) {
            condition = member.id.like(likeKeyword)
                    .or(member.name.like(likeKeyword))
                    .or(member.role.like(likeKeyword))
                    .or(member.tel.like(likeKeyword))
                    ;
        } else if ("tel".equals(searchOption)) {

            condition = member.tel.like(likeKeyword);
        }

        return queryDSLRepeatCode.keywordIsEmpty(member, condition, searchKeyword, pageable);
    }
}