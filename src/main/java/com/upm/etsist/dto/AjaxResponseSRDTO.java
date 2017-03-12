package com.upm.etsist.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.upm.etsist.views.Views;

public class AjaxResponseSRDTO {
	@JsonView(Views.Public.class)
	String code;
	
	@JsonView(Views.Public.class)
	String msg;

	@JsonView(Views.Public.class)
	AjaxRequestSRDTO ajaxRequestSRDTO;

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}


	public AjaxRequestSRDTO getAjaxRequestSRDTO() {
		return ajaxRequestSRDTO;
	}

	public void setAjaxRequestSRDTO(AjaxRequestSRDTO ajaxRequestSRDTO) {
		this.ajaxRequestSRDTO = ajaxRequestSRDTO;
	}

	public AjaxResponseSRDTO() {
		super();
	}

	public AjaxResponseSRDTO(String code, String msg, AjaxRequestSRDTO ajaxRequestSRDTO) {
		super();
		this.code = code;
		this.msg = msg;
		this.ajaxRequestSRDTO = ajaxRequestSRDTO;
	}

	@Override
	public String toString() {
		return "AjaxResponseSRDTO [code=" + code + ", msg=" + msg + ", ajaxRequestSRDTO=" + ajaxRequestSRDTO + "]";
	}

}
