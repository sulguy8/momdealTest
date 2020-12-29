/**
 * 
 */

var validation = function(valiObjs,flag) {
	for(var inputObj of valiObjs){
		var vali = inputObj.getAttribute('data-val');
		var key = inputObj.getAttribute('data-col');
		if(vali && flag===key.substring(0,1)){
			var valids = vali.split(',');
			for(val of valids){
				valiFor(inputObj,val);
			}
		}
	}
}

function valiFor(inputObj,val){
		var vali = val;
		var value = inputObj.value;
		var valiMsg = inputObj.getAttribute('data-val-msg');
		var valiType = vali.split('=')[0];
		var valiTaget = vali.split('=')[1];
		var valiMin = Number.parseInt(valiTaget.split(':')[0]);
		var valiMax = Number.parseInt(valiTaget.split(':')[1]);
		if(valiType==='len'){
			if(value.trim().length<valiMin||valiMax<value.trim().length){
				if(valiMsg===null){
					valiMsg ='해당값을 확인해주세요.'
				}
				alert(valiMsg);
				inputObj.focus();
				throw valiMsg;
			}
		}else if(valiType==='maxMin'){
			value = Number.parseInt(value);
			if((valiMin!=='&0'&&value<valiMin)||valiMax<value||false){
				if(valiMsg===null){
					valiMsg ='해당값을 확인해주세요.'
				}
				alert(valiMsg);
				inputObj.focus();
				throw valiMsg;
			}
		}else if(valiType==='img'){
			if(value===''){
				if(valiMsg===null){
					valiMsg ='이미지 입력값을 확인해주세요.'
				}
				alert(valiMsg);
				inputObj.focus();
				throw valiMsg;
			}	
		}else if(valiType==='com'){
			if(value===''){
				if(valiMsg===null){
					alert('콤보박스를 선택해주세요.');
				}else{
					alert(valiMsg);
				}
				inputObj.focus();
				throw valiMsg;
			}	
			
		
	}
	
}