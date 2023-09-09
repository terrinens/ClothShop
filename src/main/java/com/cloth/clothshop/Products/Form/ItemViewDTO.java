package com.cloth.clothshop.Products.Form;

import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import lombok.Getter;
import lombok.Setter;

/**대문자를 계속해서 맵핑해서 반환하는건 비효율적이니 다른 방법 찾아볼것.*/
@Getter @Setter
public class ItemViewDTO {

    private char kind0;

    public char getKind0() {
        return  Character.toUpperCase(ProductsKind.getKindChar(this.kind0));
    }

    public void setKind0(char kind0) {
        this.kind0 = Character.toUpperCase(ProductsKind.getKindChar(kind0));
    }

    private char kind1;

    public char getKind1() {
        return Character.toUpperCase(ProductsKind.getKindChar(this.kind1));
    }

    public void setKind1(char kind1) {
        this.kind1 = Character.toUpperCase(ProductsKind.getKindChar(kind1));
    }

    private int pageCount;

    public char[] getCharArray () {
        return new char[]{getKind0(), getKind1()};
    }
}
