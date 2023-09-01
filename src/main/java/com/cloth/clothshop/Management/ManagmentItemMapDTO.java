package com.cloth.clothshop.Management;

import lombok.Getter;

import java.util.Map;

@Getter
public class ManagmentItemMapDTO {
    Map<String, Object> formData;

    Map<String, Object> searchData;
    public int getSearchDataPage() {
        return Integer.parseInt(searchData.get("page").toString());
    }
    public String getSearchDataKeyword(){
        return searchData.get("keyword").toString();
    }
    public String getSearchDataOption(){
        return searchData.get("option").toString();
    }

    String targetId;
}
