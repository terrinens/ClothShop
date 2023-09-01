package com.cloth.clothshop.Products.ProductsSetting;

import lombok.Getter;

@Getter
public enum ProductsRecsStatus {
    notRecommend(0), Recommend(1);
    private final int status;

    ProductsRecsStatus(int status) {
        this.status = status;
    }

    public static ProductsRecsStatus fromStatus(int status) {
        return switch (status) {
            case 0 -> notRecommend;
            case 1 -> Recommend;
            default -> throw new IllegalStateException("Unexpected value: " + status);
        };
    }
}
