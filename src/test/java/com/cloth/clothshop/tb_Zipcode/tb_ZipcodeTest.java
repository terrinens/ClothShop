package com.cloth.clothshop.tb_Zipcode;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**@deprecated */
@SpringBootTest
class tb_ZipcodeTest {
	
	@Autowired
	tb_ZipcodeRepository zipcodeRepository;
	
	@Test
	void temporarilyzipcode() {

		for (int i = 1; i < 5; i++) {
			
			String num4 = String.valueOf(i) + String.valueOf(i) + String.valueOf(i) + String.valueOf(i); 
			
			tb_Zipcode zip = new tb_Zipcode();
			
			zip.setZipcode(num4);
			zip.setSido("서울" + num4 + "시");
			zip.setGugum("서울" + num4 + "구");
			zip.setDong("서울" + num4 + "동");
			zip.setBungil("서울" + num4 + "길");
			
			zipcodeRepository.save(zip);
		}
	}
}
