<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
    <jsp:include page="/WEB-INF/views/head.jsp"></jsp:include>
	<c:set var="wrongCredentials">
	   <spring:message code="label.login.enter.wrongCredentials" />
	</c:set>  
	<c:set var="enterUsername">
	   <spring:message code="label.login.enter.userName" />
	</c:set>  
	<c:set var="enterPassword">
	   <spring:message code="label.login.enter.password" />
	</c:set>  
    <body>
	<nav class="navbar navbar-default">
		<div class="container">
			<div class="collapse navbar-collapse"
				id="bs-example-navbar-collapse-1"></div>
		</div>
	</nav>
	<jsp:include page="/WEB-INF/views/showAlerts.jsp"></jsp:include>
	<div id="mainWrapper">
            <div class="login-container">
                <div class="login-card">
                    <div class="login-form">
                        <c:url var="loginUrl" value="/login" />
                        <form action="${loginUrl}" method="post" class="form-horizontal">
							<div class="container">
								<div class="row">
									<div class="col-md-offset-5 col-md-3">
			                            <c:if test="${param.error != null}">
				                            <script>
				                        		bootstrap_alert.danger("${wrongCredentials}");
				                            </script>
			                            </c:if>
										<div class="form-login">
											<h4><spring:message code="label.login.enter.welcomeBack" /></h4>
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-user"></i></span> <input type="text" id="username"
													class="form-control" name="ssoId" placeholder="${enterUsername}" required />
											</div>
											<br/>
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-lock"></i></span> <input type="password" id="password"
													class="form-control" name="password"
													placeholder="${enterPassword}" required />
											</div>
											<br/>
											<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
											<div class="wrapper">
												<button type="submit" class="btn btn-primary"><spring:message code="label.login.enter.newSession" /></button>
											</div>
										</div>
	
									</div>
								</div>
							</div>
						</form>
                    </div>
                </div>
            </div>
        </div>
 
    </body>
</html>