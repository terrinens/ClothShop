package com.cloth.clothshop.Products;

import com.cloth.clothshop.Products.Form.ItemViewDTO;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Controller @RequiredArgsConstructor @RequestMapping("/products")
public class ProductsController {
    private final ProductsService pService;

    @GetMapping("/cloth/sleeve")
    public String sleeveList(Model model) {
        char[] specificKind = {ProductsKind.A.getKind(), ProductsKind.B.getKind()};
        Page<Products> productsPage = pService.viewItemGetPaging(model ,specificKind, 0);
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

    @GetMapping("/cloth/moreInfo")
    public String moreInfo(@RequestParam ItemViewDTO viewDTO) {
        return null;
    }
}
