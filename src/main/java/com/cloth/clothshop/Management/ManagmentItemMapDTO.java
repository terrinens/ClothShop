package com.cloth.clothshop.Management;

import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@Getter @ToString
public class ManagmentItemMapDTO {
    Map<String, Object> formData;
    Map<String, Object> searchData;
    Map<String, MultipartFile> imgData;

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
