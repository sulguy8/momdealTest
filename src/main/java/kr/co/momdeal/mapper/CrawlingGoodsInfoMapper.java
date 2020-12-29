package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CrawlingGoodsInfoVO;

@MapperScan
public interface CrawlingGoodsInfoMapper {
	Page<CrawlingGoodsInfoVO> selectCGIList(CrawlingGoodsInfoVO cgi);
	CrawlingGoodsInfoVO selectCGI(int cgiNum);
	int updateCGI(CrawlingGoodsInfoVO cgi);
	int insertCGI(CrawlingGoodsInfoVO cgi);
	int deleteCGI(int cgiNum);
}