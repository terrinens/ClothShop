package com.cloth.clothshop.Management;

import com.cloth.clothshop.Management.Form.ManagementItemForm;
import com.cloth.clothshop.Management.Form.ManagementMemberForm;
import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberService;
import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.Products.ProductsService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {

    private final MemberService mService;
    private final ProductsService pService;
    private final EntityManager entityManager;

    @GetMapping("/allitem")
    public String managementProudcts(Model model) {
        Page<Products> paging = pService.managementGetPaging(model);
        model.addAttribute("itemPaging", paging);
        return "management/allitem_management";
    }

    @GetMapping("/allitem-Ajax")
    public String managementProudctsAjax(Model model
                                         , @RequestParam(value = "page", defaultValue = "0") String page
                                         , @RequestParam(value = "option", defaultValue = "") String option
                                         , @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        Page<Products> paging = pService.managementGetPaging(model, Integer.parseInt(page), keyword, option);
        model.addAttribute("itemPaging", paging);

        return "management/allitem_management_AjaxResult";
    }

    @PostMapping("/item/new-Ajax")
    public String managementNewItem(@RequestPart("formData") String formData ,
                                    @RequestPart("searchData") String searchData ,
                                    @RequestPart("imgData") Optional<MultipartFile> imgData ,
                                    Model model) {
        ManagementItemForm itemForm = ManagmentItemDTO.stringDataToFormData(formData);
        ManagmentItemDTO.SearchData toSearchData = ManagmentItemDTO.stringSearchDataToSearchData(searchData);

        imgData.ifPresent(file -> itemForm.setImage(pService.saveFile(file)));
        pService.managementNewProductsItem(itemForm);
        entityManager.clear();

        Page<Products> paging = pService.managementGetPaging(model, toSearchData);
        model.addAttribute("itemPaging", paging);

        return "management/allitem_management_AjaxResult";
    }

    /**반드시 MulitpartFile을 Optional 처리 할것!!!!!*/
    @SneakyThrows
    @PostMapping("/item/modify-Ajax")
    public String managementModifyItem(@RequestPart("formData") String formData ,
                                       @RequestPart("searchData") String searchData ,
                                       @RequestPart("imgData") Optional<MultipartFile> imgData ,
                                       Model model) {

        ManagementItemForm itemForm = ManagmentItemDTO.stringDataToFormData(formData);
        ManagmentItemDTO.SearchData toSearchData = ManagmentItemDTO.stringSearchDataToSearchData(searchData);

        imgData.ifPresent(file -> itemForm.setImage(pService.saveFile(file)));
        pService.managementModifyProductsItem(itemForm);
        entityManager.clear();

        Page<Products> paging = pService.managementGetPaging(model, toSearchData);
        model.addAttribute("itemPaging", paging);

        return "management/allitem_management_AjaxResult";
    }

    @DeleteMapping("/item/Delete-Ajax")
    public String managementDeleteItem(@RequestBody ManagmentItemDTO itemMapDTO, Model model) {
        String targetCode = itemMapDTO.getTargetId();
        if (pService.productsItemSearch(targetCode).isPresent()) {
            pService.managementDeleteProductsItem(targetCode);

            int page = itemMapDTO.getSearchDataPage();
            String option = itemMapDTO.getSearchDataOption();
            String Keyword = itemMapDTO.getSearchDataKeyword();

            Page<Products> paging = pService.managementGetPaging(model, page, Keyword, option);
            model.addAttribute("itemPaging", paging);
            return "management/allitem_management_AjaxResult";
        } else {
            return null;
        }
    }

    @GetMapping("/member")
    public String managementMember(Model model, ManagementMemberForm managementMemberForm,
                                   @RequestParam(value = "page", defaultValue = "0") String page,
                                   @RequestParam(value = "option", defaultValue = "") String option,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {
        Page<Member> paging = mService.managementGetPaging(Integer.parseInt(page), option, keyword);

        model.addAttribute("memberPaging", paging);
        model.addAttribute("MMForm", managementMemberForm);

        return "management/member_management";
    }

    /**
     * 현재 사용하는데 이전 member 컨트롤의 값의 의존하고 있음. 해결할것.
     */
    @GetMapping("/member-Ajax")
    public String managementMemberAjax(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") String page,
            @RequestParam(value = "option", defaultValue = "") String option,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Page<Member> paging = mService.managementGetPaging(Integer.parseInt(page), option, keyword);

        model.addAttribute("memberPaging", paging);
        model.addAttribute("page", page);

        return "/management/member_management_AjaxResult";
    }

    @SuppressWarnings("unchecked")
    @PutMapping("/member/modify-Ajax")
    @Transactional
    public String managementMemberModify(@RequestBody Map<String, Object> modifyData, Principal principal, Model model) {
        /*BindingResult bindingResult,*/

        Map<String, Object> formData = (Map<String, Object>) modifyData.get("formData");
        Map<String, Object> serachData = (Map<String, Object>) modifyData.get("searchData");

        String memberId = formData.get("id").toString();
        Optional<Member> optionalMember = mService.memberSearch(memberId);

        if (optionalMember.isPresent()) {
            String originPwd = optionalMember.get().getPwd();
            mService.managementMemberModify(formData, originPwd);
            entityManager.clear();

            modelAajxCommonPaging(serachData, model);
        }
        return "/management/member_management_AjaxResult";
    }

    @SuppressWarnings("unchecked")
    @DeleteMapping("/member/delete-Ajax")
    public String managementMemberDelete(@RequestBody Map<String, Object> delData, Model model) {
        String targetId = delData.get("targetId").toString();
        Map<String, Object> serachData = (Map<String, Object>) delData.get("searchData");

        Optional<Member> optionalMember = mService.memberSearch(targetId);
        if (optionalMember.isPresent()) {
            mService.memberDelete(targetId);
            modelAajxCommonPaging(serachData, model);
        }
        return "/management/member_management_AjaxResult";
    }

    @GetMapping("/order")
    public String managementOrder() {

        return "management/order_management";
    }

    /**반드시 Ajax로 넘겨받은 Object에서 추출한 serachData를 Object로 변환시켜 넘길것
     * Transactional문제로 컨트롤에서 작성함*/
    private void modelAajxCommonPaging(Map<String, Object> serachData, Model model) {
        int page = Integer.parseInt(serachData.get("page").toString());
        String option = serachData.get("option").toString();
        String keyword = serachData.get("keyword").toString();

        Page<Member> paging = mService.managementGetPaging(page, option, keyword);
        model.addAttribute("memberPaging", paging);
        model.addAttribute("page", 0);
    }
}
