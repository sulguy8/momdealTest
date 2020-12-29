<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/config.jspf"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Shoping Admin</title>
<script src="https://cdn.ckeditor.com/ckeditor5/18.0.0/classic/ckeditor.js"></script>
<c:import url="/WEB-INF/common/head.jsp" />
</head>

<script>
var codMap,lofMap,gocTicMap,gocMapDlv,mapAll;
var shopMaps = {};
var shopList = {};
var _ajax = $.ajax;
$.ajax = function(conf){
	if(!conf.error){
		conf.error = function(res){
			if(res.responseJSON && res.responseJSON.errCode=='err03'){
				alert('로그인이 만료되었습니다.');
				location.href='/adm/login';
				return;
			}
			console.log(res);
		}
	}else{
		conf._error = conf.error;
		conf.error = function(res){
			if(res.responseJSON && res.responseJSON.errCode=='err03'){
				alert('로그인이 만료되었습니다.');
				location.href='/adm/login';
				return;
			}
			conf._error(res);
		}
	}
	_ajax(conf);
}
$.ajax({
	url : '/system/all',
    dataType: "json",
    async: false,
	success : function(res){
		codMap = res.codMap;
		lofMap = res.lofMap;
		shopMaps.gocTicMap = res.gocTicMap;
		shopMaps.mapAll = res.mapAll;
		shopMaps.gocDlvMap = res.gocDlvMap;
		shopList.gocMapAll = res.gocMapAll;
		shopList.gocDlvList = res.gocDlvList;
		shopList.gocTicList = res.gocTicList;
	}
})


$.fn.dataTable.pipeline = function ( opts ) {
    // Configuration options
    var conf = $.extend( {
        pages: 0,     // number of pages to cache
        url: '',      // script url
        data: null,   // function or object with parameters to send to the server
                      // matching how `ajax.data` works in DataTables
        method: 'GET' // Ajax HTTP method
    }, opts );
 
    // Private variables for storing the cache
    var cacheLower = -1;
    var cacheUpper = null;
    var cacheLastRequest = null;
    var cacheLastJson = null;
 
    return function ( request, drawCallback, settings ) {
        var ajax          = true;
        var requestStart  = request.start;
        var drawStart     = request.start;
        var requestLength = request.length;
        var requestEnd    = requestStart + requestLength;
         
        // Store the request for checking next time around
        cacheLastRequest = $.extend( true, {}, request );
 		
        if ( ajax ) {
            // Need data from the server
            if ( requestStart < cacheLower ) {
                requestStart = requestStart - (requestLength*(1-1));
 
                if ( requestStart < 0 ) {
                    requestStart = 0;
                }
            }
             
            cacheLower = requestStart;
            cacheUpper = requestStart + (requestLength * 1);
 
            request.start = requestStart;
            request.length = requestLength*1;
 
            // Provide the same `data` options as DataTables.
            if ( typeof conf.data === 'function' ) {
                // As a function it is executed with the data object as an arg
                // for manipulation. If an object is returned, it is used as the
                // data object to submit
                var d = conf.data( request );
                if ( d ) {
                    $.extend( request, d );
                }
            }
            else if ( $.isPlainObject( conf.data ) ) {
                // As an object, the data given extends the default
                $.extend( request, conf.data );
            }
            var orderKey = request.columns[request.order[0].column].data;
            if(!orderKey){
            	orderKey = request.columns[request.order[0].column].name;
            }
			var orderTmpKey = '';
			var keyIdx =0;
			console.log(orderKey);
            while (keyIdx< orderKey.length){
                var character = orderKey.charAt(keyIdx);
                if(!/\d/.test(character) && character===character.toUpperCase()){
                	character = '_' + character.toLowerCase();
                }
                orderTmpKey += character;
                keyIdx++;
            }
 			request.orders = orderTmpKey + ' ' + request.order[0].dir;
 			request.columns = null;
 			
            settings.jqXHR = $.ajax( {
                "type":     conf.method,
                "url":      conf.url,
                "data":     request,
                "dataType": "json",
                "cache":    false,
                "success":  function ( json ) {
                	json.data = json.list;
                	
                    cacheLastJson = $.extend(true, {}, json);
 
                    if ( cacheLower != drawStart ) {
                        json.data.splice( 0, drawStart-cacheLower );
                    }
                    if ( requestLength >= -1 ) {
                        json.data.splice( requestLength, json.data.length );
                    }
                    drawCallback( json );
                }
            } );
        }
        else {
            json = $.extend( true, {}, cacheLastJson );
            json.draw = request.draw; // Update the echo for each response
            json.data.splice( 0, requestStart-cacheLower );
            json.data.splice( requestLength, json.data.length );
 
            drawCallback(json);
        }
    }
};
 
// Register an API method that will empty the pipelined data, forcing an Ajax
// fetch on the next draw (i.e. `table.clearPipeline().draw()`)
$.fn.dataTable.Api.register( 'clearPipeline()', function () {
    return this.iterator( 'table', function ( settings ) {
        settings.clearCache = true;
    } );
} );
$.fn.DataTable.ext.pager.numbers_length = 22;
var curPage = null;
var DataTable = function(conf){
	var dtDivObj = document.querySelector('#' + conf.id);
	var dtObj = {};
	var dtConfig = {
		serverSide: true,
	    stateSave: true,
	    bDestroy: true,
		dtObj : dtObj,
		pagingType : 'full_numbers',
		fixedColumns:   {
            heightMatch: 'none'
        },
		fnDrawCallback: function( oSettings ) {
			if(curPage){
				var tmpPage = curPage;
				curPage = null;
				this.fnPageChange(tmpPage);
				location.reload();
			}
			var allCheckObj = document.querySelector('#allCheck');
			if(allCheckObj){
				allCheckObj.checked = false;
			}
			
			document.querySelectorAll('button[name=deleteBtn]').forEach((obj,idx)=>{
				if(!obj.getAttribute('hasEvent')){
					obj.addEventListener('click',()=>{
						document.querySelector('#actionDelete').setAttribute('data-val',obj.getAttribute('data-pk'));
						$('#deleteModal').modal();
					})
					obj.setAttribute('hasEvent',true);
				}
			});
			
			
			if(!document.querySelector('#actionDelete').getAttribute('hasEvent')){
				document.querySelector('#actionDelete').addEventListener('click',(obj)=>{
					postJson({
						url :	conf.dtConf.delUrl,
						data : JSON.stringify(obj.target.getAttribute('data-val').split(",")),
						success : (res)=>{
							if(res.result=='ok'){
								$('#deleteModal').modal('hide');
								setTimeout( function () {
									alert('삭제 완료 되었습니다.');
									dtObj.ajax.reload(null,false);
								}, 100 );
							}
						},
						error : (err)=>{
							$('#deleteModal').modal('hide');
							if(err.responseJSON.message){
								alert(err.responseJSON.message);
							}
							setTimeout( function () {
								//dtObj.clearPipeline().draw();
							},100);
						}
					});
				});
				document.querySelector('#actionDelete').setAttribute('hasEvent',true);
			}
			if(document.querySelector('#actionSms') && !document.querySelector('#actionSms').getAttribute('hasEvent')){
				document.querySelector('#actionSms').addEventListener('click',(obj)=>{
					var param = {
						simSmsText : document.querySelector('#smsText').value
					}
					if('cuiNum' == conf.pks){
						Object.assign(param,{cuiNums : obj.target.getAttribute('data-val').split(",")});	
					}else{
						Object.assign(param,{saiNums : obj.target.getAttribute('data-val').split(",")});
					}					
					postJson({
						url :	'/surem/sms',
						data : JSON.stringify(param),
						success : (res)=>{
							console.log(res);
							if(res.result=='ok'){
								$('#smsModal').modal('hide');
								setTimeout( function () {
									alert('전송 되었습니다.\r\n요청합계 : ' + res.totalCnt + '\r\n성공 : ' + res.successCnt + '\r\n실패 : ' + res.failCnt);
									dtObj.ajax.reload(null,false);
								}, 100 );
							}else if(res.result=='ng'){
								$('#smsModal').modal('hide');
								setTimeout( function () {
									alert('전송 실패하였습니다.\r\n요청합계 : ' + res.totalCnt + '\r\n성공 : ' + res.successCnt + '\r\n실패 : ' + res.failCnt);
									dtObj.ajax.reload(null,false);
								}, 100 );
							}else{
								alert('ERROR 발생 / 개발사 문의');
							}
						},
						error : (err)=>{
							$('#smsModal').modal('hide');
							if(err.responseJSON.message){
								alert(err.responseJSON.message);
							}
							setTimeout( function () {
								//dtObj.clearPipeline().draw();
							},100);
						}
					});
				});
				document.querySelector('#actionSms').setAttribute('hasEvent',true);
			}
			if(document.querySelector('#actionEmail') && !document.querySelector('#actionEmail').getAttribute('hasEvent')){
				document.querySelector('#actionEmail').addEventListener('click',(obj)=>{
					var param = {
						emailText : document.querySelector('#emailText').value
					}
					if('cuiNum' == conf.pks){
						Object.assign(param,{cuiNums : obj.target.getAttribute('data-val').split(",")});	
					}else{
						Object.assign(param,{saiNums : obj.target.getAttribute('data-val').split(",")});
					}
					postJson({
						url :	'/send/event',
						data : JSON.stringify(param),
						success : (res)=>{
							console.log(res);
							if(res.result=='ok'){
								$('#emailModal').modal('hide');
								setTimeout( function () {
									alert('전송 되었습니다.\r\n요청합계 : ' + res.totalCnt + '\r\n성공 : ' + res.successCnt + '\r\n실패 : ' + res.failCnt);
									dtObj.ajax.reload(null,false);
								}, 100 );
							}else if(res.result=='ng'){
								$('#emailModal').modal('hide');
								setTimeout( function () {
									alert('전송 실패하였습니다.\r\n요청합계 : ' + res.totalCnt + '\r\n성공 : ' + res.successCnt + '\r\n실패 : ' + res.failCnt);
									dtObj.ajax.reload(null,false);
								}, 100 );
							}else{
								alert('ERROR 발생 / 개발사 문의');
							}
						},
						error : (err)=>{
							$('#emailModal').modal('hide');
							if(err.responseJSON.message){
								alert(err.responseJSON.message);
							}
							setTimeout( function () {
								//dtObj.clearPipeline().draw();
							},100);
						}
					});
				});
				document.querySelector('#actionEmail').setAttribute('hasEvent',true);
			}
	    },
	    scrollY:        "550px",
        scrollX:        true,
        scrollCollapse: true,
		searching: false,
		dom: 'Blrtip',
        buttons: [
            {
                extend: 'csv',
                text: 'Export csv',
                charset: 'utf-8',
                extension: '.csv',
                filename: 'export',
                bom: true,
                className: 'btn btn-primary glyphicon glyphicon-save-file'
            }            
        ],
		processing: true,
		serverSide: true
	};
	
	this.reload = function(){
		dtObj.ajax.reload(null,false);
	}
	this.search = function(url){
		dtObj.ajax.url(
				 $.fn.dataTable.pipeline( {
<c:if test="${!empty pk}">
					url: url + '&${pk}=${value}'
</c:if>
<c:if test="${empty pk}">
					url: url
</c:if>
			        })).load();
	}
	var htmlInit = function(){
		var headerHtml = '<div class="card-header">';
		headerHtml += '<i class="fas fa-table"></i>' + conf.title + '${subTitle}';
		headerHtml += '</div>'
		dtDivObj.insertAdjacentHTML('afterbegin',headerHtml);
		var tableHtml = '<div class="card-body">';
		tableHtml += '<div class="table-responsive">';
		tableHtml += '<table class="table table-bordered" id="' + conf.id + 'SubDT" class="display" style="width:100%">';
		tableHtml += '<thead>';
		tableHtml += '</thead>';
		tableHtml += '<tbody>';
		tableHtml += '</tbody>';
		tableHtml += '</table>';
		tableHtml += '</div>';
		tableHtml += '</div>';
		if(conf.smallText){
			tableHtml += '<div class="card-footer small text-muted" id="addBox">' + conf.smallText + '</div>';
		}
		dtDivObj.insertAdjacentHTML('beforeend',tableHtml);
	}
	var dataTableInit = async function(){

		if(conf.allCheck){
			dtConfig.buttons.push(
	            {
	            	text : '전체삭제',
	                attr: { id: 'allDeleteBtn' },
	                className: 'btn btn-primary glyphicon glyphicon-save-file'
	            }
			);
			dtConfig.order = [[1,'desc']];
			conf.dtConf.columns.splice(0,0,{
				  orderable :false,
		          data: null,
		          title:'<input type="checkbox" id="allCheck">',
		          className: "text-center",
		          width:70,
		          render: function ( data, type, row ) {
		            return '<input type="checkbox" name="' + conf.pks + '" value=" ' + data[conf.pks]+'">'  ;
		          }
		        });

		}
		if(conf.dtConf.smsBtn){
			dtConfig.buttons.push( 
	            {
	            	text : '문자전송',
	                attr: { id: 'sendSms' },
	                className: 'btn btn-primary glyphicon glyphicon-save-file'
	            }
			);
		}
		if(conf.dtConf.smsAllBtn){
			dtConfig.buttons.push( 
	            {
	            	text : '전체 문자 전송',
	                attr: { id: 'sendSmsAll' },
	                className: 'btn btn-primary glyphicon glyphicon-save-file'
	            }
			);
		}
		if(conf.dtConf.emailBtn){
			dtConfig.buttons.push( 
	            {
	            	text : '이메일 전송',
	                attr: { id: 'sendEmail' },
	                className: 'btn btn-primary glyphicon glyphicon-save-file'
	            }
			);
		}
		if(conf.dtConf.url){
			let lastUrl = conf.dtConf.url.substring(conf.dtConf.url.length-1,conf.dtConf.url.length);
			if(lastUrl != '&' && lastUrl != '?'){
				conf.dtConf.url += '?';
			}
			console.log(conf.dtConf)
			if(conf.dtConf.url.indexOf('/goods/gois?goiType=') == -1){
				conf.dtConf.url += 'active=1&'
			}			
			conf.dtConf.ajax = $.fn.dataTable.pipeline( {
<c:if test="${!empty pk}">
				url: conf.dtConf.url + '${pk}=${value}'
</c:if>
<c:if test="${empty pk}">
				url: conf.dtConf.url
</c:if> 
	        });
		}
		for(let key in conf.dtConf.columns){
			/* if(!conf.dtConf.columns[key].width){
				conf.dtConf.columns[key].width='200';
			} */
			if(conf.dtConf.columns[key].type==='co'){
				let codType = conf.dtConf.columns[key].data;
				let	cod = conf.dtConf.columns[key].cod;
				conf.dtConf.columns[key].data = null;
				conf.dtConf.columns[key].name = codType;
				conf.dtConf.columns[key].render = function(data,type,row){
					let key = cod?cod : codType;
					if(!codMap[key]|| !codMap[key][data[codType]]){
						return data[codType];
					}
					return codMap[key][data[codType]];
				}
			}else if(conf.dtConf.columns[key].type==='lb'){
				let url = conf.dtConf.columns[key].url
				let param = conf.pks;
				if(conf.dtConf.columns[key].param){
					param = conf.dtConf.columns[key].param;
				}
				conf.dtConf.columns[key].orderable = false;
				conf.dtConf.columns[key].render = function(data,type,row){
					let targetUrl = url;
					if(conf.dtConf.columns[key].subTitle){
						targetUrl += '&subTitle=' + row[conf.dtConf.columns[key].subTitle];
					}
					let value = '&value=' + row[param] + '&pk=' + param ;
					if(conf.dtConf.columns[key].subParam){
						value += '&subValue=' + row[conf.dtConf.columns[key].subParam] + '&subPk=' + conf.dtConf.columns[key].subParam ;
					}
					return '<button type="button" class="btn btn-primary" onclick="goPage(\'' + targetUrl + value+ '\')">이동</button>';
				}
			}else if(conf.dtConf.columns[key].type==='mb'){
				let url = conf.dtConf.columns[key].url
				let param = conf.pks;
				if(conf.dtConf.columns[key].param){
					param = conf.dtConf.columns[key].param;
				}
				conf.dtConf.columns[key].orderable = false;
				conf.dtConf.columns[key].render = function(data,type,row){
					return '<button type="button" class="btn btn-primary" onclick="' + conf.dtConf.columns[key].func + '(\'' + row[param] + '\')">이동</button>';
				}
			}else if(conf.dtConf.columns[key].type==='img'){
				let url = conf.dtConf.columns[key].url
				let param = conf.pks;
				if(conf.dtConf.columns[key].param){
					param = conf.dtConf.columns[key].param;
				}
				conf.dtConf.columns[key].render = function(data,type,row){
					return '<img src="${imgPath}' + data + '" width="100px" height: "auto"/>';
				}
			}
		}

		if(conf.dtConf.addBtn){
			dtConfig.buttons.push(
	            {
	            	text : '신규추가',
	                action: addModalOpen,
	                className: 'btn btn-primary glyphicon glyphicon-save-file'
	            }
			);
		}
		
		
		if(conf.dtConf.editBtn){
			conf.dtConf.columns.push({
		          data: null,
		          title:'수정',
		          className: "text-center",
		          width:70,
		          orderable :false,
		          render: function ( data, type, row ) {
		            return '<button type="button" class="btn btn-primary" onclick="updateModalOpen(\'' + data[conf.pks] + '\')">수정</button>';
		          }
		        }
			);
		}
		if(conf.dtConf.detailBtn){
			conf.dtConf.columns.push({
		          data: null,
		          title:'상세보기',
		          className: "text-center",
		          width:70,
		          orderable :false,
		          render: function ( data, type, row ) {
		            return '<button type="button" class="btn btn-primary" onclick="updateModalOpen(\'' + data[conf.pks] + '\')">상세보기</button>';
		          }
		        }
			);
		}
		if(conf.dtConf.answer){
			conf.dtConf.columns.push({
		          data: null,
		          title:'답변/수정',
		          className: "text-center",
		          width:70,
		          orderable :false,
		          render: function ( data, type, row ) {
		            return '<button type="button" class="btn btn-primary" onclick="updateModalOpen(\'' + data[conf.pks] + '\')">답변/수정</button>';
		          }
		        }
			);
		}

		if(conf.dtConf.deleteBtn){
			conf.dtConf.columns.push({
		          data: null,
		          'title':'삭제',
		          className: "text-center",
		          width:70,
		          orderable:false,
		          render: function ( data, type, row ) {
		          	return '<button type="button" name="deleteBtn" data-pk="' + data[conf.pks] + '" class="btn btn-primary">삭제</button>';
		          }
		        }
			);
		}
		dtConfig = $.extend(dtConfig,conf.dtConf);
		dtObj =$('#' + conf.id + 'SubDT').DataTable(dtConfig); 
		if(conf.hiddenColumns){
			for(var hIdx of conf.hiddenColumns){
				var col = dtObj.column(hIdx);
				col.visible(false);
			}
		}
		
		dtObj.on( 'page.dt', function (evt) {
		    var info = dtObj.page.info();
		    console.log(evt);
		    console.log(info);
		} );
		let searchObj = document.querySelector('.search');
		if(searchObj){
			var searchObjHtml= searchObj.outerHTML;
			searchObj.parentElement.removeChild(searchObj);
			document.querySelector('.dt-buttons').insertAdjacentHTML('afterend',searchObjHtml);
			
			if(document.querySelectorAll('[id$="lofSido"]').length!==0){
				document.querySelectorAll('[id$="lofSido"').forEach((obj,idx)=>{
					obj.addEventListener('change',function(){
						var key = this.value
						var prefix = ''
						if(this.id.split('-').length>1){
							prefix = this.id.split('-')[0] + '-';
						}
						
						var targetObj = document.querySelector('[id=' + prefix + 'lofGugun]');
						if(targetObj){
							targetObj.innerHTML = '';
							var subHtmlCod = '<option value="">전체</option>';
							for(let subKey in lofMap[key]){
								subHtmlCod += '<option value="' + subKey + '">' + subKey +  '</option>';
							}
							targetObj.insertAdjacentHTML('afterbegin',subHtmlCod);
						}else{
							targetObj = document.querySelector('[id=' + prefix + 'lofNum]');
							targetObj.innerHTML = '';
							var subHtmlCod = '<option value="">전체</option>';
							for(let subKey in lofMap[key]){
								subHtmlCod += '<option value="' + lofMap[key][subKey] + '">' + subKey +  '</option>';
							}
							targetObj.insertAdjacentHTML('afterbegin',subHtmlCod);
						}
					},false);
				});
					
			}
			document.querySelectorAll('select[data-cods]').forEach((obj,idx)=>{
				let cods = obj.getAttribute('data-cods').split(',');
				if(cods.length<=1){
					return;
				}
				
				let commonId = obj.id.substring(0,obj.id.length-1);
				let valueArrId = commonId+'s';
				if(!window[valueArrId]){
					window[valueArrId] = [];
				}
				let prefix = '';
				let surfix = obj.getAttribute('id');
				let depth = parseInt(cods[1]);
				if(obj.getAttribute('id').split('-').length>1){
					prefix = obj.getAttribute('id').split('-')[0] + '-';
					surfix = obj.getAttribute('id').split('-')[1];
				}
				surfix = surfix.substring(0,surfix.length-1);
				let htmlCode = '<option value="">전체</option>';
				if(window.shopMaps[cods[0]]){
					if(depth==1){
						for(let key1 in  window.shopMaps[cods[0]]){
							htmlCode += '<option value="' + key1 +'">' + key1 + '</option>';
						}
					}

					
					obj.onchange = function(){
						if(document.querySelectorAll('select[id^="' + commonId + '"]').length!=0){
							document.querySelectorAll('select[id^="' + commonId + '"]').forEach((tObj,idx)=>{
								if(obj!=tObj && cods[1]<tObj.getAttribute('data-cods').split(',')[1]){
									tObj.innerHTML = '<option value="">전체</option>';
								}
							});
						}
						if(document.querySelector('input[data-change^="' + commonId + '"]')){
							document.querySelector('input[data-change^="' + commonId + '"]').value = '';
						}
						window[valueArrId][depth-1] = obj.value;
						for(let idx = depth ;idx<window[valueArrId].length;){
							window[valueArrId].splice(idx,1);
						}
						var shopMapsObj = window.shopMaps[cods[0]];
						for(let val of window[valueArrId]){
							shopMapsObj = shopMapsObj[val];
						}
						let dataTarget = obj.getAttribute('data-target');
						if(dataTarget){
							document.querySelector('#' + dataTarget).value = shopMapsObj;
							return;
						}
						let subId = prefix + surfix + (depth + 1);
						let subObj = document.querySelector('select#' + subId + '[data-cods]');
						if(subObj){
							let subHtmlCode = '<option value="">전체</option>';
							for(let key1 in  shopMapsObj){
								subHtmlCode += '<option value="' + key1 +'">' + key1 + '</option>';
							}
							subObj.innerHTML = subHtmlCode;
						}
					}
					obj.innerHTML = htmlCode;
				}
			});
			
			document.querySelectorAll('input[data-target-cods]').forEach((obj,idx)=>{
				let cods = obj.getAttribute('data-cods');
				let targetCods = obj.getAttribute('data-target-cods').split(',');
				let codKey = obj.id.split('-')[1];
				if(window.shopList[cods]){
					obj.onchange = function(){
						for(let subCod of window.shopList[cods]){
							if(subCod[codKey]==this.value){
								for(let targetCod of targetCods){
									let targetCodeKey = targetCod.split('-')[1];
									document.querySelector('#' + targetCod).value = subCod[targetCodeKey];
									document.querySelector('#' + targetCod).dispatchEvent(new Event('change'));
								}
								return;
							}
						}
						console.log(window.shopList[cods])
					}
				}
			})
		}
		if(conf.paging===false){
			document.querySelector('#cpcTableSubDT_paginate').style.visibility="hidden";
		}
	}
	var depthOf = function(object,value,depth) {
	    var level = 1;
	    var key;
	    while(level<=depth){
	    	
	    }
	    for(key in object) {
	        if (!object.hasOwnProperty(key)) continue;
	        if(typeof object[key] == 'object'){
	        	if(depth && depth==level){
	        		return object[value];
	        	}else{
	        		object = depthOf(object[key])
	        	}
	        	level++;
	        }
	    }
	    return object;
	}
	var getOptionMap = function(obj, dept){
		
	}
	this.getCheckedValues = function(){
		var chkObjs = document.querySelectorAll('[name=' + conf.pks + ']:checked');
		var checkNums = [];
		chkObjs.forEach((obj,idx)=>{
			checkNums.push(obj.value);
		});
		return checkNums;
	}
	this.getAllValues = function(){
		var checkNums = [];
		$.ajax({
			url :	'/usermanage/selectAllList',
			method : 'GET',
		    contentType: 'application/json',
			data : {"cuiAgreeSms":"1"},
			success : conf.success?conf.success:function(res){
				res.forEach((obj,idx)=>{
					checkNums.push(obj.cuiPhone1);
				});
			},
			error : conf.error?conf.error:function(err){
			}
		});
		return checkNums;
	}
	
	this.getRowClick = function(){
		return conf.dtConf.rowClick;
	}
	var initEvent = ()=>{
		if(this.getRowClick()){
			$('#' + conf.id + 'SubDT').on('click','tr',function(a,b,c){
				var trElement = a.target.parentElement;
				if(a.target.type){
					if(a.target.type==='checkbox'){
						return;
					}
				}
				var pk = trElement.querySelector('input[name=' + conf.pks + ']').value;
				viewModalOpen(pk);
			})
		}
		if(document.querySelector('#allCheck')){
			document.querySelector('#allCheck').addEventListener('change',function(event){
				var checkObjs = document.querySelectorAll('[name=' + conf.pks +']');
				checkObjs.forEach(function(curObj,curIndex,listObj){
					curObj.checked = event.target.checked;
				})
			})
		}

		if(document.querySelector('#allDeleteBtn')){
			document.querySelector('#allDeleteBtn').addEventListener('click',()=>{
				 document.querySelector('#actionDelete').setAttribute('data-val',this.getCheckedValues());
				$('#deleteModal').modal(); 
			})
		}
		
		if(document.querySelector('#sendSms')){
			document.querySelector('#sendSms').addEventListener('click',()=>{
				document.querySelector('#actionSms').setAttribute('data-val',this.getCheckedValues());
				$('#smsModal').modal();	
			})
		}
		if(document.querySelector('#sendSmsAll')){
			document.querySelector('#sendSmsAll').addEventListener('click',()=>{
				document.querySelector('#actionSms').setAttribute('data-val',this.getAllValues());
				$('#smsModal').modal();
			})
		}
		if(document.querySelector('#sendEmail')){
			document.querySelector('#sendEmail').addEventListener('click',()=>{
				document.querySelector('#actionEmail').setAttribute('data-val',this.getCheckedValues());
				$('#emailModal').modal();
			})
		}
		
		
	}

	htmlInit();
	dataTableInit();
	initEvent();
}

function postJson(conf){
	$.ajax({
		url :	conf.url,
		method : 'POST',
	    contentType: 'application/json',
		data : conf.data,
		success : conf.success?conf.success:function(res){
			alert(res);
		},
		error : conf.error?conf.error:function(err){
			alert(err);
		}
	});
}

$(document).ready(function(){
	var dataFolder = '${pages[0]}';
	var dataPage = '${pages[1]}';
	var folderObj = document.querySelector('[data-folder=' + dataFolder + ']');
	if(folderObj){
		folderObj.className += ' active';
		var pageObj = folderObj.querySelector('[data-page=' + dataPage +']');
		if(pageObj){
			pageObj.className += ' active'
		}
	}
	$('.dropdown-item[data-page]').click(function(){
		var targetUrl = '/?page=';
		targetUrl += this.parentElement.parentElement.getAttribute('data-folder');
		targetUrl += '/' + this.getAttribute('data-page');
		location.href = targetUrl;
	})

	document.querySelectorAll('select[data-cod]').forEach((obj,idx)=>{
		let cod = obj.getAttribute('data-cod');
		let htmlCod = '<option value="">전체</option>';
		if(cod==='lofMap'){
			let id = obj.getAttribute('id');
			if(id.indexOf('lofSido')!=-1){
				for(let key in lofMap){
					htmlCod += '<option value="' + key + '">' + key +  '</option>';
				}
				obj.innerHTML = htmlCod;
			}
		}else if(cod==='agreeMap'){
			let name = obj.getAttribute('name');
			htmlCod='<option value=""> 전체</option>';
			htmlCod += '<option value="1">동의</option>';
			htmlCod += '<option value="0">미동의</option>';
			obj.innerHTML = htmlCod;
		}else if(window.shopMaps[cod]){
			console.log(window.shopMaps[cod]);
		}else{
			let dataFilters = obj.getAttribute('data-filters')?obj.getAttribute('data-filters').split(','):'';
			let subCodMap = codMap[cod];
			for(let key in subCodMap){
				if(dataFilters){
					console.log(dataFilters.indexOf(key));
					console.log(subCodMap[key]);
					if(dataFilters.indexOf(key)==-1) continue;	
				}
				let value = subCodMap[key];
				htmlCod += '<option value="' + key + '">' + value +  '</option>';
			}
			obj.insertAdjacentHTML('afterbegin',htmlCod);
		}
	});
	 $('div#addModal').on('hide.bs.modal', function(event) {
		 	$(this).find('input:not(disabled)').val('');
		 	$(this).find('select').val('');
		 	$(this).find('textarea').val('');
	 })
});

function checkValidation(valiType,valiValue,value,msg){
	if(valiType==='min'){
		if(value<valiValue){
			throw msg;
		}
	}
}
function getVali(obj){
	var vali = obj.getAttribute('data-vali');
	if(vali.indexOf('Size')===0){
		let vsIdx = vali.indexOf('(')+1;
		let veIdx = vali.lastIndexOf(')');
		vali = vali.substring(vsIdx,veIdx);
		var msg ='';
		if(vali.indexOf('message')!==-1){
			vsIdx = vali.indexOf('message');
			msg = vali.substring(vsIdx+7);
			vali = vali.substring(0, vsIdx);
		}
		var validations = vali.split(",");
		
		for(var validation of validations ){
			if(validation.trim()){
				checkValidation(validation.split('=')[0].trim(),validation.split('=')[1].trim(),obj.value,msg);
			}			
		}
	}
}
function validation(){
	var valiObjs = document.querySelectorAll('[data-vali]');
	for(let valiObj of valiObjs){
		try{
			getVali(valiObj);
		}catch(err){
			valiObj.focus();
			alert(err);
			return false;
		}
	}
	return true;
}

function goPage(url){
	location.href='/?' + url 
}
function tt(s){
	
	if(document.getElementById(s).style.display=="none"){
		
		document.getElementById(s).style.display="inline";
	}else{
		document.getElementById(s).style.display="none";
	}
	
}
</script>

<script src="${jsPath}/validation.js?ver=1.0.0" ></script> 
<body id="page-top">
	<!-- 메뉴 헤더 -->
	<c:import url="/WEB-INF/common/header.jsp" />
	<div id="wrapper">
		<div id="content-wrapper">
			<c:import url="/WEB-INF/adm/${curPage}.jsp" />
		</div>
	</div>
		<!-- 하단 카피라이터 -->
		<footer class="sticky-footer">
			<div class="container my-auto">
				<div class="copyright text-center my-auto">
					<span>Copyright © shoping 2019</span>
				</div>
			</div>
		</footer>
	<!-- 로그아웃 모달 & 삭제 모달-->
	<c:import url="/WEB-INF/common/commonModal.jsp"/>
</body>
</html>