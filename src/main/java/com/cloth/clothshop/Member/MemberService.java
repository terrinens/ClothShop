package com.cloth.clothshop.Member;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service @RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository mRepository;
	private final PasswordEncoder pEncoder;
	
	public void memberSignup(String id, String pwd, String name, String address, String tel) {
		
		Member member = new Member();
		
		/*member.setZipcode(zipcode);*/
		member.setId(id);
		member.setPwd(pEncoder.encode(pwd));
		member.setName(name);
		member.setAddress(address);
		member.setTel(tel);
		
		mRepository.save(member);
	}
}
