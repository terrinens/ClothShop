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
import org.springframework.ui.Model;

import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository mRepository;
    private final PasswordEncoder pEncoder;
    private final Management_RepeatCode managementRepeatCode;
    private final Member member = new Member();

    public void memberSignup(String id, String pwd, String name, String address, String tel) {
        /*member.setZipcode(zipcode);*/
        member.setId(id);
        member.setPwd(pEncoder.encode(pwd));
        member.setName(name);
        member.setAddress(address);
        member.setTel(tel);

        mRepository.save(member);
    }

    public void adminSignup(String id, String pwd, String name, String address, String tel, String role) {
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

    /**반드시 Optional로 체크한 Password값을 넘길것*/
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void managementMemberModify(Map<String, Object> formData, String originPwd) {
        managementRepeatCode.encoderPwdModify(formData, originPwd);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Page<Member> managementGetPaging(int page, String option, String keyword) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("id").ascending());
        return mRepository.findByOptionAndKeyword(option, keyword, pageable);
    }

    public Member memberSearch(String id) {
        Optional<Member> optionalMember = mRepository.findMemberById(id);
        return optionalMember.orElse(null);
    }

    public  void memberDelete(String id) {
        mRepository.delete(memberSearch(id));
    }
}


