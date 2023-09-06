package com.cloth.clothshop.Management.Form;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;

@Getter @Setter @ToString
public class ManagementItemForm {
    //JS로 valid 체크를 하므로 메세지 없앰
    private char kind;

    private String code;

    /**DTO 통합을 위해 추가함*/
    private String code_origin;

    private String name;

    private String contents;

    private String sizeSt;

    private String sizeEt;

    private String price;

    private int quantity;

    private char useyn;

    private String image;

    private Date indate;

    private int prodRecsStatus;
}
