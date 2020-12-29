package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("hor")
@Data
public class HotdealRecommendVO {

	@NotNull
	private Integer cuiNum;
	private Integer hoiNum;
	@NotNull
	private Integer hoiRecommendType;
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