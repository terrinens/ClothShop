package com.cloth.clothshop.Management;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class ManagementNewItemForm {

    private String code;

    @NotEmpty(message = "상품 이름은 필수 입력 사항 입니다.")
    private String name;

    @NotEmpty(message = "상품 분류는 필수 선택 사항 입니다.")
    private char kind;

    private String price1;

    private String price2;

    private String conent;

    private String image;

    private String sizeSt;

    private String sizeEt;

    private int quantity;

    @NotEmpty(message = "상품 전시 여부는 필수 선택 사항 입니다.")
    private char useyn;

    private Date indate;
}
