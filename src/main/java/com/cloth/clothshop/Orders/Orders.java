package com.cloth.clothshop.Orders;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@Entity @Getter @Setter
public class Orders {
	
	@Id @GeneratedValue(strategy = GenerationType.SEQUENCE) @Column(name = "PK_order_seq")
	private int seq;
	
	@Column(length = 20) @JoinColumn(name = "PK_products_products_code")
	//products-code
	private String product_code;
	
	@JoinColumn(name = "PK_member_id", nullable = false)
	//member-id
	private String id;
	
	@Column(length = 5)
	//주문 수량
	private String product_size;
	
	@Column(length = 5)
	private String quantity;
	
	@Column(length = 1)
	private char result;
	
	@Column @CreatedDate @CreationTimestamp
	private Date indate;
}
