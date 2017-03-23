<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="imageWidth" value="250" />
<c:set var="imageHight" value="250" />

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員編集画面</title>
</head>
<body>
	<h1>データ編集</h1>aaaa
	<form action="/CodeCampFinal/MainServlet" method="post"
		enctype="multipart/form-data">
		社員ID:<input type="text" name="empID" value="${employee.empID }" placeholder="EMP001"><br />
		名前:<input type="text" name="name" value="${employee.name }" required><br />
		年齢:<input type="text" name="age" value="${employee.age }" required><br />
		性別:
		<input type="radio" name="sex" value="0" <c:if test="${employee.sex == 0 }">checked</c:if>>男性
		<input type="radio" name="sex" value="1" <c:if test="${employee.sex == 1 }">checked</c:if>>女性<br />
		写真:<br />
		<c:if test="${empID != null }">
			<img src="/CodeCampFinal/MainServlet?empID=${empID }&requestType=getImage" width="${imageWidth }" height="${imageHight }" />
			<br />
		</c:if>
		<input type="file" name="file"><br />

		郵便番号:<input type="text" name="addressNum" value="${employee.addressNum }" size="8" placeholder="123-4567" required><br />
		都道府県:<select name="city" required>
			<c:forEach var="city" items="${cityList }">
				<option value="${city }" <c:if test="${city == employee.city }">selected</c:if>>${city }</option>
			</c:forEach>
		</select>

		住所:<input type="text" name="address" value="${employee.address }" size="15" required><br />
		所属:<select name="department" required>
			<c:forEach var="department" items="${departmentList }">
				<option value="${department.id }" <c:if test="${department.id == employee.department.id }">selected</c:if>>${department.department }</option>
			</c:forEach>
		</select><br/>

		入社日:<input type="text" name="enterDate" value="${employee.enterDate }" size="10" placeholder="2016-04-01" required>
		退社日:<input type="text" name="retireDate" value="${employee.retireDate }" size="10" placeholder="2016-03-31"><br />
		<c:choose>
			<c:when test="${employee == null }">
				<input type="hidden" name="requestType" value="設定">
			</c:when>
			<c:otherwise>
				<input type="hidden" name="requestType" value="更新">
			</c:otherwise>
		</c:choose>
		<input type="submit" value="設定"><br />
	</form>
	<form action="/CodeCampFinal/MainServlet" method="post">
		<input type="submit" name="requestType" value="キャンセル">
	</form>
</body>
</html>