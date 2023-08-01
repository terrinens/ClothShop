package com.cloth.clothshop.tb_Zipcode;

import org.springframework.stereotype.Service;

@Service
public class tb_ZipcodeService {
	
	tb_ZipcodeRepository zipcodeRepository;
	
	public void insertZipcode (String zipcode, String sido, String gugum, String dong, String bungil) {
		
		tb_Zipcode tb_Zipcode = new tb_Zipcode();
		tb_Zipcode.setZipcode(zipcode);
		tb_Zipcode.setSido(sido);
		tb_Zipcode.setGugum(gugum);
		tb_Zipcode.setDong(dong);
		tb_Zipcode.setBungil(bungil);
		
		zipcodeRepository.save(tb_Zipcode);
	}
}
