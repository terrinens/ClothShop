package com.cloth.clothshop.Member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, String> {

    Optional<Member> findMemberById(String id);

    Optional<Member> findMemberByName(String name);

    @Modifying
    @Query("UPDATE Member "
            + "SET pwd = :pwd, name = :name, role = :role, address = :address, tel = :tel "
            + "WHERE id = :id"
    )
    void managementModify(
            @Param("id") String id,
            @Param("pwd") String pwd,
            @Param("name") String name,
            @Param("role") String role,
            @Param("address") String address,
            @Param("tel") String tel
            );
}
