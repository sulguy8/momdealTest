package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import kr.co.momdeal.validator.ValidImage;
import lombok.Data;

@Alias("hoi")
@Data
public class HotdealInfoVO {

	private Integer hoiNum;
	private Integer caiNum;
	@NotNull
	private Integer cuiNum;
	private Integer shiNum;
	private Integer hoiType;
	private Integer hoiBest;
	private Integer hoiStatus;
	@NotNull
	private String hoiTitle;
	@NotNull
	private String hoiContent;
	private String hoiSdate;
	private String hoiStime;
	private String hoiEdate;
	private String hoiEtime;
	@NotNull
	private Integer hoiRecommend1Cnt;
	@NotNull
	private Integer hoiRecommend2Cnt;
	@NotNull
	private Integer hoiRecommend3Cnt;
	@NotNull
	private Integer hoiShareCnt;
	@NotNull
	private Integer hoiAlarmCnt;
	@NotNull
	private Integer hoiViewCnt;
	@NotNull
	private Integer hoiExtlinkCnt;
	private String hoiImg1Name;
	@ValidImage
	private MultipartFile hoiImg1;
	private String hoiImg2Name;
	@ValidImage
	private MultipartFile hoiImg2;
	private String hoiImg3Name;
	@ValidImage
	private MultipartFile hoiImg3;
	private String hoiImg4Name;
	@ValidImage
	private MultipartFile hoiImg4;
	private String hoiImg5Name;
	@ValidImage
	private MultipartFile hoiImg5;
	private String hoiImg6Name;
	@ValidImage
	private MultipartFile hoiImg6;
	private String hoiImg7Name;
	@ValidImage
	private MultipartFile hoiImg7;
	private String hoiImg8Name;
	@ValidImage
	private MultipartFile hoiImg8;
	private String hoiImg9Name;
	@ValidImage
	private MultipartFile hoiImg9;
	private String hoiImg10Name;
	@ValidImage
	private MultipartFile hoiImg10;
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