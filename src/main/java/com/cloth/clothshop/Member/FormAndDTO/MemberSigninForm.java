package com.cloth.clothshop.Member.FormAndDTO;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @Valid
public class MemberSigninForm {
    @NotEmpty(message = "아이디를 확인 해주세요")
    private String id;
    @NotEmpty(message = "비밀번호를 확인 해주세요")
    private String pwd;
}
