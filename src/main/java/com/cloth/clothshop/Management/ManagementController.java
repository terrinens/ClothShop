package com.cloth.clothshop.Management;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberService;
import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.Products.ProductsService;
import com.cloth.clothshop.RepeatCode.Management_RepeatCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller @RequiredArgsConstructor
@RequestMapping("/management") @Slf4j
public class ManagementController {

    private final MemberService mService;
    private final ProductsService pService;

    @GetMapping("/allitem")
    public String managementProudcts(Model model, ManagementNewItemForm managementNewItemForm,
                                   @RequestParam (value = "page", defaultValue = "0") String page,
                                   @RequestParam (value = "option", defaultValue = "") String option,
                                   @RequestParam (value = "keyword", defaultValue = "") String keyword
    ) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Products> paging = pService.managementGetAutoPaging(requestParam);

        model.addAttribute("paging", paging);
        model.addAttribute("targetForm", managementNewItemForm);

        return "management/member_management";
    }

    @PostMapping("/item/new")
    public String managementNewItem(@ModelAttribute ManagementNewItemForm mnewItemForm) {

        pService.managementNewProductsItem(mnewItemForm);

        return "redirect:/management/allitem";
    }

    @GetMapping("/member")
    public String managementMember(Model model, ManagementMemberForm managementMemberForm,
                                   @RequestParam (value = "page", defaultValue = "0") String page,
                                   @RequestParam (value = "option", defaultValue = "") String option,
                                   @RequestParam (value = "keyword", defaultValue = "") String keyword
                                   ) {

        Object[] requestParam = new Object[]{page, option, keyword};
        Page<Member> paging = mService.managementGetAutoPaging(requestParam);

        model.addAttribute("paging", paging);
        model.addAttribute("targetForm", managementMemberForm);

        return "management/member_management";
    }

    @PostMapping("/member/modify")
    public String managementMemberModify(ManagementMemberForm mmForm, Principal principal) {

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
