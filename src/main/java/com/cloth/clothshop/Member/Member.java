package com.cloth.clothshop.Member;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.*;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Date;

@DynamicInsert @DynamicUpdate @Entity @Setter @Getter
public class Member {
	
	@Id @Column(name = "PK_member_id" ,length = 20)
	private String id;
	
	@Column
	private String pwd;
	
	@Column (length = 50)
	private String name;
	
	@Column (length = 20, columnDefinition = "VARCHAR(20)")
	@ColumnDefault("'User'")
	private String role;
	
	/*@ManyToOne(optional = false)
	private tb_Zipcode zipcode;*/
	
	@Column (length = 20)
	private String address;
	
	@Column (length = 13)
	private String tel;

	@Column @CreatedDate @UpdateTimestamp
	private Date indate;
}
