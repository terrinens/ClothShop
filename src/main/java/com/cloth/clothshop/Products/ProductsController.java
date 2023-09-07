package com.cloth.clothshop.Products;

import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller @RequiredArgsConstructor @RequestMapping("/products")
public class ProductsController {
    private final ProductsService pService;
    private final EntityManager entityManager;

    @GetMapping("/cloth/sleeve")
    public String sleeveList(Model model) {
        char[] specificKind = {ProductsKind.A.getKind(), ProductsKind.B.getKind()};
        Page<Products> productsPage = pService.viewItemGetPaging(specificKind);
        model.addAttribute("productsPage", productsPage);
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
