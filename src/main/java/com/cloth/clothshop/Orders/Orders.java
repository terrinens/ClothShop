package com.cloth.clothshop.Orders;

import java.sql.Date;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

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
