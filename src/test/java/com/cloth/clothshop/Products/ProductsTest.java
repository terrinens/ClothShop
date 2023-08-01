package com.cloth.clothshop.Products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ProductsTest {
	
	@Autowired
	ProductsRepository productsRepository;
	
	@Test
	void temporarilyProducts() {
		
		Products pro1 = new Products();
		Products pro2 = new Products();
		Products pro3 = new Products();
		Products pro4 = new Products();
		Products pro5 = new Products();
		
		pro1.setCode("aaaa1111");
		pro1.setName("반팔");
		pro1.setKind('a');
		pro1.setPrice1("10000");
		pro1.setPrice2("13000");
		pro1.setConent("평범한 반팔입니다.");
		pro1.setImage("이미지 준비중!");
		pro1.setSizeSt("XS");
		pro1.setSizeEt("XL");
		pro1.setQuantity(17);
		pro1.setUseyn('Y');
				
		pro2.setCode("bbbb2222");
		pro2.setName("긴팔");
		pro2.setKind('b');
		pro2.setPrice1("12000");
		pro2.setPrice2("17000");
		pro2.setConent("평범한 긴팔입니다.");
		pro2.setImage("이미지 준비중!");
		pro2.setSizeSt("XS");
		pro2.setSizeEt("XL");
		pro2.setQuantity(4);
		pro2.setUseyn('Y');
		
		pro3.setCode("cccc3333");
		pro3.setName("반바지");
		pro3.setKind('c');
		pro3.setPrice1("4000");
		pro3.setPrice2("7000");
		pro3.setConent("평범한 반바지입니다.");
		pro3.setImage("이미지 준비중!");
		pro3.setSizeSt("XS");
		pro3.setSizeEt("XL");
		pro3.setQuantity(20);
		pro3.setUseyn('Y');
		
		pro4.setCode("dddd4444");
		pro4.setName("긴바지");
		pro4.setKind('d');
		pro4.setPrice1("17000");
		pro4.setPrice2("22000");
		pro4.setConent("평범한 긴바지입니다.");
		pro4.setImage("이미지 준비중!");
		pro4.setSizeSt("XS");
		pro4.setSizeEt("XL");
		pro4.setQuantity(2);
		pro4.setUseyn('Y');
		
		pro5.setCode("eeee5555");
		pro5.setName("치마");
		pro5.setKind('e');
		pro5.setPrice1("23000");
		pro5.setPrice2("34000");
		pro5.setConent("평범한 치마입니다.");
		pro5.setImage("이미지 준비중!");
		pro5.setSizeSt("XS");
		pro5.setSizeEt("XL");
		pro5.setQuantity(32);
		pro5.setUseyn('Y');
		
		productsRepository.save(pro1);
		productsRepository.save(pro2);
		productsRepository.save(pro3);
		productsRepository.save(pro4);
		productsRepository.save(pro5);
	}
}
