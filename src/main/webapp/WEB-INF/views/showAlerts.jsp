<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<div id="alert_placeholder"></div>
	<script>
		bootstrap_alert = function() {};
		bootstrap_alert.danger = function(message) {
            $('#alert_placeholder').html('<div class="alert alert-danger"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
        };
		bootstrap_alert.success = function(message) {
            $('#alert_placeholder').html('<div class="alert alert-success"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
        };
		bootstrap_alert.info = function(message) {
            $('#alert_placeholder').html('<div class="alert alert-info"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
        };
		bootstrap_alert.warning = function(message) {
            $('#alert_placeholder').html('<div class="alert alert-warning"><a class="close" data-dismiss="alert">×</a><span>'+message+'</span></div>')
        };
    	<c:if test="${infoMSG != null}">
			bootstrap_alert.info("${infoMSG}");
		</c:if>
    	<c:if test="${successMSG != null}">
			bootstrap_alert.success("${successMSG}");
		</c:if>
		<c:if test="${dangerMSG != null}">
			bootstrap_alert.danger("${dangerMSG}");
		</c:if>
		<c:if test="${warningMSG != null}">
			bootstrap_alert.warning("${warningMSG}");
		</c:if>
	</script>
