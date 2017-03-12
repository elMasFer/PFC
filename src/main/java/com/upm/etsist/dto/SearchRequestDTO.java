package com.upm.etsist.dto;

import java.util.List;

public class SearchRequestDTO {

	private String request;
	private String language="esp";
	private List<SearchResponseDTO> list;
	
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public List<SearchResponseDTO> getList() {
		return list;
	}
	public void setList(List<SearchResponseDTO> list) {
		this.list = list;
	}
	@Override
	public String toString() {
		return "SearchRequestDTO [request=" + request + ", language=" + language + "]";
	}
	public SearchRequestDTO(String request, String language) {
		super();
		this.request = request;
		this.language = language;
	}
	public SearchRequestDTO() {
		super();
	}

}
