<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- 경로 : src/main/webapp/WEB-INF/views.boardCOntents.jsp -->

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style type="text/css">
         table{
            width: 600px;
         }
         table, td, th{
            border: 1px solid;
            border-collapse: collapse;
            padding: 5px;
         }
         input[type='text']{
            width: 98%
         }
         textarea{
            width: 98%;
            resize: none;
         }
         th{
            background-color: plum;
         }
      </style>
</head>
<body>

<center>

<table>
		<tr><h3>게시판</h3></tr>
		<tr>
			<th><p>번호</p></th>
			<td>${board.bnum}</td>
		</tr>
		<tr>
			<th><p>제목</p></th>
			<td>${board.btitle}</td>
		</tr>
		<tr>
			<th><p>작성자</p></th>
			<td>${board.mname}</td>
		</tr>
		<tr>
			<th><p>작성일</p></th>
			<td>${board.bdate}</td>
		</tr>
		<tr>
			<th><p>조회수</p></th>
			<td>${board.bviews}</td>
		</tr>
		<tr>
			<th><p>글 내용</p></th>
			<td>${board.bcontents}</td>
		</tr>
		<tr>
			<th><p>첨부파일</p></th>
			<td><c:if test="${empty bfList}">
		<p>첨부된 파일이 없습니다.</p>
			</c:if>
			<c:if test="${!empty bfList}">
			<c:forEach var='file' items="${bfList}">
				<p> - ${file.bf_oriName}</p>
			</c:forEach></td>
			</c:if>
		</tr>
		
		

</table>

</center>
</body>
</html>




