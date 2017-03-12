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
	<c:set var="section" value="checkTexts" scope="request" />
	<jsp:include page="/WEB-INF/views/navBar.jsp"></jsp:include>
	
	<c:set var="formAction" value="/checkTexts" scope="request" />
	<jsp:include page="/WEB-INF/views/searchTextForm.jsp"></jsp:include>

	<c:set var="txtChecked">
	    <spring:message code="label.texts.results.body.checked" />
	</c:set>  
	<c:set var="txtUnchecked">
	    <spring:message code="label.texts.results.body.unchecked" />
	</c:set>  

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

							<thead>
								<tr>
									<th><spring:message code="label.texts.results.header.spanish" /></th>
									<th><spring:message code="label.texts.results.header.english" /></th>
									<th><spring:message code="label.texts.results.header.check" /></th>
								</tr>
							</thead>

							<tbody>
								<c:forEach var="response" items="${responseList}" varStatus="loop">
									<c:choose>
										<c:when test="${response.validated}">
											<c:set value="success" var="trCssClass"></c:set>
											<c:set value="glyphicon glyphicon-ok" var="btCssClass"></c:set>
											<c:set value="${label.texts.results.body.checked}" var="txtAlt"></c:set>
										</c:when>
										<c:otherwise>
											<c:set value="danger" var="trCssClass"></c:set>
											<c:set value="glyphicon glyphicon-remove" var="btCssClass"></c:set>
											<c:set value="${label.texts.results.body.unchecked}" var="txtAlt"></c:set>
										</c:otherwise>
									</c:choose>
									<tr id="tr_${loop.index}">
										<td class="${trCssClass}">
											<div class="form-group">
												<input type="hidden" id="id_${loop.index}" name="id_${loop.index}" value="${response.id}" />
												<input type="hidden" id="url_${loop.index}" name="url_${loop.index}" value="${response.url}" />
												<textarea class="form-control" rows="10" name="spa_${loop.index}" id="spa_${loop.index}">${response.spanishText}</textarea>
											</div>
										</td>
										<td class="${trCssClass}">
											<div class="form-group">
												<textarea class="form-control" rows="10" name="eng_${loop.index}" id="eng_${loop.index}">${response.englishText}</textarea>
											</div>
										</td>
										<td class="${trCssClass}">

											<form id="myForm">
												<p>
													<label class="radio-inline" alt="${txtChecked}" title="${txtChecked}">
														<input type="radio" id="validated_${loop.index}_true" name="validated_${loop.index}" value="true" />
														<span class="tag label label-default">
															<a>
																<i class="glyphicon glyphicon-ok"></i>
															</a> 
														</span>
													</label>
												</p>

												<p>

													<label class="radio-inline" alt="${txtUnchecked}" title="${txtUnchecked}">
														<input type="radio" id="validated_${loop.index}_false" name="validated_${loop.index}" value="false" />
														<span class="tag label label-default ">
															<a>
																<i class="glyphicon glyphicon-remove"></i>
															</a> 
														</span>
													</label>
												</p>
											</form>
											<p>

												<div class="form-group">
										<button type="button" class="btn btn-xs btn-default" onclick="updateSearchResponse(${loop.index});">
											<span class="glyphicon glyphicon-floppy-disk"></span> <spring:message code="label.generic.actions.save" />
										</button>
										<br/>
										<button type="button" class="btn btn-xs btn-default" onclick="deleteSearchResponse(${loop.index});">
											<span class="glyphicon glyphicon-trash"></span> <spring:message code="label.generic.actions.delete" />
										</button>
									</div>
											</p>
											<script>
												$("#validated_${loop.index}_${response.validated}").prop("checked", true);
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
							function updateSearchResponse(row) {

								var searchResponseDTO = {};
								searchResponseDTO["id"] = $('#id_'+row).val();
								searchResponseDTO["spanishText"] = $('#spa_'+row).val();
								searchResponseDTO["englishText"] = $('#eng_'+row).val();
								searchResponseDTO["url"] = $('#url_'+row).val();
								searchResponseDTO["validated"] = $('#myForm input[name=validated_'+row+']:checked').val();
								var ajaxRequestDTO = {};
								ajaxRequestDTO["row"]=row;
								ajaxRequestDTO["searchResponseDTO"]=searchResponseDTO;
								if(searchResponseDTO["spanishText"].length > 10000){
									bootstrap_alert.danger('<spring:message code="label.checkTexts.alert.max_length_exceeded.spanish" />');
								}else if(searchResponseDTO["englishText"].length > 10000){
									bootstrap_alert.danger('<spring:message code="label.checkTexts.alert.max_length_exceeded.english" />');
								}else{
									$.ajax({
										type : "POST",
										contentType : "application/json",
										url : "${pageContext.request.contextPath}/update/searchResponse",
										data : JSON.stringify(ajaxRequestDTO),
										dataType : 'json',
										timeout : 100000,
										success : function(data) {
											console.log(
													"SUCCESS: ",
													data);
											tratarResultadoUpdateAJAX_OK(data);
										},
										error : function(e) {
											console.log("ERROR: ",
													e);
											tratarResultadoUpdateAJAX_KO(e);
										},
										done : function(e) {
											console.log("DONE");
										}
									});
								}
							}
					        function tratarResultadoUpdateAJAX_OK(data){
					        	var newClass=" danger ";
					        	if(data.ajaxRequestSRDTO.searchResponseDTO.validated == true){
					        		newClass=" success ";
					        	}
					        	$("#tr_"+data.ajaxRequestSRDTO.row).children().attr('class', newClass);
					        	if(data.code == "200"){
					        		bootstrap_alert.success(data.msg);
					        	}else{
					        		bootstrap_alert.danger(data.msg);
					        	}
					        	
					        }
					        function tratarResultadoUpdateAJAX_KO(data){
					        	bootstrap_alert.danger(JSON.stringify(data));
					        }

							function deleteSearchResponse(row) {

								var searchResponseDTO = {};
								searchResponseDTO["id"] = $('#id_'+row).val();
								searchResponseDTO["spanishText"] = $('#spa_'+row).val();
								searchResponseDTO["englishText"] = $('#eng_'+row).val();
								searchResponseDTO["url"] = $('#url_'+row).val();
								searchResponseDTO["validated"] = $('#myForm input[name=validated_'+row+']:checked').val();
								var ajaxRequestDTO = {};
								ajaxRequestDTO["row"]=row;
								ajaxRequestDTO["searchResponseDTO"]=searchResponseDTO;

								$.ajax({
									type : "POST",
									contentType : "application/json",
									url : "${pageContext.request.contextPath}/delete/searchResponse",
									data : JSON.stringify(ajaxRequestDTO),
									dataType : 'json',
									timeout : 100000,
									success : function(data) {
										console.log(
												"SUCCESS: ",
												data);
										tratarResultadoDeleteAJAX_OK(data);
									},
									error : function(e) {
										console.log("ERROR: ",
												e);
										tratarResultadoDeleteAJAX_KO(e);
									},
									done : function(e) {
										console.log("DONE");
									}
								});
							}
							
					        function tratarResultadoDeleteAJAX_OK(data){
					        	$("#tr_"+data.ajaxRequestSRDTO.row).remove();
					        	if(data.code == "200"){
					        		bootstrap_alert.success(data.msg);
					        	}else{
					        		bootstrap_alert.danger(data.msg);
					        	}
					        	
					        }
					        function tratarResultadoDeleteAJAX_KO(data){
					        	bootstrap_alert.danger(JSON.stringify(data));
					        }

							
						</script>
					</div>
				</div>
			</div>
		</c:when>
	</c:choose>


</body>
</html>