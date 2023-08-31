package com.cloth.clothshop.Products;

import com.cloth.clothshop.Management.ManagementNewItemForm;
import com.cloth.clothshop.RepeatCode.Management_RepeatCode;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository pRepository;
    private final Management_RepeatCode managementRepeatCode;
    @Value("{img.dir}")
    private String imgDir;
    private final ProductsImgStorageRepository pImgStorageRepository;

    public Page<Products> getlistCloth(char kindOption1, char kindOption2, int page
    ) {

        Pageable pageable = PageRequest.of(page, 15);

        return pRepository.findByKindList(kindOption1, kindOption2, pageable);
    }

    public Page<Products> managementGetPaging(Model model, int page, String keyword, String option) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("kind").ascending());
        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);
        return pRepository.findByOptionAndKeyword(option, keyword, pageable);
    }

    public Page<Products> managementGetDefaultPaging(Model model) {
        Pageable pageable= PageRequest.of(0, 15, Sort.by("kind").ascending());
        model.addAttribute("keyword", "");
        model.addAttribute("option", "all");
        return pRepository.findByOptionAndKeyword("all", "", pageable);
    }

    public void managementNewProductsItem(ManagementNewItemForm newItemForm) {

        Products products = new Products().managementNewItemSave(newItemForm);

        pRepository.save(products);
    }

    public Long saveFile(MultipartFile files) throws IOException {
        if (files.isEmpty()) {
            return null;
        } else {
            String originName = files.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String extend = Objects.requireNonNull(originName).substring(originName.lastIndexOf("."));
            String savedName = uuid + extend;
            String savedPath = imgDir + savedName;

            ProductsImgStorage storage = ProductsImgStorage.builder()
                    .originUploadName(originName)
                    .savedName(savedName)
                    .savedPath(savedPath)
                    .build();

            files.transferTo(new File(savedPath));
            ProductsImgStorage savedImg = pImgStorageRepository.save(storage);

            return savedImg.getId();
        }
    }
}
