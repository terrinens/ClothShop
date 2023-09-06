package com.cloth.clothshop.RepeatCode;

import com.cloth.clothshop.Management.Form.ManagementMemberForm;
import com.cloth.clothshop.Member.Member;
import com.cloth.clothshop.Member.MemberRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Map;
import java.util.Optional;

@SuppressWarnings("CallToPrintStackTrace")
@Configuration
public class Management_RepeatCode {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository mRepository;

    public Management_RepeatCode(PasswordEncoder passwordEncoder, MemberRepository memberRepository) {
        this.passwordEncoder = passwordEncoder;
        this.mRepository = memberRepository;
    }

    public void encoderPwdModify(Map<String, Object> formData, String originPwd) {
        String formDataPWD = formData.get("pwd").toString();
        String formDataModifyPWD = formData.get("pwdModify").toString();

            String id = formData.get("id").toString();
            String name = formData.get("name").toString();
            String role = formData.get("role").toString();
            String address = formData.get("address").toString();
            String tel = formData.get("tel").toString();

            if (formDataModifyPWD.length() >= 4) {
                try {
                    String encodePwd = passwordEncoder.encode(formDataModifyPWD);
                    mRepository.managementModify(id, encodePwd, name, role, address, tel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (formDataPWD.equals(originPwd)){
                try {
                    mRepository.managementModifyNotPWD(id, name, role, address, tel);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    public void encoderPwdModify(Optional<Member> optional, ManagementMemberForm mmForm) {
        String formDataPWD = mmForm.getPwd();

        if (optional.isPresent()) {
            String originPwd = optional.get().getPwd();
            String id = mmForm.getId();
            String name = mmForm.getName();
            String role = mmForm.getRole();
            String address = mmForm.getAddress();
            String tel = mmForm.getTel();

            if (passwordEncoder.matches(formDataPWD, originPwd)) {
                mRepository.managementModifyNotPWD(id, name, role, address, tel);
            } else {
                mRepository.managementModify(id, formDataPWD, name, role, address, tel);
            }
        }
    }
}