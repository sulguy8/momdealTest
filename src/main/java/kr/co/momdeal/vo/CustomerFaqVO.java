package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cuf")
@Data
public class CustomerFaqVO {

	private Integer cufNum;
	@NotNull
	private String cufType;
	@NotNull
	private String cufCategory;
	@NotNull
	private String cufTitle;
	@NotNull
	private String cufContent;
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