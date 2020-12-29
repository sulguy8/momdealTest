package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.EventApplicantInfoVO;

@MapperScan
public interface EventApplicantInfoMapper {
	Page<EventApplicantInfoVO> selectEAIList(EventApplicantInfoVO eai);
	EventApplicantInfoVO selectEAI(int eaiNum);
	int updateEAI(EventApplicantInfoVO eai);
	int insertEAI(EventApplicantInfoVO eai);
	int deleteEAI(int eaiNum);
}