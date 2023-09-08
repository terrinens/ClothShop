package com.cloth.clothshop.Products;

import com.cloth.clothshop.Management.Form.ManagementItemForm;
import com.cloth.clothshop.Management.ManagmentItemDTO;
import com.cloth.clothshop.Products.ImgSetting.ProductsImgStorage;
import com.cloth.clothshop.Products.ImgSetting.ProductsImgStorageRepository;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import com.cloth.clothshop.Products.ProductsSetting.ProductsRecsStatus;
import jakarta.transaction.Transactional;
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
import java.io.InterruptedIOException;
import java.sql.Date;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ProductsService {

    @Value("${pimgsave.dir}")
    private String imgSaveDir;
    @Value("${pimgloot.dir}")
    private String imgLootDir;
    private final ProductsRepository pRepository;
    private final ProductsImgStorageRepository pImgStorageRepository;
    private final Products products = new Products();

    public List<Products> indexGetList() {
        return pRepository.findByRecommendations();
    }

    /**
     * @param model      반복되는 값 자동으로 반환을 위한 값
     * @return model 속성 : keyword, option을 반환
     */
    public Page<Products> managementGetPaging(Model model, int page, String keyword, String option) {
        //해결할 문제 kind 별로 정렬을 잡을것. 현재 kind로 정렬을 잡으면 인식하지 못함.
        Pageable pageable = PageRequest.of(page, 10, Sort.by("indate").ascending());
        model.addAttribute("page", 0);
        model.addAttribute("keyword", keyword);
        model.addAttribute("option", option);
        return pRepository.findByOptionAndKeyword(option, keyword, pageable);
    }

    /** @return 모든 상품 데이터 */
    public Page<Products> managementGetPaging(Model model) {
        //해결할 문제 kind 별로 정렬을 잡을것. 현재 kind로 정렬을 잡으면 인식하지 못함.
        Pageable pageable = PageRequest.of(0, 10, Sort.by("indate").ascending());
        model.addAttribute("keyword", "");
        model.addAttribute("option", "all");
        return pRepository.findByOptionAndKeyword("all", "", pageable);
    }

    /**
     * @param model      반복되는 값 자동으로 반환을 위한 값
     * @param searchData 검색 데이터
     * @return model 속성 : keyword, option을 반환
     */
    public Page<Products> managementGetPaging(Model model, ManagmentItemDTO.SearchData searchData) {
        //해결할 문제 kind 별로 정렬을 잡을것. 현재 kind로 정렬을 잡으면 인식하지 못함.
        Pageable pageable = PageRequest.of(searchData.getPage(), 10, Sort.by("indate").ascending());
        model.addAttribute("keyword", searchData.getKeyword());
        model.addAttribute("option", searchData.getOption());
        return pRepository.findByOptionAndKeyword(searchData.getOption(), searchData.getKeyword(), pageable);
    }

    /**@param specificKind - 출력을 원하는 kind의 값을 배열로 전달할것. 배열 1번 이후 값들은 OR처리 됨
     * @return specificKind로 전달된 값들 페이징 처리*/
    public Page<Products> viewItemGetPaging(char [] specificKind) {
        Pageable pageable = PageRequest.of(0, 10, Sort.by("indate").ascending());
        return pRepository.findBySpecificKindOR(pageable, specificKind);
    }

    public Optional<Products> productsItemSearch(String code) {
        return pRepository.findById(code);
    }

    public void managementNewProductsItem(ManagementItemForm newItemForm) {
        products.managementNewItemSave(newItemForm);
        pRepository.save(products);
    }

    /** Map 데이터를 전달시 Optional로 검사후 수정함 만약 여러 Service를 사용시 반드시 entityManager.clear(); 할것 */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void managementModifyProductsItem(Map<String, Object> itemData) {
        Optional<Products> productsOptional = pRepository.findById(mapDataConversionNewItemForm(itemData).getCode());
        if (productsOptional.isPresent()) {
            ManagementItemForm newItemForm = mapDataConversionNewItemForm(itemData);
            pRepository.modifyItem(
                    newItemForm.getCode(), ProductsKind.getKind(newItemForm.getKind()), newItemForm.getName()
                    , newItemForm.getContents(), newItemForm.getSizeSt(), newItemForm.getSizeEt()
                    , newItemForm.getPrice(), newItemForm.getQuantity(), newItemForm.getUseyn()
                    , newItemForm.getImage(), newItemForm.getIndate(), ProductsRecsStatus.fromStatus(newItemForm.getProdRecsStatus())
            );
        }
    }

    /**
     * Optional로 검사후 수정함 Form의 image값이 null일 경우 자동처리
     * 여러 Service를 사용시 반드시 entityManager.clear(); 할것
     */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void managementModifyProductsItem(ManagementItemForm itemForm) {
        Optional<Products> productsOptional = pRepository.findById(itemForm.getCode());

        if (productsOptional.isPresent()) {
            if (itemForm.getImage() == null) {
                itemForm.setImage(productsOptional.get().getImage());
            }
            pRepository.modifyItem(
                    itemForm.getCode(), ProductsKind.getKind(itemForm.getKind()), itemForm.getName()
                    , itemForm.getContents(), itemForm.getSizeSt(), itemForm.getSizeEt()
                    , itemForm.getPrice(), itemForm.getQuantity(), itemForm.getUseyn()
                    , itemForm.getImage(), itemForm.getIndate(), ProductsRecsStatus.fromStatus(itemForm.getProdRecsStatus())
            );
        }
    }

    /** Optional 검사를 거친후 사용할것 */
    public void managementDeleteProductsItem(String code) {
        pRepository.deleteById(code);
    }

    /**하나의 파일을 받아 설정된 경로에 파일을 저장하는 메서드.
     * @param files - 저장할 파일을 보낸다.
     * @return savedPath - 저장된 파일의 경로를 리턴한다.
     * @throws IOException 저장에 실패시, 현재 환경에 대한 값을 받은후, 권한 수정을 실행한다. 현재 윈도우, 리눅스만 할당함*/
    public String saveFile(MultipartFile files) {
        if (files.isEmpty()) {
            return null;
        } else {
                String originName = files.getOriginalFilename();
                String uuid = UUID.randomUUID().toString();
                String extend;
                if (Objects.requireNonNull(originName).lastIndexOf(".") != -1) {
                    extend = Objects.requireNonNull(originName).substring(originName.lastIndexOf("."));
                } else {
                    extend = files.getContentType();
                }

                String savedName = uuid + extend;
                String savedPath = imgLootDir + savedName;
                String absolutePath = imgSaveDir + savedName;

                ProductsImgStorage storage = ProductsImgStorage.builder()
                        .originUploadName(originName)
                        .savedName(savedName)
                        .savedPath(savedPath)
                        .absolutePath(absolutePath)
                        .build();

                try {
                    files.transferTo(new File(absolutePath));
                } catch (IOException e) {
                    try {
                        String os = System.getProperty("os.name").toLowerCase();
                        String command;
                        if (os.startsWith("win")) {
                            command = "icacls " + absolutePath + "/grant Everyone:(R,W)";
                        } else if (os.startsWith("linux")) {
                            command = "chmod -R 755 " + absolutePath;
                        } else {
                            throw new RuntimeException("설정된 환경이 아닙니다.");
                        }

                        Process process = Runtime.getRuntime().exec(command);
                        int exitCode = process.waitFor();
                        if (exitCode != 0) {
                            throw new IOException("권한 수정 실패");
                        }
                    } catch (InterruptedException e2) {
                        e2.printStackTrace();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }

                ProductsImgStorage savedImg = pImgStorageRepository.save(storage);
                return savedImg.getSavedPath();
        }
    }

    private ManagementItemForm mapDataConversionNewItemForm(Map<String, Object> data) {
        ManagementItemForm newItemForm = new ManagementItemForm();
        if (data.get("code_origin") != null) {
            newItemForm.setCode(data.get("code_origin").toString());
        }
        newItemForm.setName(data.get("name").toString());
        newItemForm.setKind(data.get("kind").toString().charAt(0));
        newItemForm.setPrice(data.get("price").toString());
        newItemForm.setContents(data.get("contents").toString());
        if (data.get("image") != null) {
            newItemForm.setImage(data.get("image").toString());
        }
        newItemForm.setSizeSt(data.get("sizeSt").toString());
        newItemForm.setSizeEt(data.get("sizeEt").toString());
        newItemForm.setQuantity(Integer.parseInt(data.get("quantity").toString()));
        newItemForm.setUseyn(data.get("useyn").toString().charAt(0));
        newItemForm.setIndate(Date.valueOf(LocalDateTime.now().toLocalDate()));
        newItemForm.setProdRecsStatus(Integer.parseInt(data.get("productsRecsStatus").toString()));
        return newItemForm;
    }
}
