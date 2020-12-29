package kr.co.momdeal.mapper;

import org.mybatis.spring.annotation.MapperScan;

import com.github.pagehelper.Page;

import kr.co.momdeal.vo.CategoryInfoVO;

@MapperScan
public interface CategoryInfoMapper {
	Page<CategoryInfoVO> selectCAIList(CategoryInfoVO cai);
	CategoryInfoVO selectCAI(int caiNum);
	int updateCAI(CategoryInfoVO cai);
	int insertCAI(CategoryInfoVO cai);
	int deleteCAI(int caiNum);
}