package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import kr.co.momdeal.validator.ValidImage;
import lombok.Data;

@Alias("noi")
@Data
public class NoticeInfoVO {

	private Integer noiNum;
	@NotNull
	private String noiTitle;
	@NotNull
	private String noiContent;
	@NotNull
	private Integer noiViewCnt;
	@NotNull
	private Integer noiShareCnt;
	private String noiImg1Name;
	@ValidImage
	private MultipartFile noiImg1;
	private String noiImg2Name;
	@ValidImage
	private MultipartFile noiImg2;
	private String noiImg3Name;
	@ValidImage
	private MultipartFile noiImg3;
	private String noiImg4Name;
	@ValidImage
	private MultipartFile noiImg4;
	private String noiImg5Name;
	@ValidImage
	private MultipartFile noiImg5;
	private String noiImg6Name;
	@ValidImage
	private MultipartFile noiImg6;
	private String noiImg7Name;
	@ValidImage
	private MultipartFile noiImg7;
	private String noiImg8Name;
	@ValidImage
	private MultipartFile noiImg8;
	private String noiImg9Name;
	@ValidImage
	private MultipartFile noiImg9;
	private String noiImg10Name;
	@ValidImage
	private MultipartFile noiImg10;
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