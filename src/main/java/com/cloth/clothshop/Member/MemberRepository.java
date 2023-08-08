package com.cloth.clothshop.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findMemberById(String id);

    Optional<Member> findMemberByName(String name);
}
