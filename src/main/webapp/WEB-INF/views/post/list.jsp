<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@include file="../layout/header.jsp"%>

<div class="container">
	<div class="d-flex justify-content-between">
		<h5>게시판</h5>
		<form class="form-inline" action="/" method="GET">
			<input class="form-control mr-sm-2" type="text" placeholder="Search" name="keyword" value="${param.keyword }"/>
			<button class="btn btn-secondary" type="submit">Search</button>
		</form>
	</div>
	<table class="table">
		<thead class="thead-light">
			<tr>
				<th style="width: 8%">번호</th>
				<th style="width: 67%">제목</th>
				<th style="width: 25%">글쓴이</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="post" items="${posts.content }">
				<tr>
					<td>${post.id }</td>
					<td><a href="/post/${post.id}" class="text-dark">${post.title }</a></td>
					<td>${post.user.username }</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<ul class="pagination justify-content-center">
		<c:choose>
			<c:when test="${posts.first }">
				<li class="page-item disabled"><a class="page-link" href="?page=${posts.number-1 }&keyword=${param.keyword}">Previous</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${posts.number-1 }&keyword=${param.keyword}">Previous</a></li>
			</c:otherwise>
		</c:choose>
		<c:choose>
			<c:when test="${posts.last }">
				<li class="page-item disabled"><a class="page-link" href="?page=${posts.number+1 }&keyword=${param.keyword}">Next</a></li>
			</c:when>
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${posts.number+1 }&keyword=${param.keyword}">Next</a></li>
			</c:otherwise>
		</c:choose>
	</ul>
</div>
<%@include file="../layout/footer.jsp"%>