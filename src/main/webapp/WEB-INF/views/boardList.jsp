<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<!-- 경로 : src/main/webapp/WEB-INF/views -->
<title>게시글 목록</title>
<style type="text/css">
	html, body{
		height: 100%;
		margin: 0px;
	}

</style>
</head>
<body>
	<center>
		<h3>게시판 리스트</h3>
		<table>
			<tr bgcolor="aqua" height="30">
				<th width="100">번호</th>
				<th width="200">제목</th>
				<th width="100">작성자</th>
				<th width="150">작성일</th>
				<th width="100">조회수</th>
			</tr>	
			<c:forEach var="bitem" items ="${bList}">
				<tr height="25">
					<td align="center">${bitem.bnum}</td>
					<td align="center"><a href="contents?bnum=${bitem.bnum}" >${bitem.btitle}</a></td>
					<!-- get방식으로 bnum을 넘기고, 제목에 링크 걸겠다.  ?뒤는 get방식으로 넘길 데이터를 보냄.-->
					<td align="center">${bitem.mname}</td>
					<td align="center">${bitem.bdate}</td>
					<td align="center">${bitem.bviews}</td>
				</tr>
			</c:forEach>		
		</table>
		
		<div>
			${paging}
		</div>
	</center>


	
</body>
</html>




