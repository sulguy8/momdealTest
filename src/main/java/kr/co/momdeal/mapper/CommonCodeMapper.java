package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CommonCodeVO;

@MapperScan
public interface CommonCodeMapper {
	Page<CommonCodeVO> selectCODList(CommonCodeVO cod);
	CommonCodeVO selectCOD(int codNum);
	int updateCOD(CommonCodeVO cod);
	int insertCOD(CommonCodeVO cod);
	int deleteCOD(int codNum);
}