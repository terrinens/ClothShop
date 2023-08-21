package com.cloth.clothshop.Member;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class MemberAjaxDTO {
    private String page;
    private String keyword;
    private String option;
}
