package com.cloth.clothshop.Products;

import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;
import java.util.Random;

@SpringBootTest
class ProductsTest {
	
	@Autowired
	ProductsRepository productsRepository;
	
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
