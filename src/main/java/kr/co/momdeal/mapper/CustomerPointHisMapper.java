package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerPointHisVO;

@MapperScan
public interface CustomerPointHisMapper {
	Page<CustomerPointHisVO> selectCPHList(CustomerPointHisVO cph);
	CustomerPointHisVO selectCPH(int cphNum);
	int updateCPH(CustomerPointHisVO cph);
	int insertCPH(CustomerPointHisVO cph);
	int deleteCPH(int cphNum);
}