package com.cloth.clothshop.Management.Form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagementMemberForm {

    @NotEmpty(message = "아이디는 필수 입력 사항 입니다.")
    private String id;

    @NotEmpty(message = "비밀번호는 필수 입력 사항 입니다.")
    private String pwd;

    @NotEmpty(message = "성함은 필수 입력 사항 입니다.")
    private String name;

/*
	@NotEmpty(message = "주소는 필수 입력 사항 입니다.")
	private tb_Zipcode zipcode;
*/

    @NotEmpty(message = "핸드폰 번호는 필수 입력 사항 입니다.")
    private String tel;

    @Email
    private String address;

    @NotEmpty(message = "권한은 필수 입력 사항 입니다.")
    private String role;
}
