package com.cloth.clothshop.Management.Form;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Date;
import java.time.LocalDateTime;

@Getter @Setter @ToString
public class ManagementItemForm {
    //JS로 valid 체크를 하므로 메세지 없앰
    private char kind;

    private String code;

    /**code_origin 값이 존재할시 자동적으로 할당됨 : 코드값을 수정하지 못하도록 조치한 설정*/
    public String getCode() {
        if (!code_origin.isEmpty()) {
            return code = code_origin;
        } else {
            return code;
        }
    }

    /**DTO 통합을 위해 추가함 가시성을 위해 Getter를 사용하지 못하도록 설정*/
    @Getter(AccessLevel.NONE)
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

    public Date getIndate() {
        if (indate == null) {
            return Date.valueOf(LocalDateTime.now().toLocalDate());
        }
        return indate;
    }

    private int prodRecsStatus;
}
