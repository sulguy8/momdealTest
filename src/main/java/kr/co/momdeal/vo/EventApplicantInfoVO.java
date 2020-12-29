package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("eai")
@Data
public class EventApplicantInfoVO {

	private Integer eaiNum;
	@NotNull
	private String eaiStatus;
	private String eaiTitle;
	private String eaiContent;
	@NotNull
	private String eaiMission;
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