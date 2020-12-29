package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cel")
@Data
public class CustomerEasyLoginVO {

	private Integer celNum;
	private String celType;
	private String celId;
	private Integer cuiNum;
	private String celEmail;
	private String celName;
	private String credat;
	private String cretim;
	@NotNull
	private Integer creusr;
	private String moddat;
	private String modtim;
	@NotNull
	private Integer modusr;
	@NotNull
	private String active;
	private String orders;
}