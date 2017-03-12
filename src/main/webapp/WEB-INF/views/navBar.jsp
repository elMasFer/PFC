<!DOCTYPE html>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<nav class="navbar navbar-default navbar-inverse">
  <div class="container-fluid">
   
    <div class="navbar-header">
     <span class="navbar-brand"><spring:message code="label.navBar.title" /></span>
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
    </div>
   
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
      </ul>
	  <ul class="nav navbar-nav navbar-right">
	     <jsp:include page="/WEB-INF/views/languageSelection.jsp"></jsp:include>
			<c:choose>
				<c:when test="${pageContext['request'].userPrincipal != null}">
						<li class="dropdown"><a href="#" class="dropdown-toggle" data-toggle="dropdown"> 
							<span class="glyphicon glyphicon-user"></span> <spring:message code="label.navBar.options.userConnected" />: <strong>${pageContext.request.remoteUser}</strong>
								<span class="glyphicon glyphicon-chevron-down"></span>
						</a>
							<ul class="dropdown-menu">
								<li>
									<a href="${pageContext.request.contextPath}/"> 
										<span class="glyphicon glyphicon-list-alt"></span>  <strong> <spring:message code="label.navBar.options.searchTexts" /></strong>
									</a>
								</li>
								<li class="divider"></li>
								<sec:authorize access="hasAnyRole('ROLE_ADMIN','ROLE_DBA')">
									<li>
										<a href="${pageContext.request.contextPath}/checkTexts"> 
											<span class="glyphicon glyphicon-edit"></span>  <strong> <spring:message code="label.navBar.options.checkTexts" /></strong>
										</a>
									</li>
									<li class="divider"></li>
									<li>
										<a href="${pageContext.request.contextPath}/deferredHarvest"> 
											<span class="glyphicon glyphicon-cloud-download"></span>  <strong> <spring:message code="label.navBar.options.harvest" /></strong>
										</a>
									</li>
									<li class="divider"></li>
								</sec:authorize>
								<sec:authorize access="hasRole('ROLE_ADMIN')">
									<li>
										<a href="${pageContext.request.contextPath}/adminUsers"> 
											<span class="glyphicon glyphicon-wrench"></span>  <strong> <spring:message code="label.navBar.options.adminUsers" /></strong>
										</a>
									</li>
									<li class="divider"></li>
								</sec:authorize>
								<li>
									<a href="${pageContext.request.contextPath}/updatePassword"> 
										<span class="glyphicon glyphicon-lock"></span>  <strong> <spring:message code="label.navBar.options.changePass" /></strong>
									</a>
								</li>
								<li class="divider"></li>
								<li>
								<form:form action="${pageContext.request.contextPath}/logout" id="logoutForm">
								  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
								</form:form>

									<a href="#" onclick="document.forms.namedItem('logoutForm').submit()"> 
										<span class="glyphicon glyphicon-off"></span>  <strong> <spring:message code="label.navBar.options.closeSession" /></strong>
									</a>
								</li>
							</ul>
						</li>
				</c:when>
				<c:otherwise>
					<c:url var="loginUrl" value="/login" />
					<c:set var="enterUserName">
					    <spring:message code="label.login.enter.userName" />
					</c:set>  
					<c:set var="enterPassword">
					    <spring:message code="label.login.enter.password" />
					</c:set>  
			        <li class="dropdown">
			          <a href="#" class="dropdown-toggle" data-toggle="dropdown"><b><spring:message code="label.login.enter.newSession" /></b> <span class="caret"></span></a>
						<ul id="login-dp" class="dropdown-menu">
							<li>
								 <div class="row">
										<div class="col-md-12">
											 <form class="form" id="signin" role="form" action="${loginUrl}" method="post">
													<div class="input-group">
														 <span class="input-group-addon"><i class="glyphicon glyphicon-user small"></i></span>
														 <input type="text" class="form-control" name="ssoId" id="username" placeholder="${enterUserName}" required>
													</div>
													<div class="input-group">
														 <span class="input-group-addon"><i class="glyphicon glyphicon-lock small"></i></span>
														 <input type="password" class="form-control" name="password" id="password" placeholder="${enterPassword}" required>
			                                        </div>
													<div class="form-group">
														 <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
														 <button type="submit" class="btn btn-primary btn-block"><spring:message code="label.login.enter.newSession" /></button>
													</div>
											 </form>
										</div>
								 </div>
							</li>
						</ul>
			        </li>
				</c:otherwise>
			</c:choose>
		</ul>
    </div>
  </div>
</nav>
	<c:choose>
		<c:when test="${not empty section}">
			<ol class="breadcrumb">
			  <li><a href="${pageContext.request.contextPath}"><spring:message code="label.navBar.breadcrum.home" /></a></li>
			  <li class="active"><spring:message code="label.navBar.section.${section}" /></li>
			</ol>
		</c:when>
		<c:otherwise>
			<ol class="breadcrumb">
			  <li><spring:message code="label.navBar.breadcrum.home" /></li>
			</ol>
		</c:otherwise>
	</c:choose>


