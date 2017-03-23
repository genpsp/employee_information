<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部署編集画面</title>
</head>
<body>
	<c:choose>
		<c:when test="${depart == null }">
			<p>部署データを新規作成します</p>
		</c:when>
		<c:otherwise>
			<p>部署データを編集します</p>
		</c:otherwise>
	</c:choose>
	<form action="/CodeCampFinal/DepartmentServlet" method="post">
		部署名:<input type="text" name="department" value="${depart.department }" required><br />
		<c:choose>
			<c:when test="${depart == null }">
				<input type="hidden" name="requestType" value="設定">
			</c:when>
			<c:otherwise>
				<input type="hidden" name="requestType" value="更新">
			</c:otherwise>
		</c:choose>
		<input type="submit" value="設定"><br />
	</form>
	<form action="/CodeCampFinal/DepartmentServlet" method="post">
		<input type="submit" name="requestType" value="キャンセル">
	</form>
</body>
</html>