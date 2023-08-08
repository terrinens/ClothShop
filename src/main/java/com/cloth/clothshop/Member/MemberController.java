package com.cloth.clothshop.Member;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller 
@RequiredArgsConstructor 
@RequestMapping("/member")
public class MemberController {

	private final MemberService memberService;
	private final MemberRepository memberRepository;

	@GetMapping("/signup")
	public String memberSingup(MemberForm memberForm){
		
		return "/member/signup_form";
	}
	
	@PostMapping("/signup")
	public String memberSingup(@Valid MemberForm memberForm, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {

			return "/member/signup_form";
		}

		if (!memberForm.getPwd().equals(memberForm.getPwd_check())) {

			bindingResult.rejectValue("pwd_check", "passwordInCorrect", "비밀번호를 재확인 해주세요!");

			return "/member/signup_form";
		}

		Optional<Member> memberCheck = memberRepository.findById(memberForm.getId());
		if (memberCheck.isPresent()) {

			return "/member/signup_form";
		}

		try {
			memberService.memberSignup(
					memberForm.getId(),
					memberForm.getPwd(),
					memberForm.getName(),
					memberForm.getAddress(),
					memberForm.getTel()
			);
		} catch (Exception e) {

			e.printStackTrace();
			bindingResult.rejectValue("id", "","이미 등록된 사용자 입니다.");
		}

		return "redirect:/";
	}

	@GetMapping("/signin")
	public String memberSignin() {

		return "member/signin_form";
	}

	@PostMapping("/signin")
	public void memberSignin(@RequestParam("id") String id, @RequestParam("pwd") String pwd) {

		System.out.println(id);
		System.out.println(pwd);
	}
	
}
