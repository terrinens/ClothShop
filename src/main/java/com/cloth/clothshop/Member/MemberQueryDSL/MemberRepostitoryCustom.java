package com.cloth.clothshop.Member.MemberQueryDSL;

import com.cloth.clothshop.Member.Member;

public interface MemberRepostitoryCustom {

    Member findByKeyword(String keyword, String searchOption);
}
