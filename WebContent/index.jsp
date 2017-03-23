<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TOPページ</title>
</head>
<body>
	<h1>社員情報管理ツール</h1>
	<c:choose>
		<c:when test="${User != null }">
			<a href="/CodeCampFinal/MainServlet?requestType=戻る">社員一覧</a>
			<br />
			<a href="/CodeCampFinal/DepartmentServlet?requestType=戻る">部署一覧</a>
		</c:when>
		<c:otherwise>
			<a href="/CodeCampFinal/LoginServlet?requestType=新規">ユーザー登録をする</a>
		</c:otherwise>
	</c:choose>
</body>
</html>