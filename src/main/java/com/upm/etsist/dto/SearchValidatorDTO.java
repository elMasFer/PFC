package com.upm.etsist.dto;

public class SearchValidatorDTO {
	private String codError="0";
	private String desError="";
	public String getCodError() {
		return codError;
	}
	public void setCodError(String codError) {
		this.codError = codError;
	}
	public String getDesError() {
		return desError;
	}
	public void setDesError(String desError) {
		this.desError = desError;
	}
	@Override
	public String toString() {
		return "SearchValidatorBean [codError=" + codError + ", desError=" + desError + "]";
	}
	public SearchValidatorDTO(String codError, String desError) {
		super();
		this.codError = codError;
		this.desError = desError;
	}
	public SearchValidatorDTO() {
		super();
	}
	
}
