<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/common/config.jspf"%>
<!-- Sidebar -->
<ul class="sidebar navbar-nav">

	<li class="nav-item dropdown" data-folder="system">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="false"
		aria-expanded="true"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>시스템설정</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="#" data-page="cui">중간관리자설정</a> 
			<a class="dropdown-item" href="#" data-page="point">포인트설정</a> 
			<a class="dropdown-item" href="#" data-page="bri">브랜드설정</a>
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>일반회원</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">회원관리</a> 
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>샵회원관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">샵회원관리</a>
			<a class="dropdown-item" href="login.html">샵관리</a> 
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>상품관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">티켓상품</a>
			<a class="dropdown-item" href="login.html">배송상품</a> 
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>예약관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">예약내역</a>
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>판매관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">상품판매관리</a>
			<a class="dropdown-item" href="login.html">티켓판매관리</a>
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>정산관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">상품/티켓</a>
			<a class="dropdown-item" href="login.html">포인트정산</a>
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>환급/환불</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">환불신청건</a>
			<a class="dropdown-item" href="login.html">환급신청건</a>
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>게시판관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">샤핑소식</a>
			<a class="dropdown-item" href="login.html">이벤트</a>
			<a class="dropdown-item" href="login.html">체험단(상품)</a>
			<a class="dropdown-item" href="login.html">체험단(티켓)</a>
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>커뮤니티관리</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">뷰티샵Story</a>
			<a class="dropdown-item" href="login.html">화장품Story</a>
			<a class="dropdown-item" href="login.html">티켓리뷰</a>
			<a class="dropdown-item" href="login.html">제품리뷰</a>
		</div>
	</li>
	
	<li class="nav-item dropdown">
		<a class="nav-link dropdown-toggle" href="#" id="pagesDropdown"
		role="button" data-toggle="dropdown" aria-haspopup="true"
		aria-expanded="false"> 
			<i class="fas fa-fw fa-folder"></i> 
			<span>고객문의</span>
		</a>
		<div class="dropdown-menu" aria-labelledby="pagesDropdown">
			<a class="dropdown-item" href="login.html">유저문의</a>
			<a class="dropdown-item" href="login.html">샵회원문의</a>
		</div>
	</li>
</ul>