package com.cloth.clothshop.Member;

import com.cloth.clothshop.Management.ManagementMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository mRepository;
    private final PasswordEncoder pEncoder;

    public Member memberSignup(String id, String pwd, String name, String address, String tel) {

        Member member = new Member();

        /*member.setZipcode(zipcode);*/
        member.setId(id);
        member.setPwd(pEncoder.encode(pwd));
        member.setName(name);
        member.setAddress(address);
        member.setTel(tel);

        mRepository.save(member);

        return member;
    }

    public Member adminSignup(String id, String pwd, String name, String address, String tel, String role) {

        Member member = new Member();

        /*member.setZipcode(zipcode);*/
        member.setId(id);
        member.setPwd(pEncoder.encode(pwd));
        member.setName(name);
        member.setAddress(address);
        member.setTel(tel);
        member.setRole(role);

        mRepository.save(member);

        return member;
    }

    @Transactional
    public void ManagementMemberModify(ManagementMemberForm mmForm) {

        mRepository.managementModify(mmForm.getId(), mmForm.getPwd(),
                mmForm.getName(), mmForm.getRole(), mmForm.getAddress(),
                mmForm.getTel());
    }

    public List managementMemberList() {

        List<Member> memberList = mRepository.findAll();

        return memberList;
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
}


