package com.cloth.clothshop.Products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

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
		Products pro6 = new Products();

		pro1.setName("반팔");
		pro1.setKind('a');
		pro1.setContents("평범한 반팔입니다.");
		pro1.setPrice("10000");
		pro1.setImage("이미지 준비중!");
		pro1.setSizeSt("XS");
		pro1.setSizeEt("XL");
		pro1.setQuantity(17);
		pro1.setUseyn('Y');
				
		pro2.setName("긴팔");
		pro2.setKind('b');
		pro2.setContents("평범한 긴팔입니다.");
		pro2.setPrice("12000");
		pro2.setImage("이미지 준비중!");
		pro2.setSizeSt("XS");
		pro2.setSizeEt("XL");
		pro2.setQuantity(4);
		pro2.setUseyn('Y');
		
		pro3.setName("반바지");
		pro3.setKind('c');
		pro3.setContents("평범한 반바지입니다.");
		pro3.setPrice("4000");
		pro3.setImage("이미지 준비중!");
		pro3.setSizeSt("XS");
		pro3.setSizeEt("XL");
		pro3.setQuantity(20);
		pro3.setUseyn('Y');
		
		pro4.setName("긴바지");
		pro4.setKind('d');
		pro4.setContents("평범한 긴바지입니다.");
		pro4.setPrice("17000");
		pro4.setImage("이미지 준비중!");
		pro4.setSizeSt("XS");
		pro4.setSizeEt("XL");
		pro4.setQuantity(2);
		pro4.setUseyn('Y');
		
		pro5.setName("짧은치마");
		pro5.setKind('e');
		pro5.setContents("평범한 짧은치마입니다.");
		pro5.setPrice("23000");
		pro5.setImage("이미지 준비중!");
		pro5.setSizeSt("XS");
		pro5.setSizeEt("XL");
		pro5.setQuantity(32);
		pro5.setUseyn('Y');

		pro6.setName("긴치마");
		pro6.setKind('f');
		pro6.setContents("평범한 긴치마입니다.");
		pro6.setPrice("29000");
		pro6.setImage("이미지 준비중!");
		pro6.setSizeSt("XS");
		pro6.setSizeEt("XL");
		pro6.setQuantity(12);
		pro6.setUseyn('Y');

		productsRepository.save(pro1);
		productsRepository.save(pro2);
		productsRepository.save(pro3);
		productsRepository.save(pro4);
		productsRepository.save(pro5);
		productsRepository.save(pro6);
	}

	@Test
	void tempProducts2() {

		char[] kindValue = {'a', 'b', 'c', 'd', 'e', 'f'};
		String[] productName = {"반팔", "긴팔", "반바지", "긴바지", "짧은치마", "긴치마"};

		Random random = new Random();

		for (int i = 0; i < kindValue.length; i++) {

			Products product = new Products();

			product.setName(productName[i]);
			product.setKind(kindValue[i]);
			product.setContents("평범한 " + productName[i] + "입니다.");

			int priceThousand = random.nextInt(81) + 20;
			String price = String.format("%d000", priceThousand);
			product.setPrice(price);

			product.setImage("이미지 준비중!");
			product.setSizeSt("XS");
			product.setSizeEt("XL");
			product.setQuantity(random.nextInt(100) + 1);
			product.setUseyn('Y');

			productsRepository.save(product);
		}
	}
}
