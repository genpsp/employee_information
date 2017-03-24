<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="imageWidth" value="250" />
<c:set var="imageHight" value="250" />

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>社員情報</title>
</head>
<body>
	<h1>社員情報</h1>
	社員ID:${employee.empID }
	<br /> 名前:${employee.name }
	<br /> 年齢:${employee.age }
	<br /> 性別:
	<c:choose>
		<c:when test="${employee.sex == 0 }">男</c:when>
		<c:otherwise>女</c:otherwise>
	</c:choose><br />
	写真:
	<br />
	<img
		src="/CodeCampFinal/MainServlet?empID=${employee.empID }&requestType=getImage"
		width="${imageWidth }" height="${imageHight }" />
	<c:if test="${authority == admin }|| ${authority == manager }">
		<br /> 郵便番号:${employee.addressNum }
		<br /> 都道府県:${employee.city }
		<br /> 住所:${employee.address }
	</c:if>
	<br /> 所属:${employee.department.department }
	<br /> 役職:${employee.position.positon }
	<br /> 入社日:${employee.enterDate }
	<br /> 退社日:${employee.retireDate }
	<br />

	<a href="/CodeCampFinal/MainServlet?requestType=戻る">戻る</a>
</body>
</html>