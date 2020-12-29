package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.HotdealRecommendVO;

@MapperScan
public interface HotdealRecommendMapper {
	Page<HotdealRecommendVO> selectHORList(HotdealRecommendVO hor);
	HotdealRecommendVO selectHOR(int horNum);
	int updateHOR(HotdealRecommendVO hor);
	int insertHOR(HotdealRecommendVO hor);
	int deleteHOR(int horNum);
}