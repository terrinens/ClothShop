package com.cloth.clothshop.Products;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QProductsImgStorage is a Querydsl query type for ProductsImgStorage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProductsImgStorage extends EntityPathBase<ProductsImgStorage> {

    private static final long serialVersionUID = 925801447L;

    public static final QProductsImgStorage productsImgStorage = new QProductsImgStorage("productsImgStorage");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath originUploadName = createString("originUploadName");

    public final StringPath savedName = createString("savedName");

    public final StringPath savedPath = createString("savedPath");

    public QProductsImgStorage(String variable) {
        super(ProductsImgStorage.class, forVariable(variable));
    }

    public QProductsImgStorage(Path<? extends ProductsImgStorage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProductsImgStorage(PathMetadata metadata) {
        super(ProductsImgStorage.class, metadata);
    }

}

