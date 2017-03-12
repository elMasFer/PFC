package com.upm.etsist.ajax;

import com.upm.etsist.utils.StringUtils;

/**
 * A simple message that contains a match update, which is placed on the queue.
 * 
 * @author Roger
 * 
 *         Created 16:24:19 3 Feb 2013
 * 
 */
public class Message{

	private static final String dfLog="HH:mm:ss.SSS";
	private final String messageText;
	private final int progress;
	private final String codResultado;

	public Message() {
		messageText = "";
		progress = 0;
		codResultado = "";
		}

	public String getMessageText() {
		return messageText;
	}

	public int getProgress() {
		return progress;
	}

	public String getCodResultado() {
		return codResultado;
	}

	public Message(String messageText,int progress,String codResultado) {
		this.progress = progress;
		this.messageText = StringUtils.getFormattedDateAndString(dfLog, messageText);
		this.codResultado = codResultado;
	}

	public Message(String messageText,int progress) {
		this.progress = progress;
		this.messageText = StringUtils.getFormattedDateAndString(dfLog, messageText);
		this.codResultado = "";
	}

	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 * 
	 * @return a negative integer, zero, or a positive integer as this object is
	 *         less than, equal to, or greater than the specified object
	 */

	@Override
	public String toString() {
		return messageText;
	}

}
