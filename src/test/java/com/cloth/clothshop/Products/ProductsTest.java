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
import java.util.concurrent.TimeUnit;

@SpringBootTest
class ProductsTest {

    @Autowired
    ProductsRepository productsRepository;
    @Autowired
    ProductsService productsService;

    @Test
    void tempProducts2() throws Exception {
        char[] kindValue = {'a', 'b', 'c', 'd', 'e', 'f'};
        String[] productName = {"반팔", "긴팔", "반바지", "긴바지", "단치마", "장치마"};

        Random random = new Random();
        int c = 1;
        for (int t = 0, fe = 0; t < 20; t++) {
            for (int i = 0; i < kindValue.length; i++) {
                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Products product = new Products();

                product.setName(productName[i] + c);
                product.setKind(kindValue[i]);
                product.setContents("평범한 " + productName[i] + c + "입니다.");

                int priceThousand = random.nextInt(81) + 20;
                String price = String.format("%d000", priceThousand);
                product.setPrice(price);

                product.setImage(productsService.saveFile(testFile(fe)));

                product.setSizeSt("XS");
                product.setSizeEt("XL");
                product.setQuantity(random.nextInt(100) + 1);
                product.setUseyn('Y');
                product.setProductsRecsStatus(0);
                productsRepository.save(product);
                if (fe >= 17) {
                    fe = 0;
                } else {
                    fe++;
                }
            }
            c++;
        }
    }

    private MultipartFile testFile(int i) throws Exception {
        String userDir = System.getProperty("user.dir");
        String[] imagePath = getStrings(userDir);

        String imgPathStr = imagePath[i];
        System.out.println("받은 i값으로 할당완료 { " + imgPathStr + " }");
        String originFileName = imgPathStr.substring(imgPathStr.lastIndexOf("/") + 1);
        System.out.println("파일 이름 추출 완료 { " + originFileName + " }");
        int lastDotIndex = originFileName.lastIndexOf(".");
        if (lastDotIndex != -1) {
            originFileName = originFileName.substring(0, lastDotIndex);
        }
        System.out.println("확장자 컷팅 완료 { " + originFileName + " }");
        String fileExtend = imagePath[i].substring(imagePath[i].lastIndexOf("."));
        Path path = Paths.get(imagePath[i]);
        byte[] bytes = Files.readAllBytes(path);
        System.out.println(" { 이미지 추가 중" + " }");
        return new MockMultipartFile(originFileName, originFileName, fileExtend, bytes);
    }

    private static String[] getStrings(String userDir) {
        String staticRoot = "/src/main/resources/static";
        String relativePath = "/cloth_imgs/";
        String resultRoot = userDir + staticRoot + relativePath;
        return new String[]{
                resultRoot + "sleeve_short1.jpg"
                , resultRoot + "sleeve_long1.jpg"
                , resultRoot + "pants_short1.jpg"
                , resultRoot + "pants_long1.jpg"
                , resultRoot + "skirt_short1.jpg"
                , resultRoot + "skirt_long1.jpg"
                , resultRoot + "sleeve_short2.jpg"
                , resultRoot + "sleeve_long2.jpg"
                , resultRoot + "pants_short2.jpg"
                , resultRoot + "pants_long2.jpg"
                , resultRoot + "skirt_short2.jpg"
                , resultRoot + "skirt_long2.jpg"
                , resultRoot + "sleeve_short3.jpg"
                , resultRoot + "sleeve_long3.jpg"
                , resultRoot + "pants_short3.jpg"
                , resultRoot + "pants_long3.jpg"
                , resultRoot + "skirt_short3.jpg"
                , resultRoot + "skirt_long3.jpg"
        };
    }

    @Test
    public void osTest() {
        String os = System.getProperty("os.name").toLowerCase();
        System.out.println("환경 테스트? { " + os + " }");
    }
}
