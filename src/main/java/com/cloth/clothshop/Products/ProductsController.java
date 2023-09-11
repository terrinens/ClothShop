package com.cloth.clothshop.Products;

import com.cloth.clothshop.Products.Form.ItemViewDTO;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
    public String pantsList(Model model) {
        char[] specificKind = {ProductsKind.C.getKind(), ProductsKind.D.getKind()};
        Page<Products> productsPage = pService.viewItemGetPaging(model ,specificKind, 0);
        model.addAttribute("productsPage", productsPage);
        return "cloths/pants";
    }

    @GetMapping("/cloth/skirt")
    public String skirt(Model model) {
        char[] specificKind = {ProductsKind.E.getKind(), ProductsKind.F.getKind()};
        Page<Products> productsPage = pService.viewItemGetPaging(model ,specificKind, 0);
        model.addAttribute("productsPage", productsPage);
        return "cloths/skirt";
    }

    @GetMapping("/cloth/moreInfo")
    public String moreInfo(@ModelAttribute ItemViewDTO viewDTO, Model model) {
        char [] specifickKind = viewDTO.getCharArray();
        int page = viewDTO.getPageCount();
        Page<Products> productsPage = pService.viewItemGetPaging(model, specifickKind, page);
        model.addAttribute("productsPage", productsPage);
        return "cloths/moreitemAjax";
    }
}
