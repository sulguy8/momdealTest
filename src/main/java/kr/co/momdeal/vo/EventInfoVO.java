package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import kr.co.momdeal.validator.ValidImage;
import lombok.Data;

@Alias("evi")
@Data
public class EventInfoVO {

	private Integer eviNum;
	@NotNull
	private String eviStatus;
	private String eviTitle;
	private String eviContent;
	@NotNull
	private String eviSelectionLogic;
	@NotNull
	private String eviMission;
	@NotNull
	private Integer eviType;
	private String eviImg1Name;
	@ValidImage
	private MultipartFile eviImg1;
	private String eviImg2Name;
	@ValidImage
	private MultipartFile eviImg2;
	private String eviImg3Name;
	@ValidImage
	private MultipartFile eviImg3;
	private String eviImg4Name;
	@ValidImage
	private MultipartFile eviImg4;
	private String eviImg5Name;
	@ValidImage
	private MultipartFile eviImg5;
	private String eviImg6Name;
	@ValidImage
	private MultipartFile eviImg6;
	private String eviImg7Name;
	@ValidImage
	private MultipartFile eviImg7;
	private String eviImg8Name;
	@ValidImage
	private MultipartFile eviImg8;
	private String eviImg9Name;
	@ValidImage
	private MultipartFile eviImg9;
	private String eviImg10Name;
	@ValidImage
	private MultipartFile eviImg10;
	@NotNull
	private Integer eviRecruitMaxnum;
	@NotNull
	private String eviRecruitSdat;
	@NotNull
	private String eviRecruitEdat;
	@NotNull
	private String eviWriteSdat;
	@NotNull
	private String eviWriteEdat;
	@NotNull
	private String eviAnnounceDat;
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