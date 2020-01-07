<%@ page language="java" contentType="text/html; charset=UTF-8"

    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>

<html>

<head>

<meta charset="UTF-8">

<title>상세 보기</title>

</head>

<body>

<center>

<h2>상세 보기</h2>

<hr>

<table>

	<tr height="30">

  <td width="100" bgcolor="pink" align="center">NUM</td>

  <td colspan="5">${board.bnum}</td>

	</tr>

	<tr height="30">

  <td bgcolor="pink" align="center">WRITER</td>

  <td width="150">${board.mname}</td>

  <td bgcolor="pink" align="center">DATE</td>

  <td width="200">${board.bdate}</td>

  <td bgcolor="pink" align="center">VIEWS</td>

  <td width="100">${board.bviews}</td>

	</tr>

	<tr height="30">

  <td bgcolor="pink" align="center">TITLE</td>

  <td colspan="5">${board.btitle}</td>

	</tr>

	<tr height="200">

  <td bgcolor="pink" align="center">CONTENTS</td>

  <td colspan="5">${board.bcontents}</td>

	</tr>

	<tr>

  <th>첨부파일</th>

  <td colspan="5">

  <c:if test="${empty bfList}">

  첨부된 파일이 없습니다.

  </c:if>

  <c:if test="${!empty bfList}">

  <c:forEach var="file" items="${bfList}">

  <a href="./download?sysFileName=${file.bf_sysName}">${file.bf_oriName}</a>

  </c:forEach>

  </c:if>	

  </td>

	</tr>

</table>

<p/>

<form name="rFrm" id="rFrm">

	<table>

  <tr>

  <td><textarea rows="3" cols="50" name="r_contents" id="comment"></textarea></td>

  <td><input type="button" value="댓글전송"

   onclick="replyInsert(${board.bnum})"

   style="width:70px;height:50px"></td>

  </tr>	

	</table>

</form>

<p/>

<table>

	<tr bgcolor="skyblue" align="center" height="30">

  <td width="100">WRITER</td>

  <td width="200">CONTENTS</td>

  <td width="200">DATE</td>

	</tr>

</table> <!-- rTable에 댓글 리스트만 Ajax로 처리위해 -->

<table id="rTable">	

<c:forEach var="r" items="${rList}">

	<tr height="25" align="center">

  <td width="100">${r.r_id}</td>

  <td width="200">${r.r_contents}</td>

  <td width="200">${r.r_date}</td>

	</tr>

</c:forEach>	

</table>

</center>

</body>

</html>