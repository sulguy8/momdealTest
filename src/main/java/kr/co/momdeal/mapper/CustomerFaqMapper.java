package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerFaqVO;

@MapperScan
public interface CustomerFaqMapper {
	Page<CustomerFaqVO> selectCUFList(CustomerFaqVO cuf);
	CustomerFaqVO selectCUF(int cufNum);
	int updateCUF(CustomerFaqVO cuf);
	int insertCUF(CustomerFaqVO cuf);
	int deleteCUF(int cufNum);
}