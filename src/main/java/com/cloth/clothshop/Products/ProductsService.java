package com.cloth.clothshop.Products;

import com.cloth.clothshop.Management.ManagementNewItemForm;
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

    public Page<Products> getlistCloth(char kindOption1, char kindOption2, int page
    ) {

        Pageable pageable = PageRequest.of(page, 15);

        Page<Products> divisionCloth = pRepository.findByKindList(kindOption1, kindOption2, pageable);

        return divisionCloth;
    }

    public Page<Products> managementGetProductsPage(int page, String searchOption, String keyword) {

        Pageable pageable = PageRequest.of(page, 15, Sort.by("id").ascending());

        Page<Products> productsPage = pRepository.findByOptionAndKeyword(searchOption, keyword, pageable);

        return productsPage;
    }

    public void managementNewProductsItem(ManagementNewItemForm newItemForm) {

        Products products = new Products().managementItemSave(newItemForm);

        pRepository.save(products);
    }
}
