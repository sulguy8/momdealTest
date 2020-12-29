package kr.co.momdeal.controller.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import kr.co.momdeal.exception.ServiceException;
import kr.co.momdeal.mapper.common.GeneratorMapper;
import kr.co.momdeal.vo.common.CommentVO;
import kr.co.momdeal.vo.common.GeneratorVO;
import lombok.extern.slf4j.Slf4j;

@Controller
@Slf4j
public class GeneratorController {

	@Resource
	private GeneratorMapper gm;
	@Resource
	private ObjectMapper om;
	
	private List<GeneratorVO> genList;
	
	private boolean reload = false;
	private String folderName;
	private String tableName;
	private String pkColName;
	private String pkCamelName;
	private List<GeneratorVO> uniList = new ArrayList<>();
	private String initName = "";
	private String tableLabelName;
	
	private void initGenList(Map<String,String> params) {
		this.tableName = params.get("tableName");
		this.folderName = params.get("folderName");
		this.tableLabelName = getTitle(gm.selectTableName(tableName));
		if(params.get("reload").equals("1")) {
			reload = true;
		}
		this.genList = gm.selectColumnListWithForeign(tableName);
		if(this.genList == null || this.genList.isEmpty()) {
			throw new ServiceException("테이블명을 확인해주세요");
		}
		//컬럼 앞의 _이전의 글자만 가져와서 테이블의 약어를 생성함.
		this.initName = this.genList.get(0).getColumnName();
		this.initName = this.initName.substring(0,this.initName.indexOf("_")).toLowerCase();
		
		
		for(GeneratorVO gen : this.genList) {
			gen.setCamelName(toCamelCase(gen.getColumnName()));
			if(gen.getColumnKey().contentEquals("PRI")) {
				this.pkColName = gen.getColumnName();
				this.pkCamelName = toCamelCase(this.pkColName);
			}
			if(gen.getColumnKey().contentEquals("UNI")) {
				uniList.add(gen);
			}
			if(gen.getColumnComment()!=null && !gen.getColumnComment().contentEquals("")) {
				CommentVO cvo = null;
				try {
					cvo = om.readValue(gen.getColumnComment(),CommentVO.class);
				} catch (JsonParseException e) {
					e.printStackTrace();
				} catch (JsonMappingException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				cvo.setLabel(getTitle(cvo.getLabel()));
				gen.setCvo(cvo);
				if(cvo != null) {
					if(cvo.getFkColumn()!=null) {
						cvo.setFkColumns(cvo.getFkColumn().split(","));
						List<GeneratorVO> fkGenList = new ArrayList<>();

						if(cvo.getFkColumn().trim().equals("*")) {
							fkGenList = gm.selectColumnList(gen.getReferencedTableName());
							for(int i = 0;i<fkGenList.size();i++) {
								GeneratorVO fkGen = fkGenList.get(i);
								if(gen.getColumnName().equals(fkGen.getColumnName())) {
									fkGenList.remove(i);
									i--;
									continue;
								}
								CommentVO fkCvo = null;
								try {
									fkCvo = om.readValue(fkGen.getColumnComment(),CommentVO.class);
								} catch (JsonParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (JsonMappingException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								fkGen.setCvo(fkCvo);
								fkGen.setType(getType(fkGen));
								fkGen.setCamelName(toCamelCase(fkGen.getColumnName()));
								fkGen.setJavaType(getJavaType(fkGen));
								fkCvo.setLabel(getTitle(fkCvo.getLabel()));
							}
						}else {
							for(String fkColumn : cvo.getFkColumns()) {
								if(!fkColumn.trim().equals("")) {
									GeneratorVO fkGen = gm.selectColumn(gen.getReferencedTableName(), fkColumn);
									CommentVO fkCvo = null;
									try {
										fkCvo = om.readValue(fkGen.getColumnComment(),CommentVO.class);
									} catch (JsonParseException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (JsonMappingException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
									fkGen.setCvo(fkCvo);
									fkGen.setType(getType(fkGen));
									fkGen.setCamelName(toCamelCase(fkGen.getColumnName()));
									fkGen.setJavaType(getJavaType(fkGen));
									fkCvo.setLabel(getTitle(fkCvo.getLabel()));
									fkGenList.add(fkGen);
								}
							}
						}
						cvo.setFkGenList(fkGenList);
					}
					if(cvo.getType()!=null && !cvo.getType().contentEquals("")) {
						gen.setType(cvo.getType());
					}
				}
			}
			gen.setType(getType(gen));
			gen.setJavaType(getJavaType(gen));
		}
	}
	

	@PostMapping("/gen/xmlCode")
	public @ResponseBody String getXmlCode(@RequestBody Map<String,String> params) throws JsonParseException, JsonMappingException, IOException {
		initGenList(params);
		String mapperName = toCamelCase(tableName);
		mapperName = mapperName.substring(0,1).toUpperCase() + mapperName.substring(1);
		
		String columns = "";
		String insertColumns = "";
		String ifInsertColumns = "";
		String wheres = "";
		String values = "";
		String ifValues = "";
		String updates = "";
		String foreignColumns = "";
		String joinTables = "";
		
		for(int i=0;i<genList.size();i++) {
			GeneratorVO gen = genList.get(i);
			wheres += "			<if test=\'" + gen.getCamelName() + " != null and " + gen.getCamelName() + " != \"\"\'>\r\n";
			wheres += " 				AND "+ initName + "." + gen.getColumnName();
			if(gen.getType().contentEquals("num")) {
				wheres += " = #{" + gen.getCamelName() + "}\r\n";
			}else {
				if(gen.getColumnName().toUpperCase().equals("CREDAT") || gen.getColumnName().toUpperCase().equals("MODDAT")) {
					wheres += " like concat('%',replace(#{" + gen.getCamelName() + "},'-',''),'%')\r\n";
				}else {
					wheres += " like concat('%',#{" + gen.getCamelName() + "},'%')\r\n";
				}
			}
			wheres += "			</if>\r\n";
			
			if(gen.getColumnName().toUpperCase().equals("CREDAT") || gen.getColumnName().toUpperCase().equals("MODDAT")) {
				columns += "		DATE_FORMAT("  + initName + "." +  gen.getColumnName() + ",'%Y-%m-%d') AS " + gen.getColumnName();
				values += "		DATE_FORMAT(NOW(),'%Y%m%d'),";
				insertColumns += "	" + gen.getColumnName() + ",";
			}else if(gen.getColumnName().toUpperCase().equals("CRETIM") || gen.getColumnName().toUpperCase().equals("MODTIM")) {
				columns += "		TIME_FORMAT(" + initName + "." + gen.getColumnName() + ",'%H:%i:%S') AS " + gen.getColumnName();
				values += "		DATE_FORMAT(NOW(),'%H%i%S'),";
				insertColumns += "	" + gen.getColumnName() + ",";
			}else {
				columns += "		" + initName + "." + gen.getColumnName();
				if(gen.getColumnKey()==null || !gen.getColumnKey().equals("PRI")) {
					updates += "			<if test=\"" + gen.getCamelName() + " != null and " + gen.getCamelName() + " != '' \">  "+ gen.getColumnName() + " = #{" + gen.getCamelName() + "},</if>\r\n";
					if(gen.getIsNullable().equals("YES") || (gen.getColumnDefault()!=null && !gen.getColumnDefault().equals("NULL"))) {
							ifInsertColumns += "			<if test=\"" + gen.getCamelName() + " != null and " + gen.getCamelName() + " != '' \">  "+ gen.getColumnName() + ",</if>\r\n";
							ifValues += "			<if test=\"" + gen.getCamelName() + " != null and " + gen.getCamelName() + " != '' \">  #{"+ gen.getCamelName() + "},</if>\r\n";
					}else {
						values += "		#{" + gen.getCamelName() + "},\r\n";
						insertColumns += "	" + gen.getColumnName() + ",\r\n";
					}
				}
			}
			if(i!=0 && i % 5 == 0 ) {
				columns += "\r\n";
			} 
			columns += ",";
			if(gen.getReferencedColumnName()!=null && !gen.getReferencedColumnName().contentEquals("")) {
				String foreignColumn = gen.getReferencedColumnName();
				String foreignInitName = foreignColumn.substring(0,foreignColumn.indexOf("_")).toLowerCase();
				//foreignColumns += "	" + foreignInitName+"." + foreignColumn + ",";
				joinTables += " left join " + gen.getReferencedTableName() + " " + foreignInitName + " on " + foreignInitName + "." + foreignColumn + "=" + initName + "." + gen.getColumnName();

				CommentVO cvo = gen.getCvo();
				if(cvo!=null && cvo.getFkGenList()!=null && !cvo.getFkGenList().isEmpty()) {
					for(GeneratorVO fkGen : cvo.getFkGenList()) {
						foreignColumns += "	" + foreignInitName + "." + fkGen.getColumnName() + ",";

						wheres += "			<if test=\"" + fkGen.getCamelName() + " != null and " + fkGen.getCamelName() + " != ''\">\r\n";
						wheres += " 				AND "+ foreignInitName + "." + fkGen.getColumnName();
						if(fkGen.getType().contentEquals("num")) {
							wheres += " = #{" + fkGen.getCamelName() + "}\r\n";
						}else {
							if(fkGen.getColumnName().toUpperCase().equals("CREDAT") || fkGen.getColumnName().toUpperCase().equals("MODDAT")) {
								wheres += " like concat('%',replace(#{" + fkGen.getCamelName() + "},'-',''),'%')\r\n";
							}else {
								wheres += " like concat('%',#{" + fkGen.getCamelName() + "},'%')\r\n";
							}
						}
						wheres += "			</if>\r\n";
					}
				}
				
			}
		}
		columns += foreignColumns;
		columns = columns.substring(0,columns.length()-1); 
		insertColumns = ifInsertColumns + insertColumns.substring(0,insertColumns.length()-1);
		values = ifValues + values.substring(0,values.length()-1);
		
		String selectId = "select" + initName.toUpperCase();
		String selectListId = selectId + "List";
		String insertId = "insert" + initName.toUpperCase();
		String updateId = "update" + initName.toUpperCase();
		String deleteId = "delete" + initName.toUpperCase(); 
		
		String xml = "<!DOCTYPE mapper\r\n" + 
				"  PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n" + 
				"  \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + 
				"<mapper namespace=\"kr.co.momdeal.mapper." + mapperName + "Mapper\">\r\n" + 
				"	<sql id=\"selectColumns\">\r\n"; 
		xml += columns; 
		xml += 	"	\r\n" + 
				"	</sql>\r\n" + 
				"	<select id=\"" + selectListId + "\" resultType=\"" + initName + "\">\r\n" + 
				"		SELECT <include refid=\"selectColumns\"></include> FROM\r\n" + 
				"		" + tableName +" " + initName + " " + joinTables + "		\r\n" + 
				"		<where>\r\n";
		xml += wheres;
		xml +=	"		</where>\r\n" + 
				"		<trim prefix=\"order by\">\r\n" + 
				"	    	<if test=\"orders!=null\">\r\n" + 
				"	    		${orders}\r\n" + 
				"	    	</if>\r\n" + 
				"	    </trim>\r\n" + 
				"	    \r\n" + 
				"	</select>\r\n" + 
				"	<select id=\"" + selectId + "\" resultType=\"" + initName + "\">\r\n" + 
				"		SELECT <include refid=\"selectColumns\"></include> FROM " + tableName +" " + initName + " " + joinTables + "\r\n" + 
				"		WHERE " + pkColName + " = #{" + pkCamelName + "}\r\n" + 
				"	</select>\r\n";
//		for(GeneratorVO uni:uniList) { 
//			String camel = uni.getCamelName();
//			camel = camel.substring(0,1).toUpperCase() + camel.substring(1);
//			xml += "	<select id=\"select" + initName.toUpperCase() + "By" + camel +"\" resultType=\"sai\">\r\n";
//			xml += "		SELECT <include refid=\"selectColumns\"></include> FROM\r\n";
//			xml += "		" + tableName +" " + initName + " " + joinTables + "	\r\n";
//			xml += "		where " + initName + "." + uni.getColumnKey() + " = #{" + uni.getCamelName() + "}\r\n"; 
//			xml += "	</select>\r\n";
//		}
		xml +=	"	<insert id=\"" + insertId + "\" useGeneratedKeys=\"true\" keyProperty=\"" + pkCamelName + "\" keyColumn=\"" + pkColName + "\">\r\n";
		xml +=	"		INSERT INTO " + tableName + "	\r\n" + 
				"		(\r\n " + insertColumns + ")\r\n" + 
				"		VALUES(\r\n " + values + ")\r\n" + 
				"	</insert>\r\n" + 
				"	\r\n" + 
				"	<update id=\"" + updateId + "\">\r\n" + 
				"		UPDATE " + tableName + " " + initName + " \r\n" + 
				"		<set>\r\n"; 
		xml += updates; 
		xml +=	"			MODDAT = DATE_FORMAT(NOW(),'%Y%m%d'),\r\n" + 
				"			MODTIM = DATE_FORMAT(NOW(),'%H%i%S')\r\n" + 
				"		</set>\r\n" + 
				"		WHERE  " + initName +"."+ pkColName +"  = #{" + pkCamelName +  "}\r\n" +  
				"	</update>\r\n" + 
				"	\r\n" + 
				"	<delete id=\"" + deleteId + "\">\r\n" +
				"		UPDATE " + tableName + "	\r\n" + 
				"		SET ACTIVE=0,\r\n" + 
				"		MODDAT = DATE_FORMAT(NOW(),'%Y%m%d'),\r\n" + 
				"		MODTIM = DATE_FORMAT(NOW(),'%H%i%S')\r\n" + 
				"		WHERE  "+ pkColName +"  = #{" + pkCamelName +  "}\r\n" + 
				"	</delete>\r\n" + 
				"</mapper>";

		createFile("resources\\mapper\\", tableName + ".xml", xml,params.get("reload"));
		return xml;
	}
	private String toCamelCase(String s){
	   String[] parts = s.split("_");
	   String camelCaseString = "";
	   for (String part : parts){
	      camelCaseString = camelCaseString + toProperCase(part);
	   }
	   return camelCaseString.substring(0,1).toLowerCase() + camelCaseString.substring(1);
	}
	
	private String toProperCase(String s) {
	    return s.substring(0, 1).toUpperCase() +
	               s.substring(1).toLowerCase();
	}
	private String getType(GeneratorVO gen) {
		if(gen.getCvo()!=null && gen.getCvo().getType()!=null && !gen.getCvo().getType().contentEquals("")) {
			return gen.getCvo().getType();
		}
		String columnKey = gen.getColumnKey();
		String dataType = gen.getDataType();
		if(gen.getColumnName().toLowerCase().equals("credat")||gen.getColumnName().toLowerCase().equals("cretim")||
				gen.getColumnName().toLowerCase().equals("moddat")||gen.getColumnName().toLowerCase().equals("modtim")) {
				return "ro";
		}
		if(columnKey!=null && columnKey.equals("PRI")) {
			return "ro";
		}
		if(dataType.equals("tinyint")||dataType.equals("smallint")
			||dataType.equals("mediumint")||dataType.equals("int")) {
			return "num";
		}
		if(dataType.contentEquals("decimal")) {
			return "dbl";
		}

		if(dataType.equals("char")||dataType.equals("varchar")) {
			return "ed";
		}
		if(dataType.equals("tinytext")||dataType.equals("text")
			||dataType.equals("mediumtext")||dataType.equals("longtext")) {
			return "tx";
		}
		return "";
	}
	private String getTitle(String columnComment) {
		columnComment = columnComment.replaceAll("\\r|\\n", "");

		int idx = columnComment.indexOf("(");
		if(idx!=-1) {
			columnComment = columnComment.substring(0,idx);
		}
		return columnComment;
	}
	private String getHtmlType(GeneratorVO gen) {
		String type = gen.getType();
		if(gen.getColumnName().toLowerCase().equals("credat")||gen.getColumnName().toLowerCase().equals("moddat")) {
			return "date";
		}
		if(type.equals("ro") || type.equals("ed")) {
			return "text";
		}else if(type.equals("num")) {
			return "number";
		}else if(type.equals("co")) {
			return "select";
		}else if(type.equals("tx")) {
			return "textarea";
		}
		return "";
	}
	
	@PostMapping("/gen/jspCode")
	public @ResponseBody String getJspCode(@RequestBody Map<String,String> params) {
		initGenList(params);
		String jspCode = "<%@ page language=\"java\" contentType=\"text/html; charset=UTF-8\"\r\n" + 
				"	pageEncoding=\"UTF-8\"%>\r\n" + 
				"<%@ include file=\"/WEB-INF/common/config.jspf\"%>\r\n";
		String searchHtml = "";
		for(GeneratorVO gen : genList) {
			if(gen.getCvo().getSearch()) {
				String htmlType = getHtmlType(gen);
				if(htmlType.equals("text")||htmlType.equals("textarea")||htmlType.equals("")) {
					searchHtml += "		<label for=\"" + gen.getCamelName() +"\">" + gen.getCvo().getLabel() + " : </label>\r\n";
					searchHtml += "		<input type=\"text\" data-search=\"true\" id=\"" + gen.getCamelName() + "\" placeholder=\"" + gen.getCvo().getLabel() + "\">\r\n";
				}else if(htmlType.equals("number")) {
					searchHtml += "		<label for=\"" + gen.getCamelName() +"\">" + gen.getCvo().getLabel() + " : </label>\r\n";
					searchHtml += "		<input type=\"number\" data-search=\"true\" id=\"" + gen.getCamelName() + "\" placeholder=\"" + gen.getCvo().getLabel() + "\">\r\n";
				}else if(htmlType.equals("date")) {
					searchHtml += "		<label for=\"" + gen.getCamelName() +"\">" + gen.getCvo().getLabel() + " : </label>\r\n";
					searchHtml += "		<input type=\"date\" data-search=\"true\" id=\"" + gen.getCamelName() + "\" placeholder=\"" + gen.getCvo().getLabel() + "\">\r\n";
				}else if(htmlType.equals("select")) {
					String cod = gen.getCamelName();
					if(gen.getCvo().getCommonCode()!=null) {
						cod = gen.getCvo().getCommonCode();
					}
					searchHtml += "		<label for=\"" + gen.getCamelName() +"\">" + gen.getCvo().getLabel() + " : </label>\r\n";
					searchHtml += "		<select data-cod=\"" + cod + "\" data-search=\"true\" id=\"" + gen.getCamelName() + "\"></select>\r\n";
				}
			}
		}
		searchHtml += "		활성여부 : <select data-cod=\"commActive\" data-search=\"true\" id=\"active\"></select>\r\n";
		jspCode += "	<div class=\"search\">\r\n";
		jspCode += searchHtml;
		jspCode += "		<button class=\"dt-button btn btn-primary glyphicon glyphicon-save-file\" onclick=\"doSearch()\">검색</button>\r\n";
		jspCode += "	</div>\r\n";
		jspCode +=	"<div class=\"card mb-3\" id=\"" + initName + "Table\"></div>\r\n" + 
				"<style>\r\n" + 
				"img[data-col] {\r\n" + 
				"	width: 90% !important;\r\n" + 
				"	height: 90% !important;\r\n" + 
				"}\r\n" +  
				"</style>\r\n" + 
				"<script>\r\n" + 
				"var deleteData,dt;\r\n" + 
				"var updateData = {};\r\n" + 
				"var insertData = {};\r\n" + 
				"\r\n";
		if(!searchHtml.equals("")) {
			jspCode += "function doSearch(){\r\n" + 
					"	var searchObjs = document.querySelectorAll('[data-search=true]');\r\n" + 
					"	var url = '/" + folderName + "/" + initName + "s?';\r\n" + 
					"	for(var searchObj of searchObjs){\r\n" + 
					"		url += searchObj.id + '=' + searchObj.value + '&';\r\n" + 
					"	}\r\n" + 
					"	dt.search(url);\r\n" + 
					"}\r\n";
		}
		log.info("tableLabelName=>{}",tableLabelName);
		jspCode +=	"$(document).ready(function() {\r\n" + 
				"	var conf = {\r\n" + 
				"		id:'" + initName + "Table',\r\n" + 
				"		title : '" + tableLabelName + "',\r\n" + 
				"		pks : '" + pkCamelName + "',\r\n" + 
				"		allCheck : true,\r\n" + 
				"		dtConf : {\r\n" + 
				"			addBtn : true,\r\n" + 
				"			editBtn : true,\r\n" + 
				"			deleteBtn : true,\r\n" + 
				"	        url: '/" + folderName + "/" + initName + "s',\r\n" + 
				"	        delUrl: '/" + folderName + "/" + initName + "s/del',\r\n" + 
				"			columns : [ \r\n";
		for(GeneratorVO gen : genList) {
			jspCode += "				{title:'"+ gen.getCvo().getLabel() + "', data : '" + gen.getCamelName() + "'";
			
			if(gen.getCvo().getCommonCode()!=null) {
				jspCode +=  ",type:'"+gen.getType()+"'";
				jspCode += ", cod:'" + gen.getCvo().getCommonCode() + "'";
			}else if(gen.getColumnName().toLowerCase().equals("active")) {
					jspCode += ",type:'co',cod:'commActive'";
			}else {
				jspCode +=  ",type:'"+gen.getType()+"'";
			}
			
			if(gen.getCvo().getSearch()) {
				jspCode += ", search:true";
			}
			jspCode += "},\r\n";
			if(gen.getCvo().getFkGenList()!=null && !gen.getCvo().getFkGenList().isEmpty()) {
				for(GeneratorVO fkGen : gen.getCvo().getFkGenList()) {
					jspCode += "				{title:'"+ fkGen.getCvo().getLabel() + "', data : '" + fkGen.getCamelName() + "', type:'"+fkGen.getType()+"'";
					jspCode += "},\r\n";
				}
			}
		}
		jspCode +=	"			]\r\n" + 
				"		}\r\n" + 
				"	};\r\n" + 
				"	dt = new DataTable(conf);\r\n" + 
				"	$('input[type=file]').on('change', handleImgFileSelect);\r\n" + 
				"});\r\n" + 
				"function handleImgFileSelect(e){\r\n" + 
				"	var files = e.target.files;\r\n" + 
				"	var filesArr = Array.prototype.slice.call(files);\r\n" + 
				"	var id = e.target.id;\r\n" + 
				"	filesArr.forEach(function(f) {\r\n" + 
				"		sel_file = f;\r\n" + 
				"		var reader = new FileReader();\r\n" + 
				"		reader.onload = function(e) {\r\n" + 
				"			$('#' +id +'Name').attr(\"src\",e.target.result);\r\n" + 
				"		}\r\n" + 
				"		reader.readAsDataURL(f);\r\n" + 
				"	});\r\n" + 
				"}\r\n"+
				"function allDelete(){\r\n" + 
				"	dt.getCheckedValues();\r\n" + 
				"	alert('전체삭제');\r\n" + 
				"}\r\n" + 
				"function addModalOpen(){\r\n" + 
				"	$('#addModal').modal();\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"async function updateModalOpen(pkColName){\r\n" + 
				"	$('#updateModal').modal();\r\n" + 
				"	await doAjax('GET','/" + folderName + "/" + initName + "/' + pkColName,'','',setUpdateData);\r\n" + 
				"\r\n" + 
				"}\r\n" + 
				"function doUpdate(){\r\n" + 
				"	console.log(sendData('u'));	\r\n" + 
				"	doAjax('POST', '/" + folderName + "/" + initName + "/mod', sendData('u'), 'u');\r\n" + 
				"}\r\n" + 
				"function doSave(){\r\n" + 
				"	console.log(sendData('a'));\r\n" + 
				"	doAjax('POST', '/" + folderName + "/" + initName + "', sendData('a'), 'a')\r\n" + 
				"}\r\n" + 
				"\r\n" + 
				"async function doAjax(method, url, data, flag, func){\r\n" + 
				"	return $.ajax({\r\n" + 
				"	    url: url,\r\n" + 
				"	    type: method,\r\n" + 
				"	    data: data,\r\n" + 
				"	    async: true,\r\n" + 
				"        processData:false,\r\n" + 
				"        contentType:false,\r\n" + 
				"	    success: function(res){\r\n" + 
				"	    	if(func && typeof func ===\"function\"){\r\n" + 
				"	    		console.log('func null Pass!!');\r\n" + 
				"				func(res);\r\n" + 
				"	    	} else {\r\n" + 
				"	    		console.log(res);\r\n" + 
				"	    		if(flag == 'u'){\r\n" + 
				"		    		$('#updateModal').modal('hide');\r\n" + 
				"					setTimeout( function () {\r\n" + 
				"						(res.result == 'ok') ? alert('수정 완료되었습니다.')\r\n" + 
				"			    				: alert('수정 실패');\r\n" + 
				"						dt.reload();\r\n" + 
				"					}, 200);\r\n" + 
				"	    		} else if(flag == 'a'){\r\n" + 
				"		    		$('#addModal').modal('hide');\r\n" + 
				"					setTimeout( function () {\r\n" + 
				"						(res.result == 'ok') ? alert('저장 완료되었습니다.')\r\n" + 
				"			    				: alert('저장 실패');\r\n" + 
				"						dt.reload();\r\n" + 
				"					}, 200);\r\n" + 
				"	    		}\r\n" + 
				"	    	}\r\n" + 
				"	    },\r\n" + 
				"	    error: function (request, status, error){\r\n" + 
				"	    	console.log(request);\r\n" + 
				"	    }\r\n" + 
				"	});\r\n" + 
				"}\r\n" + 
				"function setUpdateData(data){\r\n" + 
				"	for(var key in data){\r\n" + 
				"		if(data[key]){\r\n" + 
				"			$('input[data-col=u-' + key + '],select[data-col=u-' + key + '],textarea[data-col=u-' + key + ']').val(data[key]);\r\n" + 
				"			$('img[data-col=u-' + key + ']').attr('src','${imgPath}?fileName=' + encodeURI(data[key]));\r\n" + 
				"		}else{\r\n" + 
				"			$('input[data-col=u-' + key + '],select[data-col=u-' + key + '],textarea[data-col=u-' + key + ']').val('');\r\n" + 
				"			$('img[data-col=u-' + key + ']').attr('src','');\r\n" + 
				"		}\r\n"+
				"	}\r\n" + 
				"	this.getUpdateData();\r\n" + 
				"}\r\n" + 
				"function getUpdateData(){\r\n" + 
				"	var inputObjs = $('[data-col],select[data-col],textarea[data-col]');\r\n" + 
				"	var params = {};\r\n" + 
				"	for(var inputObj of inputObjs){\r\n" + 
				"		var key = inputObj.getAttribute('data-col');\r\n" + 
				"		var value = inputObj.value;\r\n" + 
				"		params[key] = value;\r\n" + 
				"	}\r\n" + 
				"	return params;\r\n" + 
				"}\r\n" + 
				"function sendData(flag){\r\n" + 
				"	var formData = new FormData(); \r\n" + 
				"	var inputObjs = $('input[data-col],select[data-col],textarea[data-col]');\r\n" + 
				"	for(var inputObj of inputObjs){\r\n" + 
				"		var key = inputObj.getAttribute('data-col');\r\n" + 
				"		var type = inputObj.type;\r\n" + 
				"		var value = inputObj.value;\r\n" + 
				"		if(type==='file' && key.indexOf(flag + '-') != -1){\r\n" + 
				"			if($('[data-col = '+ key +']')[0].files[0]){\r\n" + 
				"				console.log(key.replace(flag + '-', ''));\r\n" + 
				"				formData.append(key.replace(flag + '-', ''), $('[data-col = '+ key +']')[0].files[0]);\r\n" + 
				"			}\r\n" + 
				"		} else if(key.indexOf(flag + '-') != -1){\r\n" + 
				"			if(value){\r\n" + 
				"				console.log(key.replace(flag + '-', ''));\r\n" + 
				"				formData.append(key.replace(flag + '-',''), value);\r\n" + 
				"			}else{\r\n" + 
				"				console.log(key.replace(flag + '-', ''));\r\n" + 
				"				formData.append(key.replace(flag + '-',''), '');\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"	}\r\n" + 
				"    return formData;\r\n" +
				"}\r\n" + 
				"</script>\r\n" + 
				"\r\n" + 
				"<!-- Modal -->\r\n" + 
				"<div class=\"modal fade\" id=\"updateModal\" tabindex=\"-1\" role=\"dialog\"\r\n" + 
				"	aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\r\n" + 
				"	<div class=\"modal-dialog\" role=\"document\">\r\n" + 
				"		<div class=\"modal-content\">\r\n" + 
				"			<div class=\"modal-header\">\r\n" + 
				"				<h5 class=\"modal-title\">수정</h5>\r\n" + 
				"				<button type=\"button\" class=\"close\" data-dismiss=\"modal\"\r\n" + 
				"					aria-label=\"Close\">\r\n" + 
				"					<span aria-hidden=\"true\">&times;</span>\r\n" + 
				"				</button>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"modal-body\" id=\"modal-msg\">\r\n"; 
		for(GeneratorVO gen : genList) {
			String htmlType = getType(gen);
			String disable = "";
			
			if(htmlType.equals("ro")) {
				htmlType = "text";
				disable = "disabled";
			}else if(htmlType.equals("num")) {
				htmlType = "number";
			}else if(htmlType.equals("ed")) {
				htmlType = "text";
			}

			jspCode +=	"				<div class=\"input-group mb-3\">\r\n" + 
						"					<div class=\"input-group-prepend\">\r\n" + 
						"						<span class=\"input-group-text\">" + gen.getCvo().getLabel() +"</span>\r\n" + 
						"					</div>\r\n";
			if(gen.getCamelName().toLowerCase().indexOf("img")==-1) {
				String valids = "";
				if(gen.getCvo().getValids()!=null) {
					for(String valid : gen.getCvo().getValids()) {
						valids += valid.substring(1) + ",";
					}
					valids = valids.substring(0,valids.length()-1);
				}
				if(gen.getType().equals("co")) {
					String cod = gen.getCamelName();
					if(gen.getCvo().getCommonCode()!=null) {
						cod = gen.getCvo().getCommonCode();
					}
					jspCode +=  "					<select class=\"form-control\" id=\"u-" + gen.getCamelName() + "\"\r\n" + 
							"						data-col=\"u-" + gen.getCamelName() +"\" data-cod=\"" + cod + "\"></select><br>\r\n" + 
							"				</div>\r\n";
				}else {
					jspCode +=  "					<input type=\"" + htmlType + "\" class=\"form-control\" id=\"u-" + gen.getCamelName() + "\"\r\n" + 
								"						data-col=\"u-" + gen.getCamelName() +"\" " + disable;
					if(!valids.equals("")) {
						valids = valids.replaceAll("\"", "'");
						jspCode +=  " data-vali=\"" + valids + "\"";
					}
					jspCode +=  "><br>\r\n" + 
								"				</div>\r\n";
				}
			}else {
				jspCode +=  "					<input type=\"file\" class=\"form-control\" id=\"u-" + gen.getCamelName().replace("Name", "") + "\" data-col=\"u-"+ gen.getCamelName().replace("Name", "") +"\"><br>\r\n" + 
							"					<img id=\"u-" + gen.getCamelName() + "\" class=\"form-control\" data-col=\"u-" + gen.getCamelName() + "\" src=\"\"><br>\r\n" + 
							"				</div>\r\n"; 
			}
		}
		jspCode +=  "			</div>\r\n" +
				"			<div class=\"modal-footer\">\r\n" + 
				"				<button type=\"button\" class=\"btn btn-primary\" onclick=\"doUpdate()\">수정</button>\r\n" + 
				"				<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">취소</button>\r\n" + 
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>\r\n" + 
				"</div>\r\n" + 
				"<div class=\"modal fade\" id=\"addModal\" tabindex=\"-1\" role=\"dialog\"\r\n" + 
				"	aria-labelledby=\"exampleModalLabel\" aria-hidden=\"true\">\r\n" + 
				"	<div class=\"modal-dialog\" role=\"document\">\r\n" + 
				"		<div class=\"modal-content\">\r\n" + 
				"			<div class=\"modal-header\">\r\n" + 
				"				<h5 class=\"modal-title\">신규 입력</h5>\r\n" + 
				"				<button type=\"button\" class=\"close\" data-dismiss=\"modal\"\r\n" + 
				"					aria-label=\"Close\">\r\n" + 
				"					<span aria-hidden=\"true\">&times;</span>\r\n" + 
				"				</button>\r\n" + 
				"			</div>\r\n" + 
				"			<div class=\"modal-body\" id=\"modal-msg\">\r\n";
		for(GeneratorVO gen : genList) {
			String htmlType = getType(gen);
			String disable = "";
			
			if(htmlType.equals("ro")) {
				htmlType = "text";
				disable = "disabled";
			}else if(htmlType.equals("num")) {
				htmlType = "number";
			}else if(htmlType.equals("ed")) {
				htmlType = "text";
			}
			

			jspCode +=	"				<div class=\"input-group mb-3\">\r\n" + 
						"					<div class=\"input-group-prepend\">\r\n" + 
						"						<span class=\"input-group-text\">" + gen.getCvo().getLabel() +"</span>\r\n" + 
						"					</div>\r\n";
			if(gen.getCamelName().toLowerCase().indexOf("img")==-1) {
				if(gen.getType().equals("co")) {
					String cod = gen.getCamelName();
					if(gen.getCvo().getCommonCode()!=null) {
						cod = gen.getCvo().getCommonCode();
					}
					jspCode +=  "					<select class=\"form-control\" id=\"a-" + gen.getCamelName() + "\"\r\n" + 
							"						data-col=\"a-" + gen.getCamelName() +"\" data-cod=\"" + cod + "\"></select><br>\r\n" + 
							"				</div>\r\n";
				}else {
					jspCode +=  "					<input type=\"" + htmlType + "\" class=\"form-control\" id=\"a-" + gen.getCamelName() + "\"\r\n" + 
								"						data-col=\"a-" + gen.getCamelName() +"\" " + disable + "><br>\r\n" + 
								"				</div>\r\n";
				}
			}else {
				jspCode +=  "					<input type=\"file\" class=\"form-control\" id=\"a-" + gen.getCamelName().replace("Name", "") + "\" data-col=\"a-"+ gen.getCamelName().replace("Name", "") +"\"><br>\r\n" + 
							"					<img id=\"a-" + gen.getCamelName() + "\" class=\"form-control\" data-col=\"a-" + gen.getCamelName() + "\" src=\"\"><br>\r\n" + 
							"				</div>\r\n"; 
			}
		}
		jspCode +=  "			</div>\r\n" + 
				"			<div class=\"modal-footer\">\r\n" + 
				"				<button type=\"button\" class=\"btn btn-primary\" onclick=\"doSave()\">신규저장</button>\r\n" + 
				"				<button type=\"button\" class=\"btn btn-secondary\" data-dismiss=\"modal\">취소</button>\r\n" + 
				"			</div>\r\n" + 
				"		</div>\r\n" + 
				"	</div>\r\n" + 
				"</div>";

				createFile("webapp\\WEB-INF\\adm\\" + folderName + "\\", initName + ".jsp", jspCode, params.get("reload"));
				return jspCode;
	}
	
	private String getInitName(List<GeneratorVO> genList) {
		for(GeneratorVO gen : genList) {
			if(gen.getColumnKey()!=null && gen.getColumnKey().equals("PRI")) {
					String initName = gen.getColumnName();
					return initName.substring(0,initName.indexOf("_")).toLowerCase();
			}
		}
		return "";
	}
	private String getJavaType(GeneratorVO gen) {
		String dataType = gen.getDataType();
		if(dataType.equals("tinyint")||dataType.equals("smallint")
			||dataType.equals("mediumint")||dataType.equals("int")) {
			dataType =  "Integer";
		}else if(dataType.equals("char")||dataType.equals("varchar")
				||dataType.equals("tinytext")||dataType.equals("text")
				||dataType.equals("mediumtext")||dataType.equals("longtext")) {
			dataType =  "String";
		}else if(dataType.equals("decimal")) {
			dataType =  "Double";
		}
		return dataType;
	}
	@PostMapping("/gen/voCode")
	public @ResponseBody String getVOCode(@RequestBody Map<String,String> params) {
		initGenList(params);
		String pkColName = "";
		String camelTableName = toCamelCase(tableName);
		camelTableName = camelTableName.substring(0,1).toUpperCase() + camelTableName.substring(1) + "VO";
		
		String voCode = "package kr.co.momdeal.vo;\r\n" + 
				"\r\n" + 
				"import org.apache.ibatis.type.Alias;\r\n" + 
				"import org.springframework.web.multipart.MultipartFile;\r\n" + 
				"import kr.co.momdeal.validator.ValidImage;\r\n" +
				"import lombok.Data;\r\n\r\n" + 
				"import javax.validation.constraints.NotNull;\r\n" + 
				"import javax.validation.constraints.Size;\r\n" + 
				"\r\n" + 
				"@Alias(\"" + initName + "\")\r\n" + 
				"@Data\r\n" + 
				"public class " + camelTableName + " {\r\n" + 
				"\r\n";

		for(GeneratorVO gen : genList) {
			String colName = gen.getColumnName();
			String camelName = toCamelCase(colName);
			String isNullable = gen.getIsNullable();
			String javaType = gen.getJavaType();
			String isPk = gen.getColumnKey();
			if(!isPk.equals("PRI") && isNullable.equals("NO")) {
				if(!camelName.equals("credat")&&!camelName.equals("cretim")
					&&!camelName.equals("moddat") && !camelName.equals("modtim")) {
					voCode += "	@NotNull\r\n";
				}
			}
			if(gen.getCvo()!=null && gen.getCvo().getValids()!=null && !gen.getCvo().getValids().isEmpty()) {
				for(String valid : gen.getCvo().getValids()) {
					if(valid.indexOf("message=")!=-1) {
						String message =  valid.substring(valid.indexOf("message="), valid.lastIndexOf(")"));
						message = message.substring(0,message.indexOf("=")+1) + "\"" + message.substring(message.indexOf("=")+1) + "\")";
						valid = valid.substring(0,valid.indexOf("message=")) +message;
					}
					voCode += "	" + valid + "\r\n";
				}
			}
			voCode += "	private " + javaType + " " + camelName + ";\r\n";
			if(camelName.toLowerCase().indexOf("img")!=-1) {
				camelName = camelName.replace("Name", "");
				voCode +=  "	@ValidImage\r\n";
				voCode +=  "	private MultipartFile " + camelName + ";\r\n";
			} 
			if(gen.getCvo()!=null) {
				if(gen.getCvo().getFkGenList()!=null) {
					for(GeneratorVO fkGen : gen.getCvo().getFkGenList()) {
						voCode += "	private " + fkGen.getJavaType() + " " + fkGen.getCamelName() + ";\r\n";
					}
				}
			}
		}
		voCode += "	private String orders;\r\n";
		voCode += "}";

		createFile("java\\kr\\co\\momdeal\\vo\\", camelTableName + ".java", voCode,params.get("reload"));
		return voCode;
	}

	@PostMapping("/gen/jsVOCode")
	public @ResponseBody String getJSVOCode(@RequestBody Map<String,String> params) {
		List<Map<String,String>> tableList = gm.selecTableList();
		String allJsVOCode = "";
		log.info("tableList=>{}",tableList);
		for(Map<String,String> table : tableList) {
			log.info("table=>{}",table);
			String tableName = table.get("TABLE_NAME");
			List<GeneratorVO> genList = gm.selectColumnList(tableName);
			String camelTableName = toCamelCase(tableName);
			camelTableName = camelTableName.substring(0,1).toUpperCase() + camelTableName.substring(1);
			
			String voCode = "export class " + camelTableName + " {\r\n"; 

			for(GeneratorVO gen : genList) {
				String colName = gen.getColumnName();
				String camelName = toCamelCase(colName);
				String dataType = gen.getDataType();
				if(dataType.equals("tinyint")||dataType.equals("smallint")
					||dataType.equals("mediumint")||dataType.equals("int")) {
					dataType =  "number";
				}else if(dataType.equals("char")||dataType.equals("varchar")
						||dataType.equals("tinytext")||dataType.equals("text")
						||dataType.equals("mediumtext")||dataType.equals("longtext")) {
					dataType =  "string";
				}else if(dataType.equals("decimal")) {
					dataType =  "Double";
				}
				if(colName.toLowerCase().equals("active")) {
					voCode += "	" + camelName + " : " + dataType + "='1';\r\n";
				}else {
					voCode += "	" + camelName + " : " + dataType + ";\r\n";
				}
				if(camelName.toLowerCase().indexOf("img")!=-1) {
					camelName = camelName.replace("Name", "");
					voCode +=  "	" + camelName + " : File;\r\n";
				} 
			}
			voCode += "	orders:string;\r\n";
			voCode += "}\r\n";
			allJsVOCode += voCode;
			createFile("java\\kr\\co\\momdeal\\jsvo\\", tableName.replaceAll("_", "-") + ".ts", voCode,params.get("reload"));
		}


		return allJsVOCode;
	}
	@PostMapping("/gen/mapperCode")
	public @ResponseBody String getMapperCode(@RequestBody Map<String,String> params) {
		initGenList(params);
		String selectId = "select" + initName.toUpperCase();
		String selectListId = selectId + "List";
		String insertId = "insert" + initName.toUpperCase();
		String updateId = "update" + initName.toUpperCase();
		String deleteId = "delete" + initName.toUpperCase(); 
		
		String camelTableName = toCamelCase(tableName);
		camelTableName = camelTableName.substring(0,1).toUpperCase() + camelTableName.substring(1);

		String mapperCode = "package kr.co.momdeal.mapper;\r\n" + 
				"\r\n" + 
				"import org.mybatis.spring.annotation.MapperScan;\r\n" + 
				"import com.github.pagehelper.Page;\r\n" + 
				"import kr.co.momdeal.vo." + camelTableName + "VO" + ";\r\n" + 
				"\r\n" + 
				"@MapperScan\r\n" + 
				"public interface " + camelTableName + "Mapper {\r\n" + 
				"	Page<" + camelTableName + "VO> "+ selectListId + "("+ camelTableName + "VO " + initName + ");\r\n" + 
				"	"+ camelTableName + "VO " + selectId + "(int " + pkCamelName + ");\r\n" + 
				"	int " + updateId + "("+ camelTableName + "VO " + initName + ");\r\n" + 
				"	int " + insertId + "("+ camelTableName + "VO " + initName + ");\r\n" + 
				"	int " + deleteId + "(int " + pkCamelName + ");\r\n" + 
				"}";
		createFile("java\\kr\\co\\momdeal\\mapper\\", camelTableName + "Mapper.java", mapperCode,params.get("reload"));
		return mapperCode;
	}
	
	private void createFile(String path, String name, String context, String string) {
		FileOutputStream outputStream; 
		try {
			
			Path currentRelativePath = Paths.get("");
			File f = new File(currentRelativePath.toAbsolutePath().toString() + "\\src\\main\\" + path + name);
			log.info("not overwrite file=>{}",f.exists() && !reload);
			if(f.exists() && !reload) {
				return;
			}
			outputStream =  new FileOutputStream(currentRelativePath.toAbsolutePath().toString() + "\\src\\main\\" + path + name);
			byte[] strToBytes = context.getBytes();
			outputStream.write(strToBytes);
			outputStream.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@PostMapping("/gen/controllerCode")
	public @ResponseBody String getControllerCode(@RequestBody Map<String,String> params) {
		initGenList(params);
		String folderName = params.get("folderName");
		String tableName = params.get("tableName");
		List<GeneratorVO> genList = gm.selectColumnList(tableName);
		String initName = getInitName(genList);
		String selectId = "select" + initName.toUpperCase();
		String selectListId = selectId + "List";
		String insertId = "insert" + initName.toUpperCase();
		String updateId = "update" + initName.toUpperCase();
		String deleteId = "delete" + initName.toUpperCase(); 
		String pkColName = "";
		String camelTableName = toCamelCase(tableName);
		camelTableName = camelTableName.substring(0,1).toUpperCase() + camelTableName.substring(1);
		List<String> uniList = new ArrayList<>();
		
		for(int i=0;i<genList.size();i++) {
			GeneratorVO gen = genList.get(i);
			if(!gen.getColumnKey().trim().equals("") && gen.getColumnKey()!=null) {
				if(gen.getColumnKey().equals("PRI")) {
					pkColName += gen.getColumnName();
				}else if(gen.getColumnKey().equals("UNI")) {
					uniList.add(gen.getColumnName());
				}
			}
		}
		String controllerCode = "package kr.co.momdeal.controller;\r\n" + 
				"\r\n" + 
				"import java.io.Closeable;\r\n" + 
				"import java.util.List;\r\n" + 
				"import java.util.Map;\r\n" + 
				"\r\n" + 
				"import javax.annotation.Resource;\r\n" + 
				"\r\n" + 
				"import org.springframework.web.bind.annotation.CrossOrigin;\r\n" + 
				"import org.springframework.web.bind.annotation.GetMapping;\r\n" + 
				"import org.springframework.web.bind.annotation.ModelAttribute;\r\n" + 
				"import org.springframework.web.bind.annotation.PathVariable;\r\n" + 
				"import org.springframework.web.bind.annotation.PostMapping;\r\n" + 
				"import org.springframework.web.bind.annotation.RequestBody;\r\n" + 
				"import org.springframework.web.bind.annotation.RestController;\r\n" + 
				"\r\n" + 
				"import kr.co.momdeal.service." + camelTableName + "Service;\r\n" + 
				"import kr.co.momdeal.vo.PaginationVO;\r\n" + 
				"import kr.co.momdeal.vo." + camelTableName + "VO;\r\n" + 
				"\r\n" + 
				"import lombok.extern.slf4j.Slf4j;\r\n" + 
				"\r\n" + 
				"@RestController\r\n" + 
				"@Slf4j\r\n" + 
				"public class " + camelTableName + "Controller {\r\n" + 
				"\r\n" + 
				"	@Resource\r\n" + 
				"	private " + camelTableName + "Service " + initName + "Service;\r\n" + 
				"\r\n" + 
				"	@GetMapping(\"/" + folderName + "/" + initName +"s\")\r\n" + 
				"	public  Closeable " + selectListId + "(" + camelTableName + "VO " + initName + ", PaginationVO page){\r\n" + 
				"		return " + initName + "Service." + selectListId + "(" + initName + ", page);\r\n" + 
				"	}\r\n" + 
				"	@GetMapping(\"/" + folderName + "/" + initName + "/{" + pkCamelName + "}\")\r\n" + 
				"	public  " + camelTableName + "VO " + selectId + "(@PathVariable(\"" + pkCamelName +  "\") int " + pkCamelName + "){\r\n" + 
				"		return " + initName + "Service." + selectId + "(" + pkCamelName + ");\r\n" + 
				"	}\r\n" + 
				"	@PostMapping(\"/" + folderName + "/" + initName + "\")\r\n" + 
				"	public  Map<String,Object> " + insertId + "(@ModelAttribute  " + camelTableName + "VO " + initName + ") {\r\n" + 
				"		return " + initName + "Service." + insertId + "("+ initName + ");\r\n" + 
				"	}\r\n" + 
				"	@PostMapping(\"/" + folderName + "/" + initName + "/mod\")\r\n" +
				"	public Map<String,Object> " + updateId + "(@ModelAttribute " + camelTableName + "VO " + initName + ") {\r\n" + 
				"		return " + initName + "Service." + updateId + "("+ initName + ");\r\n" + 
				"	}\r\n" + 
				"	@PostMapping(\"/" + folderName + "/" + initName + "s/del\")\r\n" +
				"	public Map<String,Object> " + deleteId + "(@RequestBody List<Integer> nums) {\r\n" + 
				"		return " + initName + "Service." + deleteId + "(nums);\r\n" + 
				"	}\r\n" + 
				"}\r\n";

		createFile("java\\kr\\co\\momdeal\\controller\\", camelTableName + "Controller.java", controllerCode,params.get("reload"));
		return controllerCode;
	}
	
	@PostMapping("/gen/serviceCode")
	public @ResponseBody String getServiceCode(@RequestBody Map<String,String> params) {
		String folderName = params.get("folderName");
		String tableName = params.get("tableName");
		List<GeneratorVO> genList = gm.selectColumnList(tableName);
		
		String initName = getInitName(genList);
		String selectId = "select" + initName.toUpperCase();
		String selectListId = selectId + "List";
		String selectAllListId = selectId + "AllList";
		String insertId = "insert" + initName.toUpperCase();
		String updateId = "update" + initName.toUpperCase();
		String deleteId = "delete" + initName.toUpperCase(); 
		String pkColName = "";
		String camelTableName = toCamelCase(tableName);
		camelTableName = camelTableName.substring(0,1).toUpperCase() + camelTableName.substring(1);
		List<String> uniList = new ArrayList<>();
		
		for(int i=0;i<genList.size();i++) {
			GeneratorVO gen = genList.get(i);
			if(!gen.getColumnKey().trim().equals("") && gen.getColumnKey()!=null) {
				if(gen.getColumnKey().equals("PRI")) {
					pkColName += gen.getColumnName();
				}else if(gen.getColumnKey().equals("UNI")) {
					uniList.add(gen.getColumnName());
				}
			}
		}
		String serviceCode = "package kr.co.momdeal.service;\r\n" + 
				"\r\n" + 
				"import java.util.HashMap;\r\n" + 
				"import java.util.List;\r\n" + 
				"import java.util.Map;\r\n" + 
				"\r\n" + 
				"import javax.annotation.Resource;\r\n" + 
				"\r\n" + 
				"import org.springframework.stereotype.Service;\r\n" + 
				"\r\n" + 
				"import com.github.pagehelper.Page;\r\n" + 
				"import com.github.pagehelper.PageHelper;\r\n" + 
				"import kr.co.momdeal.exception.ServiceException;\r\n" + 
				"import kr.co.momdeal.mapper." + camelTableName + "Mapper;\r\n" + 
				"import kr.co.momdeal.utils.FileUtils;\r\n" + 
				"import kr.co.momdeal.vo.PaginationVO;\r\n" + 
				"import kr.co.momdeal.vo." + camelTableName + "VO;\r\n" + 
				"\r\n" + 
				"import lombok.extern.slf4j.Slf4j;\r\n" + 
				"\r\n" + 
				"@Slf4j\r\n" + 
				"@Service\r\n" + 
				"public class " + camelTableName + "Service {\r\n" + 
				"\r\n" + 
				"	@Resource\r\n" + 
				"	private " + camelTableName + "Mapper " + initName + "Mapper;\r\n" + 
				"	\r\n" + 
				"	@Resource\r\n" + 
				"	private FileUtils fu;\r\n" + 
				"	\r\n" + 
				"	private String path = \"" + initName + "\";\r\n" + 
				"	\r\n" + 
				"	public Page<" + camelTableName + "VO> " + selectListId + "(" + camelTableName + "VO " + initName + ",PaginationVO page) {\r\n" + 
				"		PageHelper.startPage(page.getPageNum(), page.getPageSize());\r\n" + 
				"		return " + initName + "Mapper." + selectListId + "(" + initName + ");\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	public List<" + camelTableName + "VO> " + selectListId + "() {\r\n" + 
				"		return " + initName + "Mapper." + selectListId + "(null);\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	public " + camelTableName + "VO " + selectId + "(int " + pkColName + ") {\r\n" + 
				"		return " + initName + "Mapper." + selectId + "(" + pkColName + ");\r\n" + 
				"	}\r\n" + 
				"		\r\n" + 
				"	public Map<String,Object> " + updateId + "(" + camelTableName + "VO " + initName + ") {\r\n" + 
				"		Map<String,Object> rMap = new HashMap<>();\r\n" + 
				"		if(!fu.autoSaveFile(" + initName + ", path)) {\r\n" + 
				"			throw new ServiceException(\"파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.\");\r\n" + 
				"		}\r\n" + 
				"		int rCnt = " + initName + "Mapper." + updateId + "(" + initName + ");\r\n" + 
				"		if(rCnt!=1) {\r\n" + 
				"			throw new ServiceException(\"디비 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.\");\r\n" + 
				"		}\r\n" + 
				"		rMap.put(\"cnt\", rCnt);\r\n" + 
				"		rMap.put(\"result\",\"ok\");\r\n" + 
				"		return rMap;\r\n" + 
				"	}\r\n" + 
				"	\r\n" + 
				"	public Map<String,Object> " + insertId + "(" + camelTableName + "VO " + initName + ") {\r\n" + 
				"		Map<String,Object> rMap = new HashMap<>();\r\n" + 
				"		if(!fu.autoSaveFile(" + initName + ", path)) {\r\n" + 
				"			throw new ServiceException(\"파일 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.\");\r\n" + 
				"		}\r\n" + 
				"		int rCnt = " + initName + "Mapper." + insertId + "(" + initName + ");\r\n" + 
				"		if(rCnt!=1) {\r\n" + 
				"			throw new ServiceException(\"DB 등록 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.\");\r\n" + 
				"		}\r\n" + 
				"		rMap.put(\"cnt\", rCnt);\r\n" + 
				"		rMap.put(\"result\",\"ok\");\r\n" + 
				"		return rMap;\r\n" + 
				"	}\r\n" + 
				"	public Map<String,Object> " + deleteId + "(List<Integer> nums) {\r\n" + 
				"		Map<String,Object> rMap = new HashMap<>();\r\n" + 
				"		int rCnt = 0;\r\n" + 
				"		for(int num : nums) {\r\n" + 
				"			rCnt += " + initName + "Mapper." + deleteId + "(num);\r\n" + 
				"		}\r\n" + 
				"		if(rCnt!=nums.size()) {\r\n" + 
				"			throw new ServiceException(\"삭제 중 문제가 발생하였습니다. 다시 시도해주시기 바랍니다.\");\r\n" + 
				"		}\r\n" + 
				"		rMap.put(\"cnt\", rCnt);\r\n" + 
				"		rMap.put(\"result\",\"ok\");\r\n" + 
				"		return rMap;\r\n" + 
				"	}\r\n" + 
				"}";
		createFile("java\\kr\\co\\momdeal\\service\\", camelTableName + "Service.java", serviceCode,params.get("reload"));
		return serviceCode;
	}
	
	@GetMapping("/gen/{folderName}/{tableName}")
	public String generateListJsp(@PathVariable("folderName") String folderName,@PathVariable("tableName") String tableName, @RequestParam Map<String,String> param,  Model m) {
		System.out.println("generator on");
		m.addAttribute("folderName",folderName);
		m.addAttribute("tableName",tableName);
		return "/generateJsp";
	}
	
	public static void main(String[] args) {
		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString() + "\\src\\main\\java\\";
		System.out.println("Current relative path is: " + s);
	}
}
