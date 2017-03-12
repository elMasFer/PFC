<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<html>
<jsp:include page="/WEB-INF/views/head.jsp"></jsp:include>
<body>
	<c:set var="section" value="searchTexts" scope="request" />
	<jsp:include page="/WEB-INF/views/navBar.jsp"></jsp:include>
	
	<c:set var="formAction" value="/" scope="request" />
	<jsp:include page="/WEB-INF/views/searchTextForm.jsp"></jsp:include>

	<c:choose>
		<c:when test="${not empty responseList}">
			<div class="container">
				<div class="row">
					<div class="table-responsive">
						<table class="table table-bordered">
							<caption>
								<h3>
									<spring:message code="label.texts.results.text" arguments="${request},${languageLabel}" />(${fn:length(responseList)}):
								</h3>
							</caption>

							<thead class="thead-inverse">
								<tr>
									<th><spring:message code="label.texts.results.header.spanish" /></th>
									<th><spring:message code="label.texts.results.header.english" /></th>
									<th><spring:message code="label.texts.results.header.check" /></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="response" items="${responseList}">
									<c:choose>
										<c:when test="${response.validated}">
											<c:set value="success" var="trCssClass"></c:set>
											<c:set value="glyphicon glyphicon-ok" var="btCssClass"></c:set>
											<c:set var="txtAlt">
											    <spring:message code="label.texts.results.body.checked" />
											</c:set>  
										</c:when>
										<c:otherwise>
											<c:set value="danger" var="trCssClass"></c:set>
											<c:set value="glyphicon glyphicon-remove" var="btCssClass"></c:set>
											<c:set var="txtAlt">
											    <spring:message code="label.texts.results.body.unchecked" />
											</c:set>  
										</c:otherwise>
									</c:choose>
									<tr class="${trCssClass}">
										<td>
											<p>${response.spanishText}</p>
										</td>
										<td>
											<p>${response.englishText}</p>
										</td>
										<td>
											<span class="tag label label-default">
												<a>
													<i class="${btCssClass}" alt="${txtAlt}" title="${txtAlt}"></i>
												</a> 
											</span>
										</td>
									</tr>
								</c:forEach>
							</tbody>

						</table>

					</div>
				</div>
			</div>
		</c:when>
	</c:choose>


</body>
</html>