package kr.co.momdeal.mapper.common;

import java.util.List;
import java.util.Map;

import org.mybatis.spring.annotation.MapperScan;

import kr.co.momdeal.vo.common.ForeignKeyVO;
import kr.co.momdeal.vo.common.GeneratorVO;
@MapperScan
public interface GeneratorMapper {
	List<GeneratorVO> selectColumnList(String tableName);
	List<ForeignKeyVO> selectFKList(String tableName);
	List<GeneratorVO> selectColumnListWithForeign(String tableName);
	List<Map<String,String>> selecTableList();
	GeneratorVO selectColumn(String tableName, String columnName);
	String selectTableName(String tableName);
}
