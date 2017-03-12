package com.upm.etsist.dto;

import com.fasterxml.jackson.annotation.JsonView;
import com.upm.etsist.views.Views;

public class AjaxResponseUDTO {
	@JsonView(Views.Public.class)
	String code;
	
	@JsonView(Views.Public.class)
	String msg;

	@JsonView(Views.Public.class)
	AjaxRequestUDTO ajaxRequestUDTO;

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

	public AjaxRequestUDTO getAjaxRequestUDTO() {
		return ajaxRequestUDTO;
	}

	public void setAjaxRequestUDTO(AjaxRequestUDTO ajaxRequestUDTO) {
		this.ajaxRequestUDTO = ajaxRequestUDTO;
	}

	public AjaxResponseUDTO() {
		super();
	}

	public AjaxResponseUDTO(String code, String msg, AjaxRequestUDTO ajaxRequestUDTO) {
		super();
		this.code = code;
		this.msg = msg;
		this.ajaxRequestUDTO = ajaxRequestUDTO;
	}

	@Override
	public String toString() {
		return "AjaxResponseUDTO [code=" + code + ", msg=" + msg + ", ajaxRequestUDTO=" + ajaxRequestUDTO + "]";
	}


}
