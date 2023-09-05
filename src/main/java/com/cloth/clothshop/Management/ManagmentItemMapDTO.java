package com.cloth.clothshop.Management;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@ToString
public class ManagmentItemMapDTO {

    FormData formData;
    Map<String, Object> searchData;
    /**
     * 삭제 메서드를 위한 id값 이니 삭제하지 말것
     */
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


    /**
     * 가져오는데 오류가 발생시 지정된 이름이 정확하게 동일한지 확인할것.
     */
    @Getter @Setter
    static class FormData {
        private char kind;
        private String code_origin;
        private String name;
        private String price;
        private String contents;
        private String sizeSt;
        private String sizeEt;
        private int productsRecsStatus;
        private char useyn;
        private String quantity;
    }

    static FormData stringDataToFormData(String stringData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringData, FormData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Getter @Setter
    static class SearchData {
        private int page;
        private String keyword;
        private String option;
    }

    static SearchData stringSearchDataToSearchData (String stringSearchData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(stringSearchData, SearchData.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
