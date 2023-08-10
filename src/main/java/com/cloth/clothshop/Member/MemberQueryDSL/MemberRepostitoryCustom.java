package com.cloth.clothshop.Member.MemberQueryDSL;

import com.cloth.clothshop.Member.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MemberRepostitoryCustom {

    Page<Member> findByOptionAndKeyword(String searchOption, String keyword, Pageable pageable);
}
