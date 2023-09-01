package com.cloth.clothshop.Management.Form;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;

@Getter @Setter
public class ManagementItemForm {
    //JS로 valid 체크를 하므로 메세지 없앰
    private char kind;

    private String code;

    private String name;

    private String contents;

    private String sizeSt;

    private String sizeEt;

    private String price;

    private int quantity;

    private char useyn;

    private String image;

    private Date indate;
}
