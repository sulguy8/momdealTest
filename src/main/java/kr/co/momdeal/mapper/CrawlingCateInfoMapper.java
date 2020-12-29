package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CrawlingCateInfoVO;

@MapperScan
public interface CrawlingCateInfoMapper {
	Page<CrawlingCateInfoVO> selectCCIList(CrawlingCateInfoVO cci);
	CrawlingCateInfoVO selectCCI(int cciNum);
	int updateCCI(CrawlingCateInfoVO cci);
	int insertCCI(CrawlingCateInfoVO cci);
	int deleteCCI(int cciNum);
}