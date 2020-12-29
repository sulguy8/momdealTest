package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import kr.co.momdeal.validator.ValidImage;
import lombok.Data;

@Alias("cgh")
@Data
public class CrawlingGoodsHisVO {

	private Integer cghNum;
	private Integer cgiNum;
	private String cghKey;
	private String cghUrl;
	private String cghName;
	private String cghQty;
	private String cghDesc;
	@NotNull
	private String cghPrice;
	private String cghDisprice;
	private String cghSimgName;
	@ValidImage
	private MultipartFile cghSimg;
	private String cghMimgName;
	@ValidImage
	private MultipartFile cghMimg;
	private String credat;
	private String cretim;
	private Integer creusr;
	private String moddat;
	private String modtim;
	private Integer modusr;
	private String active;
	private String orders;
}