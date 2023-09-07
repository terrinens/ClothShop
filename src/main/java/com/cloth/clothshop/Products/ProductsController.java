package com.cloth.clothshop.Products;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
