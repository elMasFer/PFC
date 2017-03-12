package com.upm.etsist.dto;

import com.upm.etsist.security.model.User;

public class AjaxRequestSRDTO {

	String row;
	
	SearchResponseDTO searchResponseDTO;


	public String getRow() {
		return row;
	}

	public void setRow(String row) {
		this.row = row;
	}


	public SearchResponseDTO getSearchResponseDTO() {
		return searchResponseDTO;
	}

	public void setSearchResponseDTO(SearchResponseDTO searchResponseDTO) {
		this.searchResponseDTO = searchResponseDTO;
	}

	public AjaxRequestSRDTO() {
		super();
	}

	public AjaxRequestSRDTO(String row, SearchResponseDTO searchResponseDTO) {
		super();
		this.row = row;
		this.searchResponseDTO = searchResponseDTO;
	}

	@Override
	public String toString() {
		return "AjaxRequestSRDTO [row=" + row + ", searchResponseDTO=" + searchResponseDTO + "]";
	}

	
}
