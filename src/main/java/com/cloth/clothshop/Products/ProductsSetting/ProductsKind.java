package com.cloth.clothshop.Products.ProductsSetting;

import com.cloth.clothshop.DataNotFoundException;
import lombok.Getter;
import org.springframework.security.access.method.P;

@Getter
public enum ProductsKind {

    /* a,b = 짧,긴상체옷, c,d = 짧,긴바지, e,f = 짧,긴치마 */
    A('a'), B('b'), C('c'), D('d'), E('e'), F('f');
    private final char kind;

    ProductsKind(char kind) {
        this.kind = kind;
    }

    /**Enum 타입에 맞는 값 리턴 시켜주는 메서드*/
    public static ProductsKind getKind(char kind) {
        for (ProductsKind productsKind : ProductsKind.values()) {
            if (Character.toUpperCase(productsKind.getKind()) == Character.toUpperCase(kind)) {
                return productsKind;
            }
        }
        throw new DataNotFoundException("찾는 kind값 없음");
    }

    public static char getKindChar (char kind) {
        for (ProductsKind productsKind : ProductsKind.values()) {
            if (Character.toUpperCase(productsKind.getKind()) == Character.toUpperCase(kind)) {
                return productsKind.getKind();
            }
        }
        throw new DataNotFoundException("찾는 kind값 없음");
    }

    public static ProductsKind fromChar(char kind) {
        return switch (Character.toUpperCase(kind)) {
            case 'A' -> A;
            case 'B' -> B;
            case 'C' -> C;
            case 'D' -> D;
            case 'E' -> E;
            case 'F' -> F;
            default -> throw new DataNotFoundException("찾는 kind값 없음");
        };
    }
}
