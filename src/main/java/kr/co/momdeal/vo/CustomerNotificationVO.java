package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cun")
@Data
public class CustomerNotificationVO {

	private Integer cunNum;
	private Integer cuiNum;
	private Integer hoiNum;
	private Integer hoiType;
	@NotNull
	private Integer caiType;
	@NotNull
	private String cunKeyword;
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