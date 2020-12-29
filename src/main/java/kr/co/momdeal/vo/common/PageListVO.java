package kr.co.momdeal.vo.common;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

import com.github.pagehelper.Page;

import lombok.Data;

@Data
public class PageListVO implements Closeable{
	private int pageNum;
	private int pageSize;
	private int startRow;
	private int endRow;
	private long total;
	private int pages;
	private List<?> list;
	private List<?> data;
	//private int draw;
	private long recordsTotal;
	private long recordsFiltered;
	public PageListVO(Page<?> obj) {
		this.pageNum = obj.getPageNum();
		this.pageSize = obj.getPageSize();
		this.startRow = obj.getStartRow();
		this.endRow = obj.getEndRow();
		this.total = obj.getTotal();
		this.pages = obj.getPages();
		this.list = obj.getResult();
		this.data = obj.getResult();
		//this.draw = obj.getPageNum();
		this.recordsTotal = obj.getTotal();
		this.recordsFiltered = obj.getTotal();
	}
	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub
		
	}
}
