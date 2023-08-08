package com.cloth.clothshop.Products;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository pRepository;

    public Page<Products> getlistCloth(char kindOption1, char kindOption2, int page
    ) {

        Pageable pageable = PageRequest.of(page, 15);

        Page<Products> divisionCloth = pRepository.findByKindList(kindOption1, kindOption2, pageable);

        return divisionCloth;
    }
    /*
    //페이징 처리해서 리턴으로 돌려줌<사용>
    public Page<Question> getList(int page, String kw) {

        //page : 클라이언트에서 파라메터로 요청한 페이지 번호
        //10 : 한 페이지에서 출력할 레코드 수
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        //검색기능을 사용하지 않았을때는 가능하다.
        //Page<Question> pageQuestion = questionRepostiroy.findAll(pageable);

        Page<Question> pageQuestion = questionRepostiroy.findAllByKeyword(kw, pageable);

        return pageQuestion;
    }
     */


}
