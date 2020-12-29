package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerEasyLoginVO;

@MapperScan
public interface CustomerEasyLoginMapper {
	Page<CustomerEasyLoginVO> selectCELList(CustomerEasyLoginVO cel);
	CustomerEasyLoginVO selectCEL(int celNum);
	int updateCEL(CustomerEasyLoginVO cel);
	int insertCEL(CustomerEasyLoginVO cel);
	int deleteCEL(int celNum);
}