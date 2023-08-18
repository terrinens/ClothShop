package com.cloth.clothshop.Products;

import com.cloth.clothshop.Management.ManagementNewItemForm;
import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberRepository;
import com.cloth.clothshop.RepeatCode.Management_RepeatCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository pRepository;
    private final Management_RepeatCode managementRepeatCode;

    public Page<Products> getlistCloth(char kindOption1, char kindOption2, int page
    ) {

        Pageable pageable = PageRequest.of(page, 15);

        Page<Products> divisionCloth = pRepository.findByKindList(kindOption1, kindOption2, pageable);

        return divisionCloth;
    }

    public Page<Products> managementGetAutoPaging(Object[] requestParamArray) {

        String targetRCN = ProductsRepository.class.getName();
        String sortBenchmark = "products_kind";
        Page<Products> productsPage = managementRepeatCode.autoWritePaging(targetRCN, sortBenchmark, requestParamArray);

        return productsPage;
    }

    public void managementNewProductsItem(ManagementNewItemForm newItemForm) {

        Products products = new Products().managementItemSave(newItemForm);

        pRepository.save(products);
    }
}
