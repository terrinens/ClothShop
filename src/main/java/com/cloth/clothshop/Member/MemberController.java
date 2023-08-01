package com.cloth.clothshop.Member;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller 
@RequiredArgsConstructor 
@RequestMapping("/member")
public class MemberController {


	private final MemberService memberService;

	@GetMapping("/signup")
	public String memberSingup(MemberForm memberForm){
		
		return "/member/signup_form";
	}
	
	@PostMapping("/signup")
	public String memberSingup(@Valid MemberForm memberForm, BindingResult bindingResult) {

		System.out.println(memberForm.getId());
		System.out.println(memberForm.getPwd());
		System.out.println(memberForm.getPwd_check());
		System.out.println(memberForm.getName());
		System.out.println(memberForm.getAddress());
		System.out.println(memberForm.getTel());

		if (bindingResult.hasErrors()) {

			return "/member/signup_form";
		}

		if (!memberForm.getPwd().equals(memberForm.getPwd_check())) {

			bindingResult.rejectValue("pwd_check", "passwordInCorrect", "비밀번호를 재확인 해주세요!");
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
			bindingResult.rejectValue("id", null,"이미 등록된 사용자 입니다.");
		}

		return "redirect:/";
	}
	
}
