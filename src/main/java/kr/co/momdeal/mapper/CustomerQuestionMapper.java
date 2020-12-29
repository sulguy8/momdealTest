package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CustomerQuestionVO;

@MapperScan
public interface CustomerQuestionMapper {
	Page<CustomerQuestionVO> selectCUQList(CustomerQuestionVO cuq);
	CustomerQuestionVO selectCUQ(int cuqNum);
	int updateCUQ(CustomerQuestionVO cuq);
	int insertCUQ(CustomerQuestionVO cuq);
	int deleteCUQ(int cuqNum);
}