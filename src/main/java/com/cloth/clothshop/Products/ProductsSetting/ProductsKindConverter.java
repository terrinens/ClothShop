package com.cloth.clothshop.Products.ProductsSetting;

import com.cloth.clothshop.DataNotFoundException;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class ProductsKindConverter implements AttributeConverter<ProductsKind, Character> {

    @Override
    public Character convertToDatabaseColumn(ProductsKind attribute) {

        return attribute.getKind();
    }

    @Override
    public ProductsKind convertToEntityAttribute(Character dbData) {

        return switch (dbData) {
            case 'A' -> ProductsKind.A;
            case 'B' -> ProductsKind.B;
            case 'C' -> ProductsKind.C;
            case 'D' -> ProductsKind.D;
            case 'E' -> ProductsKind.E;
            case 'F' -> ProductsKind.F;
            default -> throw new DataNotFoundException("분류 존재하지 않음");
        };
    }
}
