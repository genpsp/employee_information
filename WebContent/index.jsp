<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TOPページ</title>
</head>
<body>
	<h1>社員情報管理ツール</h1>
	<c:choose>
		<c:when test="${not empty User }">
			<a href="/CodeCampFinal/MainServlet?requestType=戻る">社員一覧</a>
			<br />
			<a href="/CodeCampFinal/DepartmentServlet?requestType=戻る">部署一覧</a>
		</c:when>
		<c:otherwise>
		${loginMsg }<br/>
		ログイン:
			<form action="/CodeCampFinal/LoginServlet" method="post">
				社員ID:<input type="text" name="empID" size="6" required><br />
				パスワード:<input type="password" name="pass" required><br/>
				<input type="submit" name="requestType" value="ログイン">
			</form>
			<p>
				<a href="/CodeCampFinal/LoginServlet?requestType=新規">ユーザー登録をする</a>
			</p>
		</c:otherwise>
	</c:choose>
</body>
</html>