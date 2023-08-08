package com.cloth.clothshop.Controller;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
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
    public String managementMember(Model model) {

        List<Member> memberList = mService.memberList();

        model.addAttribute("memberList", memberList);

        return "management/member_management";
    }

    @GetMapping("/member/{id}")
    public String memberDetail(@PathVariable String id, Model model) {

        Optional<Member> mDetail = mService.memberSearch(id);

        model.addAttribute("mDetail", mDetail);

        return "";
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
