package com.cloth.clothshop.tb_Zipcode;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

/**@deprecated 삭제할 예정. 우편 번호 검색api를 사용할 예정*/
@Entity @Setter @Getter
public class tb_Zipcode {
	
	@Id @Column(name = "PK_tb_zipcode_zipcode")
	private String zipcode;
	
	@Column (length = 30)
	private String sido;
	
	@Column (length = 30)
	private String gugum;
	
	@Column (length = 30)
	private String dong;
	
	@Column (length = 30)
	private String bungil;
	
/*
	@OneToMany(mappedBy = "zipcode")
	private List<Member> membersList;
*/
}
