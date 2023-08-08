package com.cloth.clothshop.Management;

import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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
                                   @RequestParam(value = "keyword", defaultValue = " ") String keyword) {

        List managementMemberList = mService.managementMemberList();

        model.addAttribute("memberList", managementMemberList);
        model.addAttribute("mmForm", mmForm);

        return "management/member_management";
    }

        /*public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page,
                       @RequestParam(value = "kw", defaultValue = "") String kw) {

        //2. 비즈니스 로직처리
        //List<Question> questionList = this.questionRepostiroy.findAll();
        Page<Question> paging = questionService.getList(page, kw);

        //3. 받아온 List를 client로 전송(Model 객체에 저장해서 Client로 전송)
        model.addAttribute("paging", paging);

        paging.getTotalPages();

        return "question_list";
    }*/


    @PostMapping("/member/modify")
    public String managementMemberModify(ManagementMemberForm mmForm, Principal principal) {

        mService.ManagementMemberModify(mmForm);

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
