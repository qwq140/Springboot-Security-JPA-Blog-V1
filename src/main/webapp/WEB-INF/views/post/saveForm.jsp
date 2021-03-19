<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>

<div class="container">
	<form>
		<div class="form-group">
			<input type="text" class="form-control" placeholder="Enter Title" name="title" id="title"/>
		</div>
		<div class="form-group">
			<textarea rows="" cols="5" class="form-control" name="content" id="content"></textarea>
		</div>
		<button type="button" id="btn-save" class="btn btn-primary">Submit</button>
	</form>
</div>
<script>
      $('#content').summernote({
        tabsize: 2,
        height: 300
      });

      $("#btn-save").on("click",(e)=>{

    	  let data = {
    			title:$("#title").val(),
    			content:$("#content").val()
    	  };

  		console.log(data);
    			
    	  $.ajax({
    			type:"POST",
    			url:"/post",
    			data:JSON.stringify(data),
    			contentType:"application/json; charset=utf-8",
    			dataType:"json"
    	  }).done((res)=>{
    			console.log(res);
    		    if(res.statusCode===1){
    				location.href="/";
    			} else {
    				alert("등록 실패");
    			}
    	  });

       });
</script>

<%@include file="../layout/footer.jsp"%>