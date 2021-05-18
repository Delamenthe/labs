<%@page language="java" pageEncoding="UTF-8"%>
<%-- Импортировать JSTL-библиотеку --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="my" tagdir="/WEB-INF/tags"%>
<%@taglib prefix="ad" uri="http://lab9.by/tags"%>
<ad:logout name="${sessionScope.authUser.name}" />
<%-- Удалить из сессии JavaBean с данными пользователя --%>
<c:remove var="authUser" scope="session" />
<%-- Переадресовать на начальную страницу --%>
<c:redirect url="/index.jsp" />