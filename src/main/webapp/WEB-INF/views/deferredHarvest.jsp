<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/WEB-INF/views/head.jsp"></jsp:include>
	
<script type="text/javascript">

	$(function () {
	  var token = $("meta[name='_csrf']").attr("content");
	  var header = $("meta[name='_csrf_header']").attr("content");
	  $(document).ajaxSend(function(e, xhr, options) {
	    xhr.setRequestHeader(header, token);
	  });
	});
	
var allow = true;
var startUrl;
var pollUrl;


function Poll() {

	this.start = function start(start, poll) {

		startUrl = start;
		pollUrl = poll;
		
		if (request) {
			request.abort(); // abort any pending request
		}

		// fire off the request to MatchUpdateController
		var request = $.ajax({
			url : startUrl,
			type : "get",
		});

		// This is jQuery 1.8+
		// callback handler that will be called on success
		request.done(function(reply) {

			console.log("Game on..." + reply);
			setInterval(function() {
				if (allow === true) {
					allow = false;
					getUpdate();
				}
			}, 500);
		});
		
		// callback handler that will be called on failure
		request.fail(function(jqXHR, textStatus, errorThrown) {
			// log the error to the console
			console.log("Start - the following error occured: " + textStatus, errorThrown);
		});
		
		
	};
	
	function getUpdate() {
		
		console.log("Okay let's go...");
																	
		if (request) {
			request.abort();	// abort any pending request
		}
		
		// fire off the request to MatchUpdateController
		var request = $.ajax({
			url : pollUrl,
			type : "get",
		});

		// This is jQuery 1.8+
		// callback handler that will be called on success
		request.done(function(message) {
			console.log("Received a message");
			
			var update = getUpdate(message);
			$(update).insertAfter('#first_row');
			if(message.progress == "100" ){
				setTimeout(function(){
					window.location.replace("${pageContext.request.contextPath}/harvested/"+message.codResultado);
				}, 10000);
			}
		});
		
		function getUpdate(message) {
			
				$("#percentageText").html(message.progress+'% <spring:message code="label.harvest.completed" />');
				$("#percentageBar").width( message.progress+"%" );
				var update = "<div class='span-13 append-2 last' id='update-div'>"
					+ "<p id='message' class='update'>"
					+ message.messageText
					+ "</p>"
					+ "</div>";
				return update;

		};
		
		

		// callback handler that will be called on failure
		request.fail(function(jqXHR, textStatus, errorThrown) {
			// log the error to the console
			console.log("Polling - the following error occured: " + textStatus, errorThrown);
		});

		// callback handler that will be called regardless if the request failed or succeeded
		request.always(function() {
			allow = true;
		});
	};	
};
	
	$(document).ready(function() {
	
		var startUrl = "${pageContext.request.contextPath}/harvest/begin";
		var pollUrl = "${pageContext.request.contextPath}/harvest/deferred";
		
		var poll = new Poll();
		poll.start(startUrl,pollUrl);


		
	
		
		
	});
	
</script>



<body>
	<c:set var="section" value="harvest" scope="request" />
	<jsp:include page="/WEB-INF/views/navBar.jsp"></jsp:include>
	<div class="container">
	<jsp:include page="/WEB-INF/views/showAlerts.jsp"></jsp:include>
	 <div class="progress">
  <div name="percentageBar" id="percentageBar" class="progress-bar progress-bar-striped active" role="progressbar"
  aria-valuenow="0" aria-valuemin="0" aria-valuemax="100" style="width:0%">
    <p name="percentageText" id="percentageText"></p>
  </div>
</div>
		<div id='first_row' class="span-22 prepend-2">
			<div>
			<h2 class="update"><spring:message code="label.harvest.statusUpdates" /></h2>
			</div>
			<hr />
		</div>
		<%-- Place updates in here --%>
		<div class="span-13 append-2 last" id='update-div'>
			<c:set var="now" value="<%=new java.util.Date()%>" />
			<p id='message' class="update"><fmt:formatDate pattern="HH:mm:ss.SSS" 
            value="${now}" /> - <spring:message code="label.harvest.beginning" />...</p>
		</div>
	</div>
</body>


</html>

