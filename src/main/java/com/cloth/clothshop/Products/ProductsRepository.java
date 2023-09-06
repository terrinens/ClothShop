package com.cloth.clothshop.Products;

import com.cloth.clothshop.Products.ProductsQueryDSL.ProductsRepositoryCustom;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Date;

public interface ProductsRepository extends JpaRepository<Products, String>, ProductsRepositoryCustom {

    @Query("select p from Products p where p.kind=:kindOption1 or p.kind=:kindOption2")
    Page<Products> findByKindList(@Param("kindOption1") char kindOption1, @Param("kindOption2") char kindOption2, Pageable pageable);

    @Modifying
    @Query("UPDATE Products SET kind = :kind" +
            ", name = :name, contents = :content" +
            ", sizeSt = :sizeSt, sizeEt = :sizeEt" +
            ", price = :price, quantity = :quantity" +
            ", useyn = :useyn, image = :img" +
            ", indate = :indate  WHERE code = :code")
    void modifyItem(
            @Param("code") String code
            , @Param("kind") ProductsKind kind
            , @Param("name") String name
            , @Param("content") String content
            , @Param("sizeSt") String sizeSt
            , @Param("sizeEt") String sizeEt
            , @Param("price") String price
            , @Param("quantity") int quantity
            , @Param("useyn") char useyn
            , @Param("img") String img
            , @Param("indate") Date indate
    );
}
