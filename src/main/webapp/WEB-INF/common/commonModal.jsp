<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<div class="modal fade" id="deleteModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">>
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title">삭제</h5>
				<button type="button" class="close" data-dismiss="modal" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div class="modal-body" id="modal-msg">선택한 데이터를 삭제하시겠습니까?</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="actionDelete">삭제</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="logoutModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">> 
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">로그아웃 하시겠습니까?</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body">로그아웃하실 경우 현재 세션이 사리지며 로그인 페이지로 이동합니다.</div>
			<div class="modal-footer">
				<a class="btn btn-primary" href="/logout/admin" >로그아웃</a>
				<button class="btn btn-secondary" type="button"
					data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="smsModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">>
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">SMS전송</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body" id="modal-msg">
				<h6>전송할 메세지를 작성해주세요.</h6>				
				<textarea id="smsText" style="resize:none;" cols="60" rows="5"></textarea>
				<h6>미동의유저 수신포함 <input id="smsAgree" type="checkbox"></h6>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="actionSms">전송</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>
<div class="modal fade" id="emailModal" tabindex="-1" role="dialog"
	aria-labelledby="exampleModalLabel" aria-hidden="true" data-keyboard="false" data-backdrop="static">>
	<div class="modal-dialog" role="document">
		<div class="modal-content">
			<div class="modal-header">
				<h5 class="modal-title" id="exampleModalLabel">이메일 전송</h5>
				<button class="close" type="button" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">×</span>
				</button>
			</div>
			<div class="modal-body" id="modal-msg">
				<h6>전송할 내용을 작성해주세요.</h6>				
				<textarea id="emailText" style="resize:none;" cols="60" rows="5"></textarea>				
				<h6>미동의유저 수신포함 <input id="emailAgree" type="checkbox"></h6>
			</div>
			<div class="modal-footer">
				<button type="button" class="btn btn-primary" id="actionEmail">전송</button>
				<button type="button" class="btn btn-secondary" data-dismiss="modal">취소</button>
			</div>
		</div>
	</div>
</div>