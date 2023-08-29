package com.cloth.clothshop.Management;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberService;
import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.Products.ProductsService;
import jakarta.validation.Configuration;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {

    private final MemberService mService;
    private final ProductsService pService;

    @GetMapping("/allitem")
    public String managementProudcts(Model model, ManagementNewItemForm managementNewItemForm,
                                     @RequestParam(value = "page", defaultValue = "0") String page,
                                     @RequestParam(value = "option", defaultValue = "") String option,
                                     @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Products> paging = pService.managementGetAutoPaging(model, requestParam);

        model.addAttribute("itemPaging", paging);
        model.addAttribute("itemForm", managementNewItemForm);

        return "management/allitem_management";
    }
    @GetMapping("/allitem-Ajax")
    public String managementProudctsAjax(Model model, ManagementNewItemForm managementNewItemForm,
                                     @RequestParam(value = "page", defaultValue = "0") String page,
                                     @RequestParam(value = "option", defaultValue = "") String option,
                                     @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Products> paging = pService.managementGetAutoPaging(model, requestParam);

        model.addAttribute("itemPaging", paging);
        model.addAttribute("itemForm", managementNewItemForm);

        return "management/allitem_management_AjaxResult";
    }

    @GetMapping("/item/new")
    public String managementNewItem(ManagementNewItemForm mnewItemForm) {

        pService.managementNewProductsItem(mnewItemForm);
        System.out.println("컨트롤 :::: " + mnewItemForm.getKind());

        return "redirect:/management/allitem";
    }

    @GetMapping("/member")
    public String managementMember(Model model, ManagementMemberForm managementMemberForm,
                                   @RequestParam(value = "page", defaultValue = "0") String page,
                                   @RequestParam(value = "option", defaultValue = "") String option,
                                   @RequestParam(value = "keyword", defaultValue = "") String keyword
    ) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Member> paging = mService.managementGetAutoPaging(model, requestParam);

        model.addAttribute("memberPaging", paging);
        model.addAttribute("MMForm", managementMemberForm);

        return "management/member_management";
    }

    @GetMapping("/member-Ajax")
    public String managementMemberAjax(
            Model model,
            @RequestParam(value = "page", defaultValue = "0") String page,
            @RequestParam(value = "option", defaultValue = "") String option,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Object[] requestParamArray = new Object[]{page, option, keyword};
        Page<Member> paging = mService.managementGetAutoPagingAjax(requestParamArray);

        model.addAttribute("memberPaging", paging);
        model.addAttribute("page", page);

        return "/management/member_management_AjaxResult";
    }

    @PostMapping("/member/modify")
    public String managementMemberModify(@RequestBody Map<String, Object> modifyData, Principal principal, Model model) {
        /*BindingResult bindingResult,*/

        Map<String, Object> formData = (Map<String, Object>) modifyData.get("formData");
        Map<String, Object> serachData = (Map<String, Object>) modifyData.get("serachData");

        String memberId = formData.get("id").toString();
        Optional<Member> optionalMember = Optional.ofNullable(mService.memberSearch(memberId));

        if (optionalMember.isPresent()) {
            String page = serachData.get("page").toString();
            String option = serachData.get("option").toString();
            String keyword = serachData.get("keyword").toString();

            Object[] requestParamArray = new Object[]{page, option, keyword};
            Page<Member> paging = mService.managementGetAutoPagingAjax(requestParamArray);

            mService.managementMemberModify(formData);

            model.addAttribute("memberPaging", paging);
            model.addAttribute("page", 0);
        }

        return "/management/member_management_AjaxResult";
    }

    @PostMapping("/member/delete-Ajax")
    public String managementMemberDelete(@PathVariable String id) {
        mService.memberDelete(id);
        return "redirect:/management/member";
    }

    @GetMapping("/order")
    public String managementOrder() {

        return "management/order_management";
    }

    @GetMapping("/recommended")
    public String managementRecommended() {

        return "management/recommended_management";
    }
}
