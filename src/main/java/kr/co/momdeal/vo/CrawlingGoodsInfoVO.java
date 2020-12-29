package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import kr.co.momdeal.validator.ValidImage;
import lombok.Data;

@Alias("cgi")
@Data
public class CrawlingGoodsInfoVO {

	private Integer cgiNum;
	private Integer cspNum;
	private Integer cciNum;
	private String cgiKey;
	private String cgiUrl;
	private String cgiName;
	private String cgiQty;
	private String cgiDesc;
	@NotNull
	private String cgiPrice;
	private String cgiDisprice;
	private String cgiSimgName;
	@ValidImage
	private MultipartFile cgiSimg;
	private String cgiMimgName;
	@ValidImage
	private MultipartFile cgiMimg;
	private String credat;
	private String cretim;
	private Integer creusr;
	private String moddat;
	private String modtim;
	private Integer modusr;
	private String active;
	private String orders;
}