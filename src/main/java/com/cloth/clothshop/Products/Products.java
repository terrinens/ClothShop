package com.cloth.clothshop.Products;

import com.cloth.clothshop.Management.Form.ManagementItemForm;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKind;
import com.cloth.clothshop.Products.ProductsSetting.ProductsKindConverter;
import com.cloth.clothshop.Products.ProductsSetting.ProductsRecsStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity @Setter @Getter
@DynamicInsert
@DynamicUpdate
public class Products {

/*	@Id
	@Column(name = "PK_products_products_code")
	@GeneratedValue(generator = "ProudctsCodeGenerator")
	@GenericGenerator(
			name = "ProudctsCodeGenerator",
			strategy = "ProudctsCodeGenerator",
			parameters = {
					@Parameter(name = "kind_value", value = "kind"),
					@Parameter(name = "auto_int_value", value = "1000"),
					@Parameter(name = "incr_value", value = "1")
			}
	)
	private String code;*/

    @Id
    @Column(name = "PK_products_products_code")
    @GeneratedValue(strategy = GenerationType.UUID)
    private String code;

    @Column(name = "products_name", length = 100)
    private String name;

    @Column(name = "products_kind")
    @Convert(converter = ProductsKindConverter.class)
    @Enumerated(EnumType.STRING)
    private ProductsKind kind;

    public void setKind(char kind) {
        this.kind = ProductsKind.fromChar(kind);
    }

    @Column(name = "products_price", length = 10)
    private String price;

    @Column(name = "products_conent", length = 1000)
    private String contents;

    @Lob
    @Column(name = "products_image", length = 50)
    private String image;

    @Column(length = 5)
    private String sizeSt;

    @Column(length = 5)
    private String sizeEt;

    @Column(name = "products_quantity", length = 5)
    private int quantity;

    @Column(length = 1)
    private char useyn;

    @Column
    @CreatedDate
    @CreationTimestamp
    private Date indate;

    /**prodRecsStatus = ProductRecommendationsStatus 0 OR 1 Only*/
    @Column @ColumnDefault("'0'")
    @Enumerated(EnumType.ORDINAL)
    private ProductsRecsStatus prodRecsStatus;
    public void setProductsRecsStatus(int status) {
        this.prodRecsStatus = ProductsRecsStatus.fromStatus(status);
    }

    @SuppressWarnings("UnusedReturnValue")
    public Products managementNewItemSave(ManagementItemForm newItemForm) {
        this.name = newItemForm.getName();
        this.kind = ProductsKind.fromChar(newItemForm.getKind());
        this.price = newItemForm.getPrice();
        this.contents = newItemForm.getContents();
        this.image = newItemForm.getImage();
        this.sizeSt = newItemForm.getSizeSt();
        this.sizeEt = newItemForm.getSizeEt();
        this.quantity = newItemForm.getQuantity();
        this.useyn = newItemForm.getUseyn();
        this.prodRecsStatus = ProductsRecsStatus.fromStatus(newItemForm.getProdRecsStatus());
        return this;
    }
}
