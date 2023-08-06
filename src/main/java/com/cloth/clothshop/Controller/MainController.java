package com.cloth.clothshop.Controller;

import com.cloth.clothshop.Member.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
	@GetMapping("/")
	public String mainHome () {

		return "index";
	}

	@GetMapping("/cloth/sleeve")
	public String sleeve() {

		return "cloths/sleeve";
	}

	@GetMapping("/cloth/pants")
	public String pants() {

		return "cloths/pants";
	}

	@GetMapping("/cloth/skirt")
	public String skirt() {

		return "cloths/skirt";
	}
}
