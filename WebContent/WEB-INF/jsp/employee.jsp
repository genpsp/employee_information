<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員一覧</title>
</head>
<body>
	<h1>社員一覧:</h1>
	<c:choose>
		<c:when test="${employeeList == null }">
			<jsp:include page="/WEB-INF/jsp/employeeNotFound.jsp" />
		</c:when>
		<c:otherwise>
			<table border="1">
				<tr>
					<td>社員ID</td>
					<td>名前</td>
				</tr>
				<c:forEach var="employee" items="${employeeList }">
					<tr>
						<td>${employee.empID }</td>
						<td>${employee.name }</td>
						<td><form action="/CodeCampFinal/MainServlet" method="get">
								<input type="hidden" name="empID" value="${employee.empID }">
								<input type="hidden" name="employee" value="${employee }">
								<input type="submit" name="requestType" value="編集">
							</form></td>
						<td><form action="/CodeCampFinal/MainServlet" method="get">
								<input type="hidden" name="empID" value="${employee.empID }">
								<input type="hidden" name="employee" value="${employee }">
								<input type="submit" name="requestType" value="削除">
							</form></td>
					</tr>
				</c:forEach>
			</table>
			<form action="/CodeCampFinal/MainServlet" method="get">
				${errorMsg }
				<input type="submit" name="requestType" value="新規追加"><br />
				<input type="submit" name="requestType" value="検索..."><br />
				<input type="submit" name="requestType" value="CSVファイルに出力"><br/>
				<input type="submit" name="requestType" value="TOPへ">
			</form>
		</c:otherwise>
	</c:choose>
</body>
</html>