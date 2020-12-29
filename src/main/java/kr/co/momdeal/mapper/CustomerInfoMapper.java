package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerInfoVO;

@MapperScan
public interface CustomerInfoMapper {
	Page<CustomerInfoVO> selectCUIList(CustomerInfoVO cui);
	CustomerInfoVO selectCUI(int cuiNum);
	int updateCUI(CustomerInfoVO cui);
	int insertCUI(CustomerInfoVO cui);
	int deleteCUI(int cuiNum);
}