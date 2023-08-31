package com.cloth.clothshop.Member;

import com.cloth.clothshop.Management.ManagementMemberForm;
import com.cloth.clothshop.RepeatCode.Management_RepeatCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.event.TransactionalEventListener;
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

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void managementMemberModify(ManagementMemberForm mmForm) {
        Optional<Member> member = mRepository.findMemberById(mmForm.getId());
        managementRepeatCode.encoderPwdModify(member, mmForm);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void managementMemberModify(Map<String, Object> formData, String originPwd) {
        managementRepeatCode.encoderPwdModify(formData, originPwd);
    }

    /**쓰다보니 만든 코드는 복잡하기만 하고 쓸모 없으니 삭제하고 로직을 변경할것.*/
    public Page<Member> managementGetAutoPaging(Model model, Object[] requestParamArray) {
        String targetRCN = MemberRepository.class.getName();
        String sortBenchmark = "id";
        memberPage = managementRepeatCode.autoWritePaging(model, targetRCN, sortBenchmark, requestParamArray);
        return memberPage;
    }

    @Transactional
    public Page<Member> managementGetPagingAjax(int page, String option, String keyword) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("id").ascending());
        memberPage = mRepository.findByOptionAndKeyword(option, keyword, pageable);
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


