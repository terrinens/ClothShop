package com.cloth.clothshop.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String mainHome() {

		return "index";
	}

	@GetMapping("/management/allitem")
	public String managementAllitem() {

		return "management/allitem_management";
	}

	@GetMapping("/management/member")
	public String managementMember() {

		return "management/member_management";
	}

	@GetMapping("/management/order")
	public String managementOrder() {

		return "management/order_management";
	}

	@GetMapping("/management/recommended")
	public String managementRecommended() {

		return "management/recommended_management";
	}
}
