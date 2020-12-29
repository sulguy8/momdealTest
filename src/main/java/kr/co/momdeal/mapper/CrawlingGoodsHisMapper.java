package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CrawlingGoodsHisVO;

@MapperScan
public interface CrawlingGoodsHisMapper {
	Page<CrawlingGoodsHisVO> selectCGHList(CrawlingGoodsHisVO cgh);
	CrawlingGoodsHisVO selectCGH(int cghNum);
	int updateCGH(CrawlingGoodsHisVO cgh);
	int insertCGH(CrawlingGoodsHisVO cgh);
	int deleteCGH(int cghNum);
}