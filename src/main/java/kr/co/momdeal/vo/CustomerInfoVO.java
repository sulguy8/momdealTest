package kr.co.momdeal.vo;

import javax.validation.constraints.NotNull;

import org.apache.ibatis.type.Alias;
import org.springframework.web.multipart.MultipartFile;

import kr.co.momdeal.validator.ValidImage;
import lombok.Data;

@Alias("cui")
@Data
public class CustomerInfoVO {

	private Integer cuiNum;
	private String cuiId;
	private String cuiName;
	private String cuiNickname;
	private String cuiPhone;
	private String cuiTrans;
	private String cuiBirth;
	private String cuiProfileimgName;
	@ValidImage
	private MultipartFile cuiProfileimg;
	private String cuiAgreeLocation;
	private String cuiAgreeLocationdat;
	private String cuiAgreePhone;
	private String cuiAgreePhonedat;
	private String cuiAgreeCamera;
	private String cuiAgreeCameradat;
	private String cuiAgreePush;
	private String cuiAgreePushdat;
	private String cuiAgreeSms;
	private String cuiAgreeSmsdat;
	private String cuiAgreeEmail;
	private String cuiAgreeEmaildat;
	private String cuiAgreeService;
	private String cuiAgreeServicedat;
	private String cuiAgreePrivate;
	private String cuiAgreePrivatedat;
	private String cuiConfigReply;
	private String cuiConfigNoti;
	private String cuiConfigEvent;
	private String cuiConfigPoint;
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