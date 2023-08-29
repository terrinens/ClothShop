package com.cloth.clothshop.Products;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Table(name = "ProductsImgStorage")
@Entity @Getter
public class ProductsImgStorage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "img_id")
    private Long id;

    private String originUploadName;

    private String savedName;

    private String savedPath;

    @Builder
    public ProductsImgStorage(long id, String originUploadName, String savedName, String savedPath) {
        this.id = id;
        this.originUploadName = originUploadName;
        this.savedName = savedName;
        this.savedPath = savedPath;
    }
}
