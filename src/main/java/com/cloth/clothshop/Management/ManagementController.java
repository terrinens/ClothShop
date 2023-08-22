package com.cloth.clothshop.Management;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberAjaxDTO;
import com.cloth.clothshop.Member.MemberService;
import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.Products.ProductsService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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

    /*ajax 테스트*/
/*    @GetMapping("/member-Ajax")
    public @ResponseBody Map<String, Object> managementMemberAjax(
            Model model, ManagementMemberForm managementMemberForm,
            @RequestParam(value = "page", defaultValue = "0") String page,
            @RequestParam(value = "option", defaultValue = "") String option,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Member> paging = mService.managementGetAutoPagingAjax(requestParam);

        System.out.println(paging.getTotalElements());

        System.out.println("ajax 컨트롤러 호출 성공 !!!");
        System.out.println("ajax 넘어옴 :::: " + keyword);
        System.out.println("ajax 넘어옴 :::: " + option);

        Map<String, Object> responseData = new HashMap<>();
        responseData.put("memberPaging", paging);
        responseData.put("page", page);
        responseData.put("option", option);
        responseData.put("keyword", keyword);

        System.out.println("map 으로 쏨 :::: " + paging.getTotalPages());
        return responseData;
    }*/

    //Json 테스트
    @GetMapping("/member-Ajax") @ResponseBody
    public ModelAndView managementMemberAjax(
            @RequestParam(value = "page", defaultValue = "0") String page,
            @RequestParam(value = "option", defaultValue = "") String option,
            @RequestParam(value = "keyword", defaultValue = "") String keyword) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Member> paging = mService.managementGetAutoPagingAjax(requestParam);

        System.out.println("ajax 컨트롤러 호출 성공 !!!");
        System.out.println("ajax 컨트롤러 토탈 페이지 :::: " + paging.getTotalElements());
        System.out.println("ajax 넘어옴 :::: " + keyword);
        System.out.println("ajax 넘어옴 :::: " + option);

        MappingJackson2JsonView jsonView = new MappingJackson2JsonView();
        ModelAndView modelAndView = new ModelAndView(jsonView);
        modelAndView.addObject("memberPaging", paging);
        modelAndView.addObject("page", page);
        modelAndView.addObject("option", option);
        modelAndView.addObject("keyword", keyword);

        System.out.println("Jackson 으로 쏨 :::: " + paging.getTotalPages());
        return modelAndView;
    }

    @PostMapping("/member/modify")
    public String managementMemberModify(@Valid ManagementMemberForm mmForm, BindingResult bindingResult, Principal principal) {

        String memberId = principal.getName();
        Optional<Member> optionalMember = Optional.ofNullable(mService.memberSearch(memberId));

        if (optionalMember.isPresent()) {

            mService.ManagementMemberModify(mmForm);
        }

        return "redirect:/management/member";
    }

    @GetMapping("/member/delete/{id}")
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
