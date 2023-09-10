package com.cloth.clothshop.Member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
	
    @Test
    void temporarilyMember2() {

        Member m1 = new Member();

        m1.setId("admin");
        m1.setPwd("1234");
        m1.setName("어드민");
        m1.setRole("Admin");
        m1.setAddress("admin@admin.com");
        m1.setTel("010-1111-1111");

        memberService.adminSignup(m1.getId(), m1.getPwd(), m1.getName(), m1.getAddress(), m1.getTel(), m1.getRole());

        for (int i = 1; i < 100; i++) {

            Member m2 = new Member();

            int rendomNum1 = generateRandomNumbers();
            int rendomNum2 = generateRandomNumbers();

            m2.setId("user" + i);
            m2.setPwd("1234");
            m2.setName("유저" + i);
            m2.setAddress("user" + i + "@user.com");
            m2.setTel("010-" + rendomNum1 + "-" + rendomNum2);

            memberService.memberSignup(m2.getId(), m2.getPwd(), m2.getName(), m2.getAddress(), m2.getTel());
        }
    }

    private static int generateRandomNumbers() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
}