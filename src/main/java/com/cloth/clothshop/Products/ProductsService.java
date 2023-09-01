package com.cloth.clothshop.Products;

import com.cloth.clothshop.Management.Form.ManagementItemForm;
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
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductsService {

    private final ProductsRepository pRepository;
    private final Management_RepeatCode managementRepeatCode;
    @Value("{img.dir}")
    private String imgDir;
    private final ProductsImgStorageRepository pImgStorageRepository;
    private final Products products = new Products();

    public Page<Products> getlistCloth(char kindOption1, char kindOption2, int page
    ) {
        Pageable pageable = PageRequest.of(page, 15);

        return pRepository.findByKindList(kindOption1, kindOption2, pageable);
    }

    public Page<Products> managementGetPaging(Model model, int page, String keyword, String option) {
        //해결할 문제 kind 별로 정렬을 잡을것. 현재 kind로 정렬을 잡으면 인식하지 못함.
        Pageable pageable = PageRequest.of(page, 10, Sort.by("indate").ascending());
        model.addAttribute("page", 0);
        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);
        return pRepository.findByOptionAndKeyword(option, keyword, pageable);
    }

    public Page<Products> managementGetDefaultPaging(Model model) {
        //해결할 문제 kind 별로 정렬을 잡을것. 현재 kind로 정렬을 잡으면 인식하지 못함.
        Pageable pageable = PageRequest.of(0, 10, Sort.by("indate").ascending());
        model.addAttribute("keyword", "");
        model.addAttribute("option", "all");
        return pRepository.findByOptionAndKeyword("all", "", pageable);
    }

    public Optional<Products> productsItemSearch(String code) {
        return pRepository.findById(code);
    }

    public void managementNewProductsItem(Map<String, Object> itemData) {
        products.managementNewItemSave(mapDataConversionNewItemForm(itemData));
        pRepository.save(products);
    }

    /**Map 데이터를 전달시 Optional로 검사후 수정함*/
    public void managementModifyProductsItem(Map<String, Object> itemData) {
        Optional<Products> productsOptional = pRepository.findById(mapDataConversionModifyItemForm(itemData).getCode());
        if (productsOptional.isPresent()) {
            Products ifProduct = productsOptional.get();
            pRepository.modifyItem(
                    ifProduct.getCode(), ifProduct.getKind(), ifProduct.getName()
                    , ifProduct.getContents(), ifProduct.getSizeSt(), ifProduct.getSizeEt()
                    , ifProduct.getPrice(), ifProduct.getQuantity(), ifProduct.getUseyn()
                    , ifProduct.getImage(), ifProduct.getIndate()
            );

        }
    }

    /**
     * Optional 검사를 거친후 사용할것
     */
    public void managementDeleteProductsItem(String code) {
        pRepository.deleteById(code);
    }

    private Long saveFile(MultipartFile files) throws IOException {
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

    private ManagementItemForm mapDataConversionNewItemForm(Map<String, Object> data) {
        ManagementItemForm newItemForm = new ManagementItemForm();
        newItemForm.setName(data.get("name").toString());
        newItemForm.setKind(data.get("kind").toString().charAt(0));
        newItemForm.setPrice(data.get("price").toString());
        newItemForm.setContents(data.get("contents").toString());
        newItemForm.setImage(data.get("image").toString());
        newItemForm.setSizeSt(data.get("sizeSt").toString());
        newItemForm.setSizeEt(data.get("sizeEt").toString());
        newItemForm.setQuantity(Integer.parseInt(data.get("quantity").toString()));
        newItemForm.setUseyn(data.get("useyn").toString().charAt(0));
        newItemForm.setIndate(Date.valueOf(LocalDateTime.now().toLocalDate()));
        return newItemForm;
    }

    private ManagementItemForm mapDataConversionModifyItemForm(Map<String, Object> data) {
        ManagementItemForm newItemForm = mapDataConversionNewItemForm(data);
        newItemForm.setCode(data.get("code").toString());
        return newItemForm;
    }
}
