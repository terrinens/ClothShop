package com.cloth.clothshop.Management;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller @RequiredArgsConstructor
@RequestMapping("/management")
public class ManagementController {

    private final MemberService mService;

    @GetMapping("/allitem")
    public String managementAllitem() {

        return "management/allitem_management";
    }

    @GetMapping("/member")
    public String managementMember(Model model, ManagementMemberForm mmForm,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "searchOption", defaultValue = "") String Option,
                                   @RequestParam(value = "searchKeyword", defaultValue = "") String keyword) {

        Page<Member> paging = mService.managementGetMemberList(page, Option, keyword);

        System.out.println(Option + " :::: " + keyword);


        model.addAttribute("memberList", paging);
        model.addAttribute("mmForm", mmForm);

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
