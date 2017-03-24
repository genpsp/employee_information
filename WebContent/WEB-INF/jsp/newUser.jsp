<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>ユーザー登録</h1>
	<form action="/CodeCampFinal/LoginServlet" method="post">
		社員ID:<input type="text" name="empID" required><br />
		パスワード:<input type="password" name="pass" required><br />
		<input type="submit" name="requestType" value="登録">
	</form>
</body>
</html>