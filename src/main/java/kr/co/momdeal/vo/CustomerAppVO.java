package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cua")
@Data
public class CustomerAppVO {

	private Integer cuaNum;
	@NotNull
	private Integer cuiNum;
	private String cuaToken;
	private String cuaType;
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