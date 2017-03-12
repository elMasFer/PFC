<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<c:set var="placeholderText">
    <spring:message code="label.searchTextForm.request.placeholder.text" />
</c:set>  

<div class="container">
	<div class="row">
		<br /><br />
		<form:form action="${pageContext.request.contextPath}${formAction}" id="searchTextForm" name="searchTextForm" role="form" method="GET" modelAttribute="searchRequest" class="form-horizontal">
		    <div class="form-group">
		        <div class="col-xs-9">
		            <input type="text" class="form-control" id="request" name="request" placeholder="${placeholderText}" value="${request}" /> <br />
		            <label class="radio-inline"><spring:message code="label.searchTextForm.request.language.selectionText" /> </label>  
		            <label class="radio-inline">
		                <form:radiobutton path="language" id="language" name="language" value="esp" /><spring:message code="label.searchTextForm.request.language.spanish" />
		            </label>
		            <label class="radio-inline">
		                <form:radiobutton path="language" id="language" name="language" value="eng" /><spring:message code="label.searchTextForm.request.language.english" />
		            </label>
		        </div>
		        <div class="col-xs-2">
		            <button type="submit" class="btn btn-default"> <spring:message code="label.searchTextForm.request.action" /></button>
		        </div>
		    </div>
		</form:form>
		<script>
			$(document).ready(function() {
				  $('#searchTextForm').on('submit', function(e){
					var request = $("#request").val();
					if (request == "") {
						bootstrap_alert.danger('<spring:message code="label.texts.results.KO.noRequest" />');
						e.preventDefault();
					}
				});
				  $('#request').tooltip('hide');
			});
		</script>
	</div>
	<jsp:include page="/WEB-INF/views/showAlerts.jsp"></jsp:include>
</div>
