package com.cloth.clothshop.Member;

import com.cloth.clothshop.Management.ManagementMemberForm;
import com.cloth.clothshop.Products.Products;
import com.cloth.clothshop.RepeatCode.Management_RepeatCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository mRepository;
    private final PasswordEncoder pEncoder;
    private final Management_RepeatCode managementRepeatCode;

    public void memberSignup(String id, String pwd, String name, String address, String tel) {

        Member member = new Member();

        /*member.setZipcode(zipcode);*/
        member.setId(id);
        member.setPwd(pEncoder.encode(pwd));
        member.setName(name);
        member.setAddress(address);
        member.setTel(tel);

        mRepository.save(member);
    }

    public void adminSignup(String id, String pwd, String name, String address, String tel, String role) {

        Member member = new Member();

        /*member.setZipcode(zipcode);*/
        member.setId(id);
        member.setPwd(pEncoder.encode(pwd));
        member.setName(name);
        member.setAddress(address);
        member.setTel(tel);
        member.setRole(role);

        mRepository.save(member);
    }

    @Transactional
    public void ManagementMemberModify(ManagementMemberForm mmForm) {

        mRepository.managementModify(mmForm.getId(), mmForm.getPwd(),
                mmForm.getName(), mmForm.getRole(), mmForm.getAddress(),
                mmForm.getTel());
    }

    public Page<Member> managementGetAutoPaging(Model model, Object[] requestParamArray) {

        String targetRCN = MemberRepository.class.getName();
        String sortBenchmark = "id";
        Page<Member> memberPage = managementRepeatCode.autoWritePaging(model, targetRCN, sortBenchmark, requestParamArray);

        return memberPage;
    }

    public Page<Member> managementGetAutoPagingAjax(Object[] requestParamArray) {

        String targetRCN = MemberRepository.class.getName();
        String sortBenchmark = "id";
        Page<Member> memberPage = managementRepeatCode.autoWritePagingAjax(targetRCN, sortBenchmark, requestParamArray);

        return memberPage;
    }

    public Member memberSearch(String id) {

        Optional<Member> optionalMember = mRepository.findMemberById(id);

        if (optionalMember.isPresent()) {

            Member member = optionalMember.get();

            return member;
        } else {

            return null;
        }
    }

    public  void memberDelete(String id) {

        mRepository.delete(memberSearch(id));
    }
}


