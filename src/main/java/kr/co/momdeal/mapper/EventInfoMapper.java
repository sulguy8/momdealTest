package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.EventInfoVO;

@MapperScan
public interface EventInfoMapper {
	Page<EventInfoVO> selectEVIList(EventInfoVO evi);
	EventInfoVO selectEVI(int eviNum);
	int updateEVI(EventInfoVO evi);
	int insertEVI(EventInfoVO evi);
	int deleteEVI(int eviNum);
}