<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>
<div class="content">
	<div class="container">
		<div class="row justify-content-center">
			<div class="col-md-6 contents">
				<div class="row justify-content-center">
					<div class="col-md-12">
						<div class="form-block">
							<div class="mb-4">
								<h3>Edit Profile</h3>
							</div>
							<form>
								<input type="hidden" id="id" value="${id }" />
								<div class="form-group first">
									<input type="text" class="form-control" value="${principal.user.username }" id="username" name="username" readonly="readonly">
								</div>
								<div class="form-group first">
									<input type="password" class="form-control" id="password" name="password" placeholder="Password">
								</div>
								<div class="form-group">
									<input type="email" class="form-control" id="email" name="email" value="${principal.user.email }" />
								</div>
								<br/>
								<input id="btn-update" value="회원수정" class="btn btn-pill text-white btn-block btn-success">
							</form>
						</div>
					</div>
				</div>

			</div>

		</div>
	</div>
</div>




<script>
	$("#btn-update").on("click",(e)=>{
		e.preventDefault();
		let data = {
			username:$("#username").val(),
			password:$("#password").val(),
			email:$("#email").val()
		};

		let id = $("#id").val();
		
		$.ajax({
			type:"PUT",
			url:"/user/"+id,
			data:JSON.stringify(data),
			contentType:"application/json; charset=utf-8",
			dataType:"json"
		}).done((res)=>{
			console.log(res);
			if(res.statusCode===1){
				alert("수정에 성공하였습니다.");
				location.href="/";
			} else {
				alert("수정에 실패하였습니다.")
			}
		});
		
	}); /* 리스너 클릭되는 것을 지켜봄 */
</script>
<script src="/js/jquery-3.3.1.min.js"></script>
<script src="/js/popper.min.js"></script>
<script src="/js/bootstrap.min.js"></script>
<script src="/js/main.js"></script>
<%@include file="../layout/footer.jsp"%>