<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>

<div class="container">
	<div>
		<button class="btn btn-secondary" onClick="history.go(-1)">뒤로가기</button>
		<c:if test="${post.user.id == principal.user.id }">
			<a href="/post/${post.id }/updateForm" class="btn btn-warning">수정</a>
			<button id="btn-delete" class="btn btn-danger" value="${post.id }">삭제</button>
		</c:if>
		<br />
		<br />
		<div class="d-flex justify-content-between">
			<span>글 번호 : ${post.id }</span> <span>작성자 : ${post.user.username }</span>
		</div>
		<hr />
		<div>
			<h3>${post.title }</h3>
		</div>
		<hr />
		<div>
			<div>${post.content }</div>
		</div>
	</div>
		<!-- 댓글 시작 -->
	<div class="card">
	    <form>
			<div class="card-body">
				<textarea id="reply-content" class="form-control" rows="1"></textarea>
			</div>
			<div class="card-footer">
				<button type="button" id="btn-reply-save" class="btn btn-primary">등록</button>
			</div>
		</form>
	</div>
	<br />
	
	<div class="card">
		<div class="card-header">댓글 리스트</div>
		<ul id="reply-box" class="list-group">
				<c:forEach var="reply" items="${post.reply }">
					<li id="reply-${reply.id }" class="list-group-item d-flex justify-content-between">
						<div>${reply.content }</div> <!-- 레이징로딩 시작 - 이유는 getter 호출되니까 (세션이 열려있음 open in view 모드에서만) -->
						<div class="d-flex">
							<div class="font-italic">작성자 : ${reply.user.username } &nbsp;</div>
							<button onClick="deleteReply(${reply.id})" class="badge">삭제</button>		
						</div>
					</li>
				</c:forEach>
		</ul>
	</div>
	<!-- 댓글 끝 -->
</div>
<script>
// for문으로 중첩되는 것들은 리스너가 아닌 onClick으로
	function deleteReply(id){
		$.ajax({
			type:"DELETE",
			url:"/reply/"+id,
			dataType:"json"
	  }).done((res)=>{
			console.log(res);
		    if(res.statusCode===1){
				$("#reply-"+id).remove();
			} else {
				alert("수정에 실패하였습니다.");
			}
	  });
	}
</script>

<script>
	$("#btn-delete").on("click",(e)=>{
		let id = e.currentTarget.value;

		$.ajax({
			type:"DELETE",
			url:"/post/"+id,
			dataType:"json"
		}).done(res=>{
			if(res.statusCode === 1){
				alert("삭제에 성공하였습니다.");
				history.back();
			} else {
				alert("삭제에 실패했습니다.");
			}
		});
	});
</script>
<%@include file="../layout/footer.jsp"%>