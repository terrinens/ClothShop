package com.cloth.clothshop.Member;

import com.cloth.clothshop.Management.Form.ManagementMemberForm;
import com.cloth.clothshop.RepeatCode.Management_RepeatCode;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

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

    /** 반드시 Optional로 체크한 Password값을 넘길것 */
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public void managementMemberModify(Map<String, Object> formData, String originPwd) {
        managementRepeatCode.encoderPwdModify(formData, originPwd);
    }

    @Transactional(Transactional.TxType.REQUIRES_NEW)
    public Page<Member> managementGetPaging(int page, String option, String keyword) {
        Pageable pageable = PageRequest.of(page, 15, Sort.by("id").ascending());
        return mRepository.findByOptionAndKeyword(option, keyword, pageable);
    }

    public Optional<Member> memberSearch(String id) {
        return mRepository.findMemberById(id);
    }

    public void memberDelete(String id) {
        if (memberSearch(id).isPresent()) {
            mRepository.delete(memberSearch(id).get());
        }
    }

    public void dummyMember() {
        Member m1 = new Member();

        m1.setId("admin");
        m1.setPwd("1234");
        m1.setName("어드민");
        m1.setRole("Admin");
        m1.setAddress("admin@admin.com");
        m1.setTel("010-1111-1111");

        adminSignup(m1.getId(), m1.getPwd(), m1.getName(), m1.getAddress(), m1.getTel(), m1.getRole());

        for (int i = 1; i < 100; i++) {

            Member m2 = new Member();

            int rendomNum1 = generateRandomNumbers();
            int rendomNum2 = generateRandomNumbers();

            m2.setId("user" + i);
            m2.setPwd("1234");
            m2.setName("유저" + i);
            m2.setAddress("user" + i + "@user.com");
            m2.setTel("010-" + rendomNum1 + "-" + rendomNum2);

            memberSignup(m2.getId(), m2.getPwd(), m2.getName(), m2.getAddress(), m2.getTel());
        }
    }

    private static int generateRandomNumbers() {
        Random random = new Random();
        return 1000 + random.nextInt(9000);
    }
}


