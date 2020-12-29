package kr.co.momdeal.vo.common;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data	
public class CommentVO{

	private String label;
	private List<GeneratorVO> fkGenList;
	private List<String> valids;
	private String type;
	private String fkColumn;
	private String[] fkColumns;
	private String commonCode;
	private Boolean search = false;
}
