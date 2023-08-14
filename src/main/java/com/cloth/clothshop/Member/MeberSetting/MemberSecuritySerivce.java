package com.cloth.clothshop.Member.MeberSetting;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberSecuritySerivce implements UserDetailsService {

	//Spring Security에서 인증을 담당하는 서비스
	//UserDetailsService 인터페이스의 정의된 메소드를 오버라이딩해서 구현
	private final MemberRepository memberRepository;

	//인증을 처리하는 메소드
	@Override
	public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {

		//login_form.html에서 username, password 넘어오는 username 인풋으로 받음.
		//DB에서 인풋으로 넘어오는 username 값을 DB에서 조회
		Optional<Member> _member = memberRepository.findMemberById(id);

		if (_member.isEmpty()) {

			//DB에서 해당 username이 존재하지 않는 경우
			//강제로 예외를 발생시킨다.
			throw new UsernameNotFoundException("잘못 입력하셨거나, 존재하지 않는 회원님입니다.");
		}

		//_siteUser의 값이 비어있지 않으면 SiteUser를 끄집어 냄.
		Member member = _member.get();

		//GrantedAuthority : 권한을 저장하는 객체
		List<GrantedAuthority> authorities = new ArrayList<>();
		//GrantedAuthority 인터페이스 : 메소드 선언만되어 있음.
		//SimpleGrantedAuthority : GrantedAuthority 인터페이스의 메소드를 구현한 클래스

		//관리자인지 사용자인지 처리함.
		//role의 값이 admin이라면 : 관리자 권한을 부여
		//role의 값이 admin이 아니라면 : 일반 사용자 권한을 부여함.
		if ("admin".equals(_member.get().getRole()) || "Admin".equals(_member.get().getRole())) {

			authorities.add(new SimpleGrantedAuthority(MemberRole.ADMIN.getValue()));
		} else {

			//username 필드의 admin이 아니라면 일반사용자 권한을 부여 : USER("ROLE_USER");
			authorities.add(new SimpleGrantedAuthority(MemberRole.USER.getValue()));
		}

		//UserDetails : 인터페이스
		//User : UserDetials 인터페이스를 구현한 객체
		//User (username, password, 권한)
		return new User(member.getId(), member.getPwd(), authorities);
	}
}
