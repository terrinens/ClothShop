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
		System.out.println("리스트 사이즈? { " + productsList.size() + " }");
		model.addAttribute("productsList", productsList);
		return "index";
	}
}
