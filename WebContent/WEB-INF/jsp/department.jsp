<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>部署一覧</title>
</head>
<body>
	<h1>部署一覧:</h1>
	<table border="1">
		<tr>
			<td>ID</td>
			<td>部署名</td>
		</tr>
		<c:forEach var="department" items="${departmentList }">
			<tr>
				<td>${department.id }</td>
				<td>${department.department }</td>
				<td><form action="/CodeCampFinal/DepartmentServlet"
						method="get">
						<input type="hidden" name="departID" value="${department.id }">
						<input type="hidden" name="department" value="${department }">
						<input type="submit" name="requestType" value="編集">
					</form></td>
				<td><form action="/CodeCampFinal/DepartmentServlet"
						method="get">
						<input type="hidden" name="departID" value="${department.id }">
						<input type="hidden" name="department" value="${department }">
						<input type="submit" name="requestType" value="削除">
					</form></td>
			</tr>
		</c:forEach>
	</table>
	<form action="/CodeCampFinal/DepartmentServlet" method="get">
		${errorMsg }
		<input type="submit" name="requestType" value="新規追加"><br /> <input
			type="submit" name="requestType" value="TOPへ">
	</form>
</body>
</html>