package com.cloth.clothshop.Products.ProductsSetting;

import lombok.Getter;

@Getter
public enum ProductsRecsStatus {
    No(0), Yes(1);
    private final int status;

    ProductsRecsStatus(int status) {
        this.status = status;
    }

    public static ProductsRecsStatus fromStatus(int status) {
        return switch (status) {
            case 0 -> No;
            case 1 -> Yes;
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }
}
