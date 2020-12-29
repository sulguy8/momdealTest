package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.HotdealInfoVO;

@MapperScan
public interface HotdealInfoMapper {
	Page<HotdealInfoVO> selectHOIList(HotdealInfoVO hoi);
	HotdealInfoVO selectHOI(int hoiNum);
	int updateHOI(HotdealInfoVO hoi);
	int insertHOI(HotdealInfoVO hoi);
	int deleteHOI(int hoiNum);
}