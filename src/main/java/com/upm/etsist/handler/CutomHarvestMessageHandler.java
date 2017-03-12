package com.upm.etsist.handler;

import java.util.Date;
import java.util.Locale;
import java.util.Queue;

import org.dlese.dpc.oai.harvester.SimpleHarvestMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import com.upm.etsist.ajax.Message;
import com.upm.etsist.utils.Constants;

public class CutomHarvestMessageHandler extends SimpleHarvestMessageHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CutomHarvestMessageHandler.class);
	
	private static final String CONNECTING="A request for Identify has been made";
	private static final String NO_NEW_RECORDS="noRecordsMatch";
	private static final String NO_CONNECTIVITY="Error connecting to URL";
	Queue<Message> messagesQueue;

    private MessageSource messageSource;
	private Locale locale;
	private String status="";

	public CutomHarvestMessageHandler(Queue<Message> messagesQueue) {
		super();
		this.messagesQueue = messagesQueue;
	}

	public Queue<Message> getQueue() {
		return messagesQueue;
	}

	public void setMessagesQueue(Queue<Message> messagesQueue) {
		this.messagesQueue = messagesQueue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public void oaiErrorMessage(String oaiError, String errorMessage, String supportedGranularity, String deletedRecordSupport){
		logger.info("oaiErrorMessage("+oaiError+","+errorMessage+","+supportedGranularity+","+deletedRecordSupport+")");
		if(NO_NEW_RECORDS.equals(oaiError)){
			messagesQueue.add(new Message(messageSource.getMessage("label.harvest.status.noNewRecords",null , locale),50));
			this.status=Constants.STATUS_NO_NEW_RECORDS;
		}
		super.oaiErrorMessage(oaiError,errorMessage,supportedGranularity,deletedRecordSupport);
	}

	@Override
	public void completedHarvestMessage(int recordCount, int resumptionCount, String baseURL, String set,
			long startTime, long endTime, String zipFilePathName, String supportedGranularity,
			String deletedRecordSupport) {
		logger.info("completedHarvestMessage("+recordCount+","+resumptionCount+","+baseURL+","+set+","+startTime+","+endTime+","+zipFilePathName+","+supportedGranularity+","+deletedRecordSupport+")");
		super.completedHarvestMessage(recordCount, resumptionCount, baseURL, set, startTime, endTime, zipFilePathName,
				supportedGranularity, deletedRecordSupport);
	}

	@Override
	public void errorMessage(String errorMessage) {
		logger.info("errorMessage("+errorMessage+")");
		if(errorMessage.contains(NO_CONNECTIVITY)){
			messagesQueue.add(new Message(messageSource.getMessage("label.harvest.status.noConnection",null , locale),50));
			this.status=Constants.STATUS_NO_CONNECTIVITY;
		}
		super.errorMessage(errorMessage);
	}

	@Override
	public int getNumRecordsForStatusNotification() {
		logger.info("getNumRecordsForStatusNotification()");
		return super.getNumRecordsForStatusNotification();
	}

	@Override
	public void setHarvestAttributes(Date from, Date until) {
		logger.info("setHarvestAttributes("+from+","+until+")");
		super.setHarvestAttributes(from, until);
	}

	@Override
	public void statusMessage(int recordCount, int resumptionCount) {
		logger.info("statusMessage("+recordCount+","+resumptionCount+")");
		messagesQueue.add(new Message(messageSource.getMessage("label.harvest.status.records.get",new Object[]{recordCount} , locale),25));
		this.status=Constants.STATUS_OK;
		super.statusMessage(recordCount, resumptionCount);
	}

	@Override
	public void statusMessage(String msg) {
		logger.info("statusMessage("+msg+")");
		if(msg.contains(CONNECTING)){
			messagesQueue.add(new Message(messageSource.getMessage("label.harvest.status.connecting",null , locale),15));
		}
		super.statusMessage(msg);
	}

	public MessageSource getMessageSource() {
		return messageSource;
	}

	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}
	
}
