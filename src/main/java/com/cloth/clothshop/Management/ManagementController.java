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
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller @RequiredArgsConstructor
@RequestMapping("/management") @Slf4j
public class ManagementController {

    private final MemberService mService;
    private final ProductsService pService;
    private final Management_RepeatCode managementRepeatCode;

    @GetMapping("/allitem")
    public String managementAllItem(Model model, ManagementNewItemForm mnewItemForm, HttpServletRequest request) {

        int page;
        if (request.getParameter("page") == null) {

            page = 0;
        } else {

            page = Integer.parseInt(request.getParameter("page"));
        }

        String option = request.getParameter("option");
        String keyword = request.getParameter("keyword");

        Page<Products> paging = pService.managementGetProductsPage(page, option, keyword);

        model.addAttribute("itemPaging", paging);
        model.addAttribute("mnewItemForm", mnewItemForm);

        return "management/allitem_management";
    }

    @PostMapping("/item/new")
    public String managementNewItem(@ModelAttribute ManagementNewItemForm mnewItemForm) {

        pService.managementNewProductsItem(mnewItemForm);

        return "redirect:/management/allitem";
    }

/*    @GetMapping("/member")
    public String managementMember(Model model, ManagementMemberForm mmForm, HttpServletRequest request) {

        int page;
        if (request.getParameter("page") == null) {

            page = 0;
        } else {

            page = Integer.parseInt(request.getParameter("page"));
        }

        String option = request.getParameter("option");
        String keyword = request.getParameter("keyword");

        Page<Member> paging = mService.managementGetMemberList(page, option, keyword);

        model.addAttribute("memberList", paging);
        model.addAttribute("mmForm", mmForm);

        return "management/member_management";
    }*/

    @GetMapping("/member")
    public String managementMember(Model model, ManagementMemberForm mmForm, HttpServletRequest request) {

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String targetServiceClass = mService.getClass().getName();
        String targetServiceClassMethod = "managementGetMemberList";

        managementRepeatCode.managementPaging(model, mmForm.getClass(), Member.class, request, targetServiceClass, targetServiceClassMethod);

        stopWatch.stop();
        long msg = stopWatch.getTotalTimeNanos();
        log.info("리펙트 코드 측정 나노초 : " + msg);

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
