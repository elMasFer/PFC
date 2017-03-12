package com.upm.etsist.validator;

import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;

import com.upm.etsist.dto.SearchRequestDTO;
import com.upm.etsist.dto.SearchResponseDTO;
import com.upm.etsist.dto.SearchValidatorDTO;
import com.upm.etsist.utils.Constants;


public class SearchValidator {
	
    private MessageSource messageSource;

	private Locale locale;


	public SearchValidatorDTO searchResponseValidate(List<SearchResponseDTO> srList,String request){
		SearchValidatorDTO retorno=new SearchValidatorDTO();
		if(srList.size() == 0){
			retorno.setCodError(Constants.COD_ERROR_KO);
			retorno.setDesError(messageSource.getMessage("label.texts.results.KO.noResults",new Object[]{request} , locale));
		}
		return retorno;
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
