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

    public static ProductsKind getKind(char kind) {

        for (ProductsKind productsKind : ProductsKind.values()) {

            if (Character.toUpperCase(productsKind.getKind()) == Character.toUpperCase(kind)) {

                return productsKind;
            }
        }
        throw new DataNotFoundException("찾는 kind값 없음");
    }

    public static ProductsKind fromChar(char kind) {
        switch (Character.toUpperCase(kind)) {
            case 'A': return A;
            case 'B': return B;
            case 'C': return C;
            case 'D': return D;
            case 'E': return E;
            case 'F': return F;
            default: throw new DataNotFoundException("찾는 kind값 없음");
        }
    }
}
