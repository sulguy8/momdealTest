package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerPointVO;

@MapperScan
public interface CustomerPointMapper {
	Page<CustomerPointVO> selectCUPList(CustomerPointVO cup);
	CustomerPointVO selectCUP(int cupNum);
	int updateCUP(CustomerPointVO cup);
	int insertCUP(CustomerPointVO cup);
	int deleteCUP(int cupNum);
}