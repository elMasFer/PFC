<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/WEB-INF/views/head.jsp"></jsp:include>
<body>
	<c:set var="section" value="adminUsers" scope="request" />
	<jsp:include page="/WEB-INF/views/navBar.jsp"></jsp:include>
	
	<form:form action="${pageContext.request.contextPath}/addUser" role="form" name="submitForm" method="POST" modelAttribute="searchRequest">
		<div class="container">
			<div class="row">
				<div class="table-responsive">
					<table class="table">
						<caption>
							<h3>
								<spring:message code="label.adminUsers.newUser" />
							</h3>
						</caption>
	
						<thead>
							<tr>
								<th><spring:message code="label.adminUsers.userName" /></th>
								<th><spring:message code="label.adminUsers.password" /></th>
								<th><spring:message code="label.adminUsers.firstName" /></th>
								<th><spring:message code="label.adminUsers.lastName" /></th>
								<th><spring:message code="label.adminUsers.eMail" /></th>
								<th><spring:message code="label.adminUsers.profile" /></th>
								<th><spring:message code="label.adminUsers.save" /></th>
							</tr>
						</thead>
	
							<tr>
								<td>
									<div class="form-group">
										<input type="text" class="form-control" id="ssoId" name="ssoId" value="${newUser.ssoId}" />
									</div>
								</td>
								<td>
									<div class="form-group">
										<input type="password" class="form-control" id="password" name="password" value="${newUser.password}" />
									</div>
								</td>
								<td>
									<div class="form-group">
										<input type="text" class="form-control" id="firstName" name="firstName" value="${newUser.firstName}" />
									</div>
								</td>
								<td>
									<div class="form-group">
										<input type="text" class="form-control" id="lastName" name="lastName" value="${newUser.lastName}" />
									</div>
								</td>
								<td>
									<div class="form-group">
										<input type="text" class="form-control" id="email" name="email" value="${newUser.email}" />
									</div>
								</td>
								<td>
									<div class="form-group">
										<select id="profile" name="profile" class="selectpicker form-control">
											<option value="USER" ><spring:message code="label.adminUsers.profile.user" /></option>
											<option value="DBA"><spring:message code="label.adminUsers.profile.checker" /></option>
											<option value="ADMIN"><spring:message code="label.adminUsers.profile.admin" /></option>
										</select>
									</div>
								</td>
								<td>
									<div class="form-group">
										<button type="submit" class="btn btn-xs btn-default">
											<span class="glyphicon glyphicon-floppy-disk"></span> <spring:message code="label.generic.actions.save" />
										</button>
									</div>
									<script>
										if("${newUser.profile}" != ""){
											$("#profile").val("${newUser.profile}");
										}
									</script>
								</td>
							</tr>
						</tbody>
	
					</table>
				</div>
				<jsp:include page="/WEB-INF/views/showAlerts.jsp"></jsp:include>
			</div>
		</div>
		<script>
		
			function isEmail(email) {
				var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
				return regex.test(email);
			}
			
			function isValidUser(ssoId, password, firstName, lastName, email){
				if(ssoId.length < 5 || ssoId.length > 30){
					bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.userName" />');
					return false;
				}else{
					if(password.length < 5 || password.length > 30){
						bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.password" />');
						return false;
					}else{
						if(firstName.length < 3 || firstName.length > 30){
							bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.firstName" />');
							return false;
						}else{
							if(lastName.length < 3 || lastName.length > 30){
								bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.lastName" />');
								return false;
							}else{
								if(!isEmail(email)){
									bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.eMail" />');
									return false;
								}
							}
						}
					}
				}
				return true;			
			}
        
			$(document).ready(function() {
				$('form').on('submit', function(e){
					if(hmUsers[$("#ssoId").val()]){
						bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.used.userName.part1" />'+$("#ssoId").val()+'<spring:message code="label.adminUsers.validation.used.userName.part2" />');
						e.preventDefault();
					}else if(hmEmails[$("#email").val()]){
						bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.used.eMail.part1" />'+$("#email").val()+'<spring:message code="label.adminUsers.validation.used.eMail.part2" />');
						e.preventDefault();
					}else{
						var valid=isValidUser($("#ssoId").val(),$("#password").val(),$("#firstName").val(),$("#lastName").val(),$("#email").val());
						if(!valid ){
							e.preventDefault();
						}
					}
				});
			});
			
			var hmUsers={};
			var hmEmails={};
		</script>
	</form:form>
	<div class="container">
		<div class="row">
			<div class="table-responsive">
				<table class="table table-striped">
					<caption>
						<h3>
							<spring:message code="label.adminUsers.userList.title" />
						</h3>
					</caption>

					<thead>
						<tr>
							<th><spring:message code="label.adminUsers.userName" /></th>
							<th><spring:message code="label.adminUsers.password" /></th>
							<th><spring:message code="label.adminUsers.firstName" /></th>
							<th><spring:message code="label.adminUsers.lastName" /></th>
							<th><spring:message code="label.adminUsers.eMail" /></th>
							<th><spring:message code="label.adminUsers.profile" /></th>
							<th><spring:message code="label.adminUsers.actions" /></th>
						</tr>
					</thead>

					<tbody>
						<c:forEach var="user" items="${userList}" varStatus="loop">
							<tr id="tr_${loop.index}">
								<td>
									<div class="form-group">
										<input type="hidden" id="id_${loop.index}" name="id_${loop.index}" value="${user.id}" />
										<input type="hidden" id="ssoId_${loop.index}" name="ssoId_${loop.index}" value="${user.ssoId}" />
										<div class="col-sm-15">
									      <p class="form-control-static">${user.ssoId}</p>
									    </div>
									</div>
								</td>
								<td>
									<div class="form-group">
										<div class="col-sm-15">
											<input class="form-control" type="password" id="password_${loop.index}" name="password_${loop.index}" value="${user.password}" required />
										</div>
									</div>
								</td>
								<td>
									<div class="form-group">
										<div class="col-sm-15">
											<input class="form-control" type="text" id="firstName_${loop.index}" name="firstName_${loop.index}" value="${user.firstName}" required />
										</div>
									</div>
								</td>
								<td>
									<div class="form-group">
										<div class="col-sm-15">
											<input class="form-control" type="text" id="lastName_${loop.index}" name="lastName_${loop.index}" value="${user.lastName}" required />
										</div>
									</div>
								</td>
								<td>
									<div class="form-group">
										<div class="col-sm-15">
											<input class="form-control" type="text" id="email_${loop.index}" name="email_${loop.index}" value="${user.email}" required />
										</div>
									</div>
								</td>
								<td>
									<div class="form-group">
										<div class="col-sm-15">
											<select id="profile_${loop.index}" name="profile_${loop.index}" class="form-control selectpicker">
												<option value="USER" ><spring:message code="label.adminUsers.profile.user" /></option>
												<option value="DBA"><spring:message code="label.adminUsers.profile.checker" /></option>
												<option value="ADMIN"><spring:message code="label.adminUsers.profile.admin" /></option>
											</select>
										</div>
									</div>
								</td>
								<td>
									<div class="form-group">
										<button type="button" class="btn btn-xs btn-default" onclick="updateUser(${loop.index});">
											<span class="glyphicon glyphicon-floppy-disk"></span> <spring:message code="label.generic.actions.save" />
										</button>
										<c:if test="${pageContext.request.remoteUser != user.ssoId}">
											<br/>
											<button type="button" class="btn btn-xs btn-default" onclick="deleteUser(${loop.index});">
												<span class="glyphicon glyphicon-trash"></span> <spring:message code="label.generic.actions.delete" />
											</button>
										</c:if>
									</div>
									<script>
									
										hmUsers["${user.ssoId}"]="S";
										hmEmails["${user.email}"]="S";
									
										$("#profile_${loop.index}").val("${user.profile}");
										<c:if test="${pageContext.request.remoteUser == user.ssoId}">
											$("#profile_${loop.index}").prop('disabled', 'disabled');
											$("#profile_${loop.index}").attr('class', "form-control selectpicker active");
										</c:if>
									</script>
								</td>
							</tr>
						</c:forEach>
					</tbody>

				</table>
				<script>
						
		        
						$(function () {
						  var token = $("meta[name='_csrf']").attr("content");
						  var header = $("meta[name='_csrf_header']").attr("content");
						  $(document).ajaxSend(function(e, xhr, options) {
						    xhr.setRequestHeader(header, token);
						  });
						});
						function updateUser(row) {

							var user = {};
							user["id"] = $('#id_'+row).val();
							user["ssoId"] = $('#ssoId_'+row).val();
							user["password"] = $('#password_'+row).val();
							user["firstName"] = $('#firstName_'+row).val();
							user["lastName"] = $('#lastName_'+row).val();
							user["email"] = $('#email_'+row).val();
							user["profile"] = $('#profile_'+row).val();
							valid = isValidUser(user["ssoId"], user["password"], user["firstName"], user["lastName"], user["email"]);
							
							var ajaxRequestDTO = {};
							ajaxRequestDTO["row"]=row;
							ajaxRequestDTO["user"]=user;

							if(valid){
								$.ajax({
									type : "POST",
									contentType : "application/json",
									url : "${pageContext.request.contextPath}/update/user",
									data : JSON.stringify(ajaxRequestDTO),
									dataType : 'json',
									timeout : 100000,
									success : function(data) {
										console.log(
												"SUCCESS: ",
												data);
										responseToUpdateAJAX_OK(data);
									},
									error : function(e) {
										console.log("ERROR: ",
												e);
										responseToUpdateAJAX_KO(e);
									},
									done : function(e) {
										console.log("DONE");
									}
								});
							
							}else{
								bootstrap_alert.danger('<spring:message code="label.adminUsers.validation.allInputsRequired" />');
							}

						}
						
				        function responseToUpdateAJAX_OK(data){
				        	if(data.code == "200"){
				        		bootstrap_alert.success(data.msg);
				        	}else{
				        		bootstrap_alert.danger(data.msg);
				        	}
				        	
				        }
				        function responseToUpdateAJAX_KO(data){
				        	bootstrap_alert.danger(JSON.stringify(data));
				        }

						function deleteUser(row) {

							var user = {};
							user["id"] = $('#id_'+row).val();
							user["ssoId"] = $('#ssoId_'+row).val();
							user["password"] = $('#password_'+row).val();
							user["firstName"] = $('#firstName_'+row).val();
							user["lastName"] = $('#lastName_'+row).val();
							user["email"] = $('#email_'+row).val();
							user["profile"] = $('#profile_'+row).val();
							
							var ajaxRequestDTO = {};
							ajaxRequestDTO["row"]=row;
							ajaxRequestDTO["user"]=user;

							$.ajax({
								type : "POST",
								contentType : "application/json",
								url : "${pageContext.request.contextPath}/delete/user",
								data : JSON.stringify(ajaxRequestDTO),
								dataType : 'json',
								timeout : 100000,
								success : function(data) {
									console.log(
											"SUCCESS: ",
											data);
									responseToDeleteAJAX_OK(data);
								},
								error : function(e) {
									console.log("ERROR: ",
											e);
									responseToDeleteAJAX_KO(e);
								},
								done : function(e) {
									console.log("DONE");
								}
							});
						}
						
				        function responseToDeleteAJAX_OK(data){
				        	if(data.code == "200"){
				        		bootstrap_alert.success(data.msg);
				        		var row = data.ajaxRequestUDTO.row
				        		delete hmUsers[$('#ssoId_'+row).val()];
				        		delete hmEmails[$('#email_'+row).val()];
				        		$("#tr_"+data.ajaxRequestUDTO.row).remove();
				        	}else{
				        		bootstrap_alert.danger(data.msg);
				        	}
				        	
				        }
				        function responseToDeleteAJAX_KO(data){
				        	bootstrap_alert.danger(JSON.stringify(data));
				        }

						
					</script>
			</div>
		</div>
	</div>


</body>
</html>