package com.upm.etsist.ajax;

import java.util.Locale;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.upm.etsist.dao.SearchResponseDAO;
import com.upm.etsist.dto.AjaxRequestSRDTO;
import com.upm.etsist.dto.AjaxRequestUDTO;
import com.upm.etsist.dto.AjaxResponseSRDTO;
import com.upm.etsist.dto.AjaxResponseUDTO;
import com.upm.etsist.security.dao.UserDAO;

@Controller
public class AjaxController {
	@Autowired  
	private SearchResponseDAO searchResponseDAO;  

	@Autowired  
	private UserDAO userDAO;  

	@Autowired
    private MessageSource messageSource;

	private static final Logger logger = LoggerFactory.getLogger(AjaxController.class);

	@ResponseBody
	@RequestMapping(value = "/update/searchResponse", method=RequestMethod.POST)
	public AjaxResponseSRDTO updateSearchResponse(Locale locale, @RequestBody AjaxRequestSRDTO ajaxRequestDTO) {

		logger.info("updateSearchResponse("+ajaxRequestDTO+")");
		searchResponseDAO.update(ajaxRequestDTO.getSearchResponseDTO());
		return new AjaxResponseSRDTO("200",messageSource.getMessage("label.generic.saved.ok",null , locale),ajaxRequestDTO);

	}

	@ResponseBody
	@RequestMapping(value = "/delete/searchResponse", method=RequestMethod.POST)
	public AjaxResponseSRDTO deleteSearchResponse(Locale locale, @RequestBody AjaxRequestSRDTO ajaxRequestDTO) {

		logger.info("deleteSearchResponse("+ajaxRequestDTO+")");
		searchResponseDAO.delete(ajaxRequestDTO.getSearchResponseDTO());
		return new AjaxResponseSRDTO("200",messageSource.getMessage("label.generic.saved.ok",null , locale),ajaxRequestDTO);

	}

	@ResponseBody
	@RequestMapping(value = "/update/user", method=RequestMethod.POST)
	public AjaxResponseUDTO updateUser(Locale locale, @RequestBody AjaxRequestUDTO ajaxRequestUDTO) {

		logger.info("updateUser("+ajaxRequestUDTO+")");
		try {
			userDAO.update( ajaxRequestUDTO.getUser());
		} catch (ConstraintViolationException e) {
			return new AjaxResponseUDTO("500",messageSource.getMessage("label.generic.error.codeAndDesc",new Object[]{e.getErrorCode(),e.getMessage()} , locale),ajaxRequestUDTO);
		} catch (Exception e) {
			return new AjaxResponseUDTO("500",messageSource.getMessage("label.generic.error.desc",new Object[]{e.getMessage()} , locale),ajaxRequestUDTO);
		}
		return new AjaxResponseUDTO("200",messageSource.getMessage("label.generic.saved.ok",null , locale),ajaxRequestUDTO);

	}

	@ResponseBody
	@RequestMapping(value = "/delete/user", method=RequestMethod.POST)
	public AjaxResponseUDTO deleteUser(Locale locale, @RequestBody AjaxRequestUDTO ajaxRequestUDTO) {

		logger.info("deleteUser("+ajaxRequestUDTO+")");
		try {
			userDAO.delete(ajaxRequestUDTO.getUser());
		} catch (ConstraintViolationException e) {
			return new AjaxResponseUDTO("500",messageSource.getMessage("label.generic.error.codeAndDesc",new Object[]{e.getErrorCode(),e.getMessage()} , locale),ajaxRequestUDTO);
		} catch (Exception e) {
			return new AjaxResponseUDTO("500",messageSource.getMessage("label.generic.error.desc",new Object[]{e.getMessage()} , locale),ajaxRequestUDTO);
		}
		return new AjaxResponseUDTO("200",messageSource.getMessage("label.generic.saved.ok",null , locale),ajaxRequestUDTO);

	}

}