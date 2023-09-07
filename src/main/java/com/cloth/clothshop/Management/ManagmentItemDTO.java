package com.cloth.clothshop.Management;

import com.cloth.clothshop.Management.Form.ManagementItemForm;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class ManagmentItemDTO {

    ManagementItemForm formData;
    Map<String, Object> searchData;
    /** 삭제 메서드를 위한 id값 이니 삭제하지 말것 */
    String targetId;

    public int getSearchDataPage() {
        return Integer.parseInt(searchData.get("page").toString());
    }

    public String getSearchDataKeyword() {
        return searchData.get("keyword").toString();
    }

    public String getSearchDataOption() {
        return searchData.get("option").toString();
    }


    static ManagementItemForm stringDataToFormData(String stringData) {
        if (stringData.isEmpty()) {
            System.out.println(" { " + "dto로 변환중이지만 값이 존재하지 않음." + " }");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringData, ManagementItemForm.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @Setter
    public static class SearchData {
        private int page;
        private String keyword;
        private String option;
    }

    static SearchData stringSearchDataToSearchData(String stringSearchData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringSearchData, SearchData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
