package com.cloth.clothshop.Member;

import com.cloth.clothshop.Management.ManagementMemberForm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

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

    public Page<Member> managementGetMemberList(int page, String keyword) {


        Page<Member> memberList = mRepository.managementMemberList();

        return memberList;
    }

/*    public Page<Question> getList(int page, String kw) {

        //pageble 객체에 특정 컬럼을 정렬할 객체를 생성해서 인자로 넣어줌
        //주의 springframework.data.domin.sort이다.


        //page : 클라이언트에서 파라메터로 요청한 페이지 번호
        //10 : 한 페이지에서 출력할 레코드 수
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());

        Page<Question> pageQuestion = questionRepostiroy.findAllByKeyword(kw, pageable);

        return pageQuestion;
    }*/


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


