<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員検索</title>
</head>
<body>
	<p>条件を指定して社員情報を検索します</p>
	<form action="/CodeCampFinal/MainServlet" method="get">
		所属部署:<select name="department">
			<option value="all">未指定</option>
			<c:forEach var="department" items="${departmentList }">
				<option value="${department.id }">${department.department }</option>
			</c:forEach>
		</select><br /> 社員ID:<input type="text" name="empID"><br /> 名前に含む文字:<input
			type="text" name="searchString"><br /> <input type="submit"
			name="requestType" value="検索"><br /> <input type="submit"
			name="requestType" value="戻る">
	</form>
</body>
</html>