<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정</title>
<script type="text/javascript">
      		window.onload = function() {//이 페이지가 실행 되면, 이 함수를 실행해라
				var chk = "${check}";
				if(chk == "fail"){
					alert("정보 수정 실패");
					location.reload(true);//화면을 다시 한번 불러오면서 check를 리셋함 
				}
			}
</script>
</head>
<center>
		<h2>정보 수정</h2>
		<hr>
		<form name="updateFrm" action="userUpdate" method="post">
			<table border="1">
				<tr>
					<td width="100">ID</td>
					<td><input type="text" name="m_id" title="ID" value="${member.m_id }" readonly="readonly"></td>
				</tr>
				<tr>
					<td width="100">PWD</td>
					<td><input type="password" name="m_pwd" title="PWD" required="required"></td>
				</tr> 
				<tr>
					<td width="100">NAME</td>
					<td><input type="text" name="m_name" title="NAME"value="${member.m_name }"></td>
				</tr>
				<tr>
					<td width="100">BIRTH</td>
					<td><input type="text" name="m_birth" title="BIRTH"value="${member.m_birth }"></td>
				</tr>
				<tr>
					<td width="100">ADDR</td>
					<td><input type="text" name="m_addr" title="ADDR"value="${member.m_addr }"></td>
				</tr>
				<tr>
					<td width="100">PHONE</td>
					<td><input type="text" name="m_phone" title="PHONE"value="${member.m_phone }"></td>
				</tr>
				<tr>
					<td colspan="2" class="subject" align="center"><input type="submit"	value="수정"></td>
				</tr>
			</table>
		</form>
	</center>


</body>
</html>