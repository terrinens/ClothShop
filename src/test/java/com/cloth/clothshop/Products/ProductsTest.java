package com.cloth.clothshop.Products;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

@SpringBootTest
class ProductsTest {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductsService productsService;

    @Test
    void tempProducts2() throws Exception {
        char[] kindValue = {'a', 'b', 'c', 'd', 'e', 'f'};
        String[] productName = {"반팔", "긴팔", "반바지", "긴바지", "짧은치마", "긴치마"};

        Random random = new Random();
        int c = 1;
        for (int t = 0; t < 20; t++) {
            for (int i = 0; i < kindValue.length; i++) {
                Products product = new Products();

                product.setName(productName[i] + c);
                product.setKind(kindValue[i]);
                product.setContents("평범한 " + productName[i] + c + "입니다.");

                int priceThousand = random.nextInt(81) + 20;
                String price = String.format("%d000", priceThousand);
                product.setPrice(price);

                product.setImage(productsService.saveFile(testFile(i)));
                product.setSizeSt("XS");
                product.setSizeEt("XL");
                product.setQuantity(random.nextInt(100) + 1);
                product.setUseyn('Y');
                product.setProductsRecsStatus(0);
                productsRepository.save(product);
            }
            c++;
        }
    }

    private MultipartFile testFile(int i) throws Exception {
        String [] imagePath = {
                "C:\\BackEndProj\\ClothShop\\src\\main\\resources\\static\\cloth_imgs\\sleeve_short1.jpg"
                , "C:\\BackEndProj\\ClothShop\\src\\main\\resources\\static\\cloth_imgs\\sleeve_long1.jpg"
                , "C:\\BackEndProj\\ClothShop\\src\\main\\resources\\static\\cloth_imgs\\pants_short1.jpg"
                , "C:\\BackEndProj\\ClothShop\\src\\main\\resources\\static\\cloth_imgs\\pants_long1.jpg"
                , "C:\\BackEndProj\\ClothShop\\src\\main\\resources\\static\\cloth_imgs\\skirt_short1.jpg"
                , "C:\\BackEndProj\\ClothShop\\src\\main\\resources\\static\\cloth_imgs\\skirt_long1.jpg"
        };
        String imgPathStr = imagePath[i];
        System.out.println("받은 i값으로 할당완료 { " + imgPathStr + " }");
        String originFileName = imgPathStr.substring(imgPathStr.lastIndexOf("\\") + 1);
        System.out.println("파일 이름 추출 완료 { " + originFileName + " }");
        int lastDotIndex = originFileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            originFileName = originFileName.substring(0, lastDotIndex);
        }
        System.out.println("확장자 컷팅 완료 { " + originFileName + " }");
        String fileExtend = imagePath[i].substring(imagePath[i].lastIndexOf("."));
        Path path = Paths.get(imagePath[i]);
        byte[] bytes = Files.readAllBytes(path);
        System.out.println("완성값? { " + new MockMultipartFile(originFileName, originFileName, fileExtend, bytes).getName() + " }");
        System.out.println("완성값? { " + new MockMultipartFile(originFileName, originFileName, fileExtend, bytes).getContentType() + " }");
        System.out.println("완성값? { " + new MockMultipartFile(originFileName, originFileName, fileExtend, bytes).getSize() + " }");

        return new MockMultipartFile(originFileName, originFileName, fileExtend, bytes);
    }

    @Test
    public void osTest() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("환경 테스트? { " + os + " }");
    }
}
