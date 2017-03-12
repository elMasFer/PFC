<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<li class="dropdown">
	<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">
		<c:choose>
			<c:when test="${pageContext.response.locale.language == 'es'}">
				<span class="lang-xs lang-lbl" lang="es"></span><span class="caret"></span>
			</c:when>
			<c:otherwise>
				<span class="lang-xs lang-lbl" lang="en"></span><span class="caret"></span>
			</c:otherwise>
		</c:choose>
	</a>
	<ul class="dropdown-menu" role="menu">
		<li>
			<a id="navSpa" href="?locale=es" class="language"> 
				<span class="lang-xs lang-lbl" lang="es"></span>
			</a>
		</li>
		<li>
			<a id="navEng" href="?locale=en" class="language">
				<span class="lang-xs lang-lbl" lang="en"></span>
			</a>
		</li>
	</ul>
</li>
