package com.cloth.clothshop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String mainHome() {

		return "index";
	}

	@GetMapping("/management/member")
	public String managementMember() {

		return "management/member_management";
	}
}
