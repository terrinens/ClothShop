package com.cloth.clothshop.Member;

import com.cloth.clothshop.Management.ManagementMemberForm;
import com.cloth.clothshop.RepeatCode.Management_RepeatCode;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository mRepository;
    private final PasswordEncoder pEncoder;
    private final Management_RepeatCode managementRepeatCode;
    private Page<Member> memberPage;

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

    public void managementMemberModify(ManagementMemberForm mmForm) {
        Optional<Member> member = mRepository.findMemberById(mmForm.getId());

        String formDataPWD = mmForm.getPwd();
        managementRepeatCode.encoderPwdModify(member, mmForm);
    }

    public void managementMemberModify(Map<String, Object> formData) {
        System.out.println("????????");
        /*System.out.println("서비스 modify로 넘어온 값 :::: " + formData);*/
        Optional<Member> member = mRepository.findMemberById(formData.get("id").toString());
        /*System.out.println("서비스 modify에서 member로 바꾼값 :::: " + member.get().getId());*/
        String formDataPWD = formData.get("pwd").toString();
        /*System.out.println("서비스 modify에서 넘어온 pwd :::: " + formDataPWD);*/

        System.out.println("%%%%%%%%%%%%%%%%%%%%");
        managementRepeatCode.encoderPwdModify(member, formData);
        System.out.println("*************************");
    }

    public Page<Member> managementGetAutoPaging(Model model, Object[] requestParamArray) {

        String targetRCN = MemberRepository.class.getName();
        String sortBenchmark = "id";
        memberPage = managementRepeatCode.autoWritePaging(model, targetRCN, sortBenchmark, requestParamArray);

        return memberPage;
    }

    public Page<Member> managementGetAutoPagingAjax(Object[] requestParamArray) {

        String targetRCN = MemberRepository.class.getName();
        String sortBenchmark = "id";
        memberPage = managementRepeatCode.autoWritePagingAjax(targetRCN, sortBenchmark, requestParamArray);

        return memberPage;
    }

    public Member memberSearch(String id) {

        Optional<Member> optionalMember = mRepository.findMemberById(id);

        return optionalMember.orElse(null);
    }

    public  void memberDelete(String id) {

        mRepository.delete(memberSearch(id));
    }
}


