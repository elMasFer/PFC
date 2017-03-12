<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="false"%>
<html>
<jsp:include page="/WEB-INF/views/head.jsp"></jsp:include>
 
    <body>
	<c:set var="section" value="changePassword" scope="request" />
	<jsp:include page="/WEB-INF/views/navBar.jsp"></jsp:include>
	<jsp:include page="/WEB-INF/views/showAlerts.jsp"></jsp:include>
	<c:set var="placeHolder_old">
	    <spring:message code="label.changePassword.placeHolder.oldPassword" />
	</c:set>  
	<c:set var="placeHolder_new">
	    <spring:message code="label.changePassword.placeHolder.newPassword" />
	</c:set>  
	<c:set var="placeHolder_newConfirm">
	    <spring:message code="label.changePassword.placeHolder.newPasswordConfirm" />
	</c:set>  
	<div id="mainWrapper">
            <div class="login-container">
                <div class="login-card">
                    <div class="login-form">
                        <c:url var="loginUrl" value="/updatePassword" />
                        <form:form action="${loginUrl}" method="post" modelAttribute="changePassword" class="form-horizontal">
							<div class="container">
								<div class="row">
									<div class="col-md-offset-5 col-md-3">
										<div class="form-login">
											<h4><spring:message code="label.changePassword.title" /></h4>
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-lock"></i></span> <input type="password" id="oldPassword"
													class="form-control" name="oldPassword" placeholder="${placeHolder_old}"
													required />
											</div>
											<br/>
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-lock"></i></span> <input type="password" id="newPassword"
													class="form-control" name="newPassword"
													placeholder="${placeHolder_new}" required />
											</div>
											<br/>
											<div class="input-group">
												<span class="input-group-addon"><i
													class="glyphicon glyphicon-lock"></i></span> <input type="password" id="newPasswordConfirm"
													class="form-control" name="newPasswordConfirm"
													placeholder="${placeHolder_newConfirm}" required />
											</div>
											<br/>
											<input type="hidden" name="${_csrf.parameterName}"
												value="${_csrf.token}" />
											<div class="wrapper">
												<button name="savePass" type="submit" class="btn btn-primary"><spring:message code="label.changePassword.action" /></button>
											</div>
										</div>
									<script>
									$(document).ready(function() {
										  $('form').on('submit', function(e){
											var pass = $("#newPassword").val();
											var valid = (pass == $("#newPasswordConfirm").val() && pass != "");
											if (!valid) {
												bootstrap_alert.danger('<spring:message code="label.changePassword.validation" />');
												e.preventDefault();
											}
										});
									});
									</script>

								</div>
								</div>
							</div>
						</form:form>
                    </div>
                </div>
            </div>
        </div>
 
    </body>
</html>