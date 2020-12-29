package kr.co.momdeal.vo.common;

import org.apache.ibatis.type.Alias;

import lombok.Data;

@Alias("gen")
@Data
public class GeneratorVO {
	//컬럼 이름
	private String columnName;
	// 컬럼 이름의 카멜케이스
	private String camelName;
	//컬럼 데이터 타입
	private String dataType;
	//자바 데이터 타입
	private String javaType;
	//정의된 타입(ro,ed..)
	private String type;
	//널 가능 여부
	private String isNullable;
	//컬럼 기본 값
	private String columnDefault;
	//컬럼 키 정보(PRI, UNI, MUL..)
	private String columnKey;
	//컬럼 커멘트
	private String columnComment;
	// 외래키일 경우 참조 테이블 이름
	private String referencedTableName;
	// 외래키일 경우 참조 컬럼 이름
	private String referencedColumnName;
	// 키 이름
	private String constraintName;
	//컬럼 커멘트를 제이슨 형태에서 변환할 cvo
	private CommentVO cvo;
}
