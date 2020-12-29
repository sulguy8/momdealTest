package kr.co.momdeal.vo.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ErrorVO {
	private String userId;
	private String date;
	private String msg;
	private String detailMsg;
	private String errCode;
	private SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD");
	

	public ErrorVO(Date date, String msg, String detailMsg) {
		super();
		this.date = sdf.format(date);
		this.msg = msg;
		this.detailMsg = detailMsg;
	}
	public ErrorVO(Date date, String msg, String detailMsg, String errCode) {
		super();
		this.date = sdf.format(date);
		this.msg = msg;
		this.detailMsg = detailMsg;
		this.errCode = errCode;
	}

	public ErrorVO(Date date, String msg, String detailMsg, String errCode,String userId) {
		super();
		this.date = sdf.format(date);
		this.msg = msg;
		this.detailMsg = detailMsg;
		this.errCode = errCode;
		this.userId = userId;
	}
	
	public String getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = sdf.format(date);
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getDetailMsg() {
		return detailMsg;
	}
	public void setDetailMsg(String detailMsg) {
		this.detailMsg = detailMsg;
	}
	
	public String getErrCode() {
		return errCode;
	}
	public void setErrCode(String errCode) {
		this.errCode = errCode;
	}
	@Override
	public String toString() {
		return "ErrorVO [date=" + date + ", msg=" + msg + ", detailMsg=" + detailMsg + ", errCode=" + errCode + "]";
	}
	
	
}
