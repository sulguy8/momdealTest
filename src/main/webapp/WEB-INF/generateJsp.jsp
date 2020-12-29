<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/config.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shoping Admin Login</title>
<c:import url="/WEB-INF/common/head.jsp"/>
<style>
body{
	color:white;
	
}
</style>
</head>
<script>
window.onload = function(){
	var btns = document.querySelectorAll("button");
	for(var btn of btns){
		btn.onclick = function(){
			var id = this.innerText;
			var texts = document.querySelectorAll("textarea");
			for(var txt of texts){
				txt.style.display = "none";
			}
			var data = {
					folderName : $('#folderName').val(),
					tableName : $('#tableName').val(),
					reload : $('[name=reload]:checked').val()
			}
			$.ajax({
				url : '/gen/' + id,
				data : JSON.stringify(data),
				contentType: "application/json; charset=utf-8",
				method:'POST',
				success : function(res){
					if(id!=='allCode'){
						document.querySelector('#' + id).style.display = '';
						document.querySelector('#' + id).value = res;
					}
				}
			})

			//
		}	
	}
	
	
}
</script>
<body class="bg-dark">
<button>allCode</button>
<button>jsVOCode</button>
<button>jspCode</button>
<button>xmlCode</button>
<button>mapperCode</button>
<button>voCode</button>
<button>serviceCode</button>
<button>controllerCode</button><br>
<br>
파일 자동 오버라이트 ON : <input type="radio" name="reload" value="1">
OFF : <input type="radio" name="reload" value="0" checked><br>
folder Name : <input type="text" id="folderName" value="${folderName}"><br>
table Name : <input type="text" id="tableName" value="${tableName}"><br>
jsVOCode
<textarea id="jsVOCode" style="display:none" cols=200 rows=35>
${jsVOCode}
</textarea>
<textarea id="jspCode" style="display:none" cols=200 rows=35>
${jspCode}
</textarea>
<textarea id="xmlCode" style="display:none" cols=200 rows=35>
${xmlCode}
</textarea>
<textarea id="mapperCode" style="display:none" cols=200 rows=35>
${mapperCode}
</textarea>
<textarea id="voCode" style="display:none" cols=200 rows=35>
${voCode}
</textarea>
<textarea id="serviceCode" style="display:none" cols=200 rows=35>
${serviceCode}
</textarea> 
<textarea id="controllerCode" style="display:none" cols=200 rows=35>
${controllerCode}
</textarea> 
<c:import url="/WEB-INF/common/bottom.jsp"/>
</body>
</html>