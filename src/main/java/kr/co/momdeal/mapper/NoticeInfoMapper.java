package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.NoticeInfoVO;

@MapperScan
public interface NoticeInfoMapper {
	Page<NoticeInfoVO> selectNOIList(NoticeInfoVO noi);
	NoticeInfoVO selectNOI(int noiNum);
	int updateNOI(NoticeInfoVO noi);
	int insertNOI(NoticeInfoVO noi);
	int deleteNOI(int noiNum);
}