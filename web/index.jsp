<%@ page import="java.util.List" %>
<%@ page import="LabaPacks.UserActiveList" %>
<%@page language="java" contentType="text/html; charset=UTF-8"
		pageEncoding="UTF-8"%>
<%-- Импортировать JSTL-библиотеку --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- Импортировать собственную библиотеку тегов --%>
<%@taglib prefix="ad" uri="http://lab9.by/tags"%>
<%@taglib prefix="my" tagdir="/WEB-INF/tags"%>
<html>
<head>
	<title>Главная страница</title>
	<meta http-equiv='Content-Type' content='text/html; charset=UTF-8' />
</head>
<body>
<c:if test="${sessionScope.authUser!=null}">
	<div
			style="background-color: #ccc; border: 1px solid black; float: right; margin: 10px; margin-top: 20px; padding: 5px 0px; text-align: center; width: 150px;">
		<a href="<c:url value="/cabinet.jsp" />">В личный кабинет</a>
	</div>
</c:if>
<%-- Подключить заголовок страницы --%>
<jsp:include page="/static/header.jsp"></jsp:include>
<h1>Главная страница</h1>
<div class="w3-container w3-light-blue" style="float: right;">

	<h2>Active Users</h2>

	<%
		UserActiveList userActiveList = new UserActiveList();
		List<String> names = userActiveList.getUserActive();



		if (names != null && !names.isEmpty()) {

			for (String s : names) {
				out.println("<li class=w3-hover-sand>" + s + "</li>");
			}
			out.println("</ul>");

		}
	%>


</div>
<%-- Вставить разметку 2-колоночной страницы --%>
<my:layout2Columns leftColumnWidth="68%" rightColumnWidth="28%">
		<jsp:attribute name="leftColumnBody">
<%-- Содержание левой колонки передаѐтся как атрибут leftColumnBody --%>
			<%-- Извлечь список всех объявлений --%>
			<ad:getAds range="all" var="adListing" sort="${sessionScope.sort}"
					   dir="${sessionScope.dir}" />
			<%-- Показать объявления без возможности редактирования --%>
			<my:adListing adListing="${adListing}" editMode="false" />
	</jsp:attribute>
	<jsp:attribute name="rightColumnBody">
<%-- Содержание правой колонки передаѐтся как атрибут rightColumnBody --%>
		<%-- Вставить тег отображения сообщения об ошибке --%>
		<my:errorMessage />
		<%-- Вставить форму входа --%>
		<my:loginForm>
<jsp:attribute name="processor">
<%-- Адрес страницы-обработчика задаѐтся как атрибут processor
Т.к. необходимо "склеить" имя страницы с именем контекста,
используется JSTL тег c:url --%>
	<c:url value="/doLogin.jsp" />
</jsp:attribute>
</my:loginForm>
		<%-- Вставить ссылку регистрации --%>
		<my:registerButton>
<jsp:attribute name="processor">
<%-- Адрес страницы с формой регистрации передаѐтся как и
для страницы-обработчика формы регистрации --%>
	<c:url value="/register.jsp" />
</jsp:attribute>
</my:registerButton>
</jsp:attribute>
</my:layout2Columns>
<%-- Вставить нижний заголовок страницы --%>
<%@ include file="/static/footer.jsp"%>
</body>
</html>