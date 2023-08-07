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

    @Autowired
    ProductsService pService;

    @GetMapping("/cloth/sleeve")
    public String sleeve() {

        return "cloths/sleeve";
    }

    @GetMapping("/cloth/pants")
    public String pantsList(Model model, @RequestParam(value = "page", defaultValue = "0") int page
    ) {

        char kindOption1 = 'c'; char kindOption2 = 'd';

        Page<Products> paging = pService.getlistCloth(kindOption1, kindOption2, page);

        model.addAttribute("paging", paging);

        return "cloths/pants";
    }

    @GetMapping("/cloth/skirt")
    public String skirt() {

        return "cloths/skirt";
    }

/*    @GetMapping("/list")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {

        //2. 비즈니스 로직처리
        //List<Question> questionList = this.questionRepostiroy.findAll();
        Page<Question> paging = questionService.getList(page, kw);

        //3. 받아온 List를 client로 전송(Model 객체에 저장해서 Client로 전송)
        model.addAttribute("paging", paging);

        paging.getTotalPages();

        return "question_list";
    }*/

}
