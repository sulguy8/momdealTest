package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cai")
@Data
public class CategoryInfoVO {

	private Integer caiNum;
	@NotNull
	private String caiType;
	private String caiDep1;
	private String caiDep2;
	private String caiDep3;
	private String caiDep4;
	private String caiDep5;
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