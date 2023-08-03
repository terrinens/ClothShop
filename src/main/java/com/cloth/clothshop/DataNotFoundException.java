package com.cloth.clothshop;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Entity Not Found")
public class DataNotFoundException extends RuntimeException {
	
	//실행예외 : 실행시 오류
	//파일을 찾을 수 없을때 DetailNotFoundException 발생
	private static final long serialVersionUID = 1L;
	
	public DataNotFoundException(String message) {
		
		super(message);
	}
}
