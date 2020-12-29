package kr.co.momdeal.vo.common;

import lombok.Data;

@Data
public class PaginationVO {
	private Integer pageNum;
	private Integer pageSize;
	private Integer start;
	private Integer length;
}
