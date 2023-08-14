package com.cloth.clothshop.Member.MeberSetting;

import lombok.Getter;

@Getter
public enum MemberRole {
	
	//로그인 후의 사용자의 권한을 적용하는 Enum
	//Enum에 있는 필드는 모두 상수이다. : 값을 수정 할 수 없다.
	ADMIN("ROLE_ADMIN"),
	USER("ROLE_USER");
	
	MemberRole(String value) {
		
		this.value = value;
	}
	
	private String value;
}