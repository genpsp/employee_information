<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Not Found</title>
</head>
<body>
	<p>登録されている社員がいません</p>
	<form action="/CodeCampFinal/MainServlet" method="get">
		<input type="submit" name="requestType" value="新規追加"><br />
		<input type="submit" name="requestType" value="検索...">
	</form>
</body>
</html>