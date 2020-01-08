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
 <script type="text/javascript">
      		window.onload = function() {//이 페이지가 실행 되면, 이 함수를 실행해라
				var chk = "${check}";
				if(chk != ""){
					alert(chk);
					location.reload(true);//화면을 다시 한번 불러오면서 check를 리셋함 
				}
			}
</script>
<script type="text/javascript">
         window.onload = function(){
            var chk = ${check};
            
            if(chk == 2){
               alert("글 등록 성공!");
               location.reload(true);
            }
         }
</script>

</head>
<body>	
	<center>
		<h3>게시판 리스트</h3><hr>
		<div style="width: 600px; ">
			<div align="right">
					<a href = "../logout">로그아웃</a>
					<!-- .logout과 ../logout 의 차이는? 상위 몇번째로 올라가니?-->
			</div>
			<div align="left">
					<table>
						<tr height="30">
							<td width="80" bgcolor="hotpink" align="center">ID</td>
							<td>${mb.m_id}</td><!-- mb는 session에서 넣어줬다! -->
						</tr>
					<tr height="30">
							<td width="80" bgcolor="hotpink" align="center">이름</td>
							<td>${mb.m_name}</td>
						</tr>
						<tr height="30">
							<td width="80" bgcolor="hotpink" align="center">등급</td>
							<td>${mb.g_name}</td>
						</tr>
						<tr height="30">
							<td width="80" bgcolor="hotpink" align="center">포인트</td>
							<td>${mb.m_point}</td>
						</tr>
					</table>
			
			</div>
			
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
		<p>
		<button onclick="location.href='./writeFrm'">글쓰기</button>
		</p>
		</div>
	</center>


	
</body>
</html>




