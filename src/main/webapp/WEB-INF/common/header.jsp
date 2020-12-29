<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <nav class="navbar navbar-expand-sm navbar-dark bg-dark">
      <a class="navbar-brand" href="/">Shoping</a>
      <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarsExample03" aria-controls="navbarsExample03" aria-expanded="false" aria-label="Toggle navigation">
        <span class="navbar-toggler-icon"></span>
      </button>

      <div class="collapse navbar-collapse" id="navbarsExample03">
        <ul class="navbar-nav mr-auto">
        <!-- 
          <li class="nav-item active">
            <a class="nav-link" href="#">Home <span class="sr-only">(current)</span></a>
          </li>
          <li class="nav-item">
            <a class="nav-link" href="#">Link</a>
          </li>
          <li class="nav-item">
            <a class="nav-link disabled" href="#">Disabled</a>
          </li>
         -->
          <li class="nav-item dropdown" data-folder="system">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">시스템설정</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="bri">브랜드 설정</a>
              <a class="dropdown-item" href="#" data-page="cod">공통코드 설정</a>
              <a class="dropdown-item" href="#" data-page="lof">지역필터</a>
              <a class="dropdown-item" href="#" data-page="bii">배너 이미지 관리</a>
              <a class="dropdown-item" href="#" data-page="cpc">포인트 정책</a>
            </div>
          </li>
          <li class="nav-item dropdown" data-folder="usermanage">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">회원 관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="cui">일반회원 관리</a>
              <a class="dropdown-item" href="#" data-page="cul">간편로그인 관리</a>
              <!-- <a class="dropdown-item" href="#" data-page="cus">고객 배송지 관리</a> -->
              <!-- <a class="dropdown-item" href="#" data-page="cup">고객 포인트</a> -->   
              <a class="dropdown-item" href="#" data-page="cph">포인트 적립차감 이력</a>
              <a class="dropdown-item" href="#" data-page="cmh">적립금 적립차감 이력</a>
            </div>
          </li>
          <li class="nav-item dropdown" data-folder="shop"> 
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">샵 관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="sai">중간관리자 관리</a>
              <a class="dropdown-item" href="#" data-page="shi">샵 관리</a>
              <!-- <a class="dropdown-item" href="#" data-page="sbi">샵 은행 </a> -->
              <a class="dropdown-item" href="#" data-page="sph">샵 적립차감 이력</a>
              <a class="dropdown-item" href="#" data-page="shp">샵 포인트 정책</a>
              <a class="dropdown-item" href="#" data-page="shf">샵 필터 관리</a>
              <a class="dropdown-item" href="#" data-page="mai">관리사 관리</a>
            </div>
          </li>
          <li class="nav-item dropdown" data-folder="goods">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">상품관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="tii">티켓상품</a>
              <!-- <a class="dropdown-item" href="#" data-page="goi">배송상품</a> -->
              <a class="dropdown-item" href="#" data-page="goc">상품 카테고리 관리</a>
            </div>
          </li>
          <li class="nav-item dropdown" data-folder="reservation">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">예약관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="rei">예약내역</a>
              <a class="dropdown-item" href="#" data-page="mei">문자상담내역</a>
            </div>
          </li>
          <li class="nav-item dropdown" data-folder="system">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">판매관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <!-- <a class="dropdown-item" href="#" data-page="cui">상품판매관리</a> -->
              <a class="dropdown-item" href="#" data-page="tis">티켓판매관리</a>
            </div>
          </li>
		  <li class="nav-item dropdown" data-folder="settlement">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">정산관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="sph2">포인트정산</a>
              <!-- <a class="dropdown-item" href="#" data-page="cui">포인트정산</a> -->
            </div>
          </li>
<!--           <li class="nav-item dropdown" data-folder="system">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">환급/환불</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="cui">환불신청건</a>
              <a class="dropdown-item" href="#" data-page="cui">환급신청건</a>
            </div>
          </li> -->
          <li class="nav-item dropdown" data-folder="board">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">게시판관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="noi">샵핑소식</a>
              <a class="dropdown-item" href="#" data-page="cuq">1:1문의</a>
              <!-- <a class="dropdown-item" href="#" data-page="cui">이벤트</a>
              <a class="dropdown-item" href="#" data-page="cui">체험단(상품)</a>
              <a class="dropdown-item" href="#" data-page="cui">체험단(티켓)</a> -->
            </div>
          </li>
          <li class="nav-item dropdown" data-folder="free">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">무료체험</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="fei">무료체험관리</a>
              <a class="dropdown-item" href="#" data-page="fai1">신청자관리</a>
              <a class="dropdown-item" href="#" data-page="fai2">선정자관리</a>
              <a class="dropdown-item" href="#" data-page="fer">체험리뷰관리</a>
            </div>
          </li>
          <li class="nav-item dropdown" data-folder="community">
            <a class="nav-link dropdown-toggle" href="#" id="dropdown03" 
            data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">커뮤니티관리</a>
            <div class="dropdown-menu" aria-labelledby="dropdown03">
              <a class="dropdown-item" href="#" data-page="bes">뷰티샵Story</a>
              <!-- <a class="dropdown-item" href="#" data-page="cos">화장품Story</a> -->
              <a class="dropdown-item" href="#" data-page="gor">티켓리뷰</a>
              <!-- <a class="dropdown-item" href="#" data-page="cui">제품리뷰</a> -->
            </div>
          </li>
          <li class="nav-item dropdown no-arrow">
	        <a class="nav-link dropdown-toggle" href="#" id="userDropdown" role="button" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
	          <i class="fas fa-user-circle fa-fw"></i>
	        </a>
	        <div class="dropdown-menu dropdown-menu-right" aria-labelledby="userDropdown">
	          <a class="dropdown-item" href="#" onclick="testAutoCancel()">AutoCancelTest</a>
	          <div class="dropdown-divider"></div>
	          <a class="dropdown-item" href="#" onclick="testFree()">무료체험(00시-전체)</a>
	          <div class="dropdown-divider"></div>
   	          <a class="dropdown-item" href="#" onclick="testFree2()">무료체험(14시-미작성)</a>
	          <div class="dropdown-divider"></div>
	          <a class="dropdown-item" href="#">Settings</a>
	          <div class="dropdown-divider"></div>
	          <a class="dropdown-item" href="#" onclick="localStorage.clear();window.location.reload(true);">초기화</a>
	          <div class="dropdown-divider"></div>
	          <a class="dropdown-item" href="#" data-toggle="modal" data-target="#logoutModal">Logout</a>
	        </div>
      	  </li>
        </ul>
        
      </div>
    </nav>
    <script>
    	function testAutoCancel(){
    		return $.ajax({
    		    url: '/test/cancel',
    		    type: 'GET',
    		    async: true,
    	        processData:false,
    	        contentType:false,
    		    success: function(res){
    		    	console.log(res);
    		    },
    		    error: function (request, status, error){
    		    	console.log(request);
    		    }
    		});
    	}
    	function testFree(){
    		return $.ajax({
    		    url: '/test/free',
    		    type: 'GET',
    		    async: true,
    	        processData:false,
    	        contentType:false,
    		    success: function(res){
    		    	console.log(res);
    		    },
    		    error: function (request, status, error){
    		    	console.log(request);
    		    }
    		});
    	}
    	function testFree2(){
    		return $.ajax({
    		    url: '/test/free2',
    		    type: 'GET',
    		    async: true,
    	        processData:false,
    	        contentType:false,
    		    success: function(res){
    		    	console.log(res);
    		    },
    		    error: function (request, status, error){
    		    	console.log(request);
    		    }
    		});
    	}    	
    </script>
