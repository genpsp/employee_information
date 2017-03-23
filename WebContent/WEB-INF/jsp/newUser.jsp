<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>ユーザー登録</h1>
	<form action="/CodeCampFinal/LoginServlet" method="post">
		名前:<input type="text" name="name"><br />
		パスワード:<input type="password" name="pass"><br />
		メールアドレス:<input type="text" name="mail"><br/>
		<input type="submit" name="requestType" value="登録">
	</form>
</body>
</html>