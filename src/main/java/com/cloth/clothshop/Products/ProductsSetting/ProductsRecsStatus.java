package com.cloth.clothshop.Products.ProductsSetting;

import lombok.Getter;

public enum ProductsRecsStatus {
    no(0), yes(1);
    private final int status;

    ProductsRecsStatus(int status) {
        this.status = status;
    }

    public static ProductsRecsStatus fromStatus(int status) {
        return switch (status) {
            case 0 -> no;
            case 1 -> yes;
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }

    public int getStatus() {
        return this.status;
    }
}
