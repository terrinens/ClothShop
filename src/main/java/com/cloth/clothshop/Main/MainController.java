package com.cloth.clothshop.Main;

import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.Products.ProductsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller @RequiredArgsConstructor
public class MainController {
	private final ProductsService pService;

	@GetMapping("/")
	public String mainHome(Model model) {
		List<Products> productsList = pService.indexGetList();
		model.addAttribute("productsList", productsList);
		System.out.println(" { 메인 컨트롤러 호출 완료" + " }");
		return "index";
	}
}
