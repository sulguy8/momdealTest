package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cci")
@Data
public class CrawlingCateInfoVO {

	private Integer cciNum;
	@NotNull
	private Integer cspNum;
	private Integer cciParentNum;
	@NotNull
	private String cciUrl;
	@NotNull
	private String cciKey;
	private String cciDep1;
	private String cciDep2;
	private String cciDep3;
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