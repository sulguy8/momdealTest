package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cph")
@Data
public class CustomerPointHisVO {

	private Integer cphNum;
	@NotNull
	private Integer cphPnt;
	@NotNull
	private Integer cphType;
	@NotNull
	private String cphDesc;
	private Integer cuiNum;
	private Integer hoiNum;
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