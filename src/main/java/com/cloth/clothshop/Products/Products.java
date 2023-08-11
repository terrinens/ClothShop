package com.cloth.clothshop.Products;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@Entity @Getter @Setter
public class Products {
	
	@Id @Column(name = "PK_products_products_code", length = 20)
	private String code;

	@Column(name = "products_name", length = 100)
	private String name;
	
	@Column(name = "products_kind", length = 1)
	private char kind;
	
	@Column(name = "products_price1", length = 10)
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
	
	@Column @CreatedDate @CreationTimestamp
	private Date indate;
}
