package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerAppVO;

@MapperScan
public interface CustomerAppMapper {
	Page<CustomerAppVO> selectCUAList(CustomerAppVO cua);
	CustomerAppVO selectCUA(int cuaNum);
	int updateCUA(CustomerAppVO cua);
	int insertCUA(CustomerAppVO cua);
	int deleteCUA(int cuaNum);
}