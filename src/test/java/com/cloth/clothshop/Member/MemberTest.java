package com.cloth.clothshop.Member;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MemberTest {

    @Autowired
    MemberRepository memberRepository;
    @Autowired
    MemberService memberService;
	
	/*@Autowired
	tb_ZipcodeRepository zipcodeRepository;*/
	
/*	@Test
	void temporarilyMember() {
		
		Member m1 = new Member();
		Optional<tb_Zipcode> oq = zipcodeRepository.findById("1111");
		
		if (oq.isPresent()) {
			
			tb_Zipcode zipcode = oq.get();
			
			m1.setId("admin");
			m1.setPwd("1234");
			m1.setName("어드민");
			m1.setRole("Admin");
			m1.setZipcode(zipcode);
			m1.setAddress("admin@admin.com");
			m1.setTel("010-1111-1111");
			memberRepository.save(m1);
		}
		
		for (int i = 1; i < 5; i++) {
			
			Member m2 = new Member();
			String num4 = String.valueOf(i) + String.valueOf(i) + String.valueOf(i) + String.valueOf(i);
			
			Optional<tb_Zipcode> oq2 = zipcodeRepository.findById(num4);
			
			if (oq2.isPresent()) {
				
				tb_Zipcode zipcode = oq2.get();
				
				m2.setId("user"+i);
				m2.setPwd("1234");
				m2.setName("유저"+i);
				*//*m2.setZipcode(zipcode);*//*
				m2.setAddress("user"+i+"@user.com");
				m2.setTel("010-" + num4 + "-" + num4);
				
				memberRepository.save(m2);
			}
		}*/

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

        for (int i = 1; i < 5; i++) {

            Member m2 = new Member();
            String num4 = i + String.valueOf(i) + i + i;

            m2.setId("user" + i);
            m2.setPwd("1234");
            m2.setName("유저" + i);
            m2.setAddress("user" + i + "@user.com");
            m2.setTel("010-" + num4 + "-" + num4);

            memberService.memberSignup(m2.getId(), m2.getPwd(), m2.getName(), m2.getAddress(), m2.getTel());
        }
    }
}