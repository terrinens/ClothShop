package com.cloth.clothshop.tb_Zipcode;

import java.util.List;

import com.cloth.clothshop.Member.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

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
