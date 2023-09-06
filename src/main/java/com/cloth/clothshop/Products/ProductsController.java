package com.cloth.clothshop.Products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller @RequiredArgsConstructor @RequestMapping("/products")
public class ProductsController {

    @GetMapping("/cloth/sleeve")
    public String sleeveList() {
        return "cloths/sleeve";
    }

    @GetMapping("/cloth/pants")
    public String pantsList() {
        return "cloths/pants";
    }

    @GetMapping("/cloth/skirt")
    public String skirt() {
        return "cloths/skirt";
    }
}
