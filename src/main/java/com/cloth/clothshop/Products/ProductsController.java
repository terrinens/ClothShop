package com.cloth.clothshop.Products;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller @RequiredArgsConstructor @RequestMapping("/products")
public class ProductsController {


    private final ProductsService pService;

    @GetMapping("/cloth/sleeve")
    public String sleeveList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        char kindOption1 = 'a'; char kindOption2 = 'b';

        Page<Products> paging = pService.getlistCloth(kindOption1, kindOption2, page);

        model.addAttribute("paging", paging);

        return "cloths/sleeve";
    }

    @GetMapping("/cloth/pants")
    public String pantsList(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        char kindOption1 = 'c'; char kindOption2 = 'd';

        Page<Products> paging = pService.getlistCloth(kindOption1, kindOption2, page);

        model.addAttribute("paging", paging);

        return "cloths/pants";
    }

    @GetMapping("/cloth/skirt")
    public String skirt(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {

        char kindOption1 = 'e'; char kindOption2 = 'f';

        Page<Products> paging = pService.getlistCloth(kindOption1, kindOption2, page);

        model.addAttribute("paging", paging);

        return "cloths/skirt";
    }
}
