package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cod")
@Data
public class CommonCodeVO {

	private Integer codNum;
	@NotNull
	private String codType;
	@NotNull
	private String codKey;
	@NotNull
	private String codVal;
	@NotNull
	private String codDesc;
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