package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("cuq")
@Data
public class CustomerQuestionVO {

	private Integer cuqNum;
	private Integer cuiNum;
	@NotNull
	private String cuqType;
	@NotNull
	private String cuqCategory;
	@NotNull
	private String cuqContent;
	private String cuqAnswer;
	private String cuqAnswerDate;
	private String cuqAnswerTime;
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