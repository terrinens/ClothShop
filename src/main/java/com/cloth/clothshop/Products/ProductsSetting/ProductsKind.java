package com.cloth.clothshop.Products.ProductsSetting;

import com.cloth.clothshop.DataNotFoundException;
import lombok.Getter;

@Getter
public enum ProductsKind {

    /* a,b = 짧,긴상체옷, c,d = 짧,긴바지, e,f = 짧,긴치마 */
    A('a'), B('b'), C('c'), D('d'), E('e'), F('f');
    private final char kind;

    ProductsKind(char kind) {

        this.kind = kind;
    }

    public char getKind() {

        for (ProductsKind productsKind : ProductsKind.values()) {

            if (Character.toUpperCase(productsKind.getKind()) == kind) {

                return this.kind;
            }
        }

        throw new DataNotFoundException("찾는 kind값 없음");
    }
}
