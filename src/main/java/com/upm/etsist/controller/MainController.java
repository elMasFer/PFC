package com.upm.etsist.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.DefaultPropertiesPersister;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.upm.etsist.dao.SearchResponseDAO;
import com.upm.etsist.dto.SearchRequestDTO;
import com.upm.etsist.dto.SearchResponseDTO;
import com.upm.etsist.dto.SearchValidatorDTO;
import com.upm.etsist.executor.SearchExecutor;
import com.upm.etsist.validator.SearchValidator;
import com.upm.etsist.security.dao.UserDAO;
import com.upm.etsist.security.model.User;
import com.upm.etsist.utils.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;

import org.hibernate.exception.ConstraintViolationException;
import org.apache.log4j.Logger;

@Controller
public class MainController {

	@Autowired  
	private SearchResponseDAO searchResponseDao;  

	@Autowired  
	private UserDAO userDao;

	@Autowired
    private MessageSource messageSource;

	@Autowired  
	private SearchExecutor searchExecutor;  

	@Autowired  
	private UserDAO userDAO;  

	@Autowired  
	private SearchValidator searchValidator;  

	private static final Logger logger = Logger.getLogger(MainController.class);

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String searchTexts(Locale locale, Model model, @ModelAttribute("searchRequest") SearchRequestDTO searchRequest) {
		logger.info("MainController.submit("+searchRequest+")");
		if(searchRequest!=null && searchRequest.getRequest() != null && !searchRequest.getRequest().equals("")){
			String lang=searchRequest.getLanguage();
			model.addAttribute("language", lang);
			model.addAttribute("request", searchRequest.getRequest());
			if(lang.equals("eng")){
				model.addAttribute("languageLabel", messageSource.getMessage("label.maincontroller.searchText.english",null , locale));
			}else{
				model.addAttribute("languageLabel", messageSource.getMessage("label.maincontroller.searchText.spanish",null , locale));
			}
			searchValidator.setLocale(locale);
			List<SearchResponseDTO> srList=searchExecutor.search(searchRequest,true);
			SearchValidatorDTO validacion=searchValidator.searchResponseValidate(srList, searchRequest.getRequest());
			if(validacion.getCodError().equals(Constants.COD_ERROR_KO)){
				model.addAttribute("dangerMSG", validacion.getDesError());
			}else{
				model.addAttribute("responseList", srList);
			}
		}
		return "searchTexts";
	}
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String adminUsersPOST(Locale locale, Model model, @ModelAttribute("searchRequest") User newUser) {
		logger.info("MainController.addUser("+newUser+")");
		List<User> userList=new ArrayList<User>();
		try{
			userDAO.save(newUser);
			model.addAttribute("newUser", new User());
			model.addAttribute("successMSG", messageSource.getMessage("label.maincontroller.addUser.ok",null , locale));
		}catch(ConstraintViolationException e){
			model.addAttribute("newUser", newUser);
			model.addAttribute("dangerMSG", "Se produjo error: "+e.getErrorCode()+" - "+e.getMessage());
		}catch(Exception e){
			model.addAttribute("newUser", newUser);
			model.addAttribute("dangerMSG", "Se produjo error: "+e.getMessage());
		}
		finally{
			try{
				userList=userDAO.findAll();
			}catch(ConstraintViolationException e){
				model.addAttribute("newUser", newUser);
				model.addAttribute("dangerMSG", messageSource.getMessage("label.generic.error.codeAndDesc",new Object[]{e.getErrorCode(),e.getMessage()} , locale));
			}catch(Exception e){
				model.addAttribute("newUser", newUser);
				model.addAttribute("dangerMSG", messageSource.getMessage("label.generic.error.desc",new Object[]{e.getMessage()} , locale));
			}
		}
		model.addAttribute("userList", userList);
		return "adminUsers";
	}
	@RequestMapping(value = "/adminUsers", method = RequestMethod.GET)
	public String adminUsersGET(Locale locale, Model model) {
		logger.info("MainController.adminUsersGET");
		List<User> userList=new ArrayList<User>();
		try{
			userList=userDAO.findAll();
		}catch(ConstraintViolationException e){
			model.addAttribute("dangerMSG", messageSource.getMessage("label.generic.error.desc",new Object[]{e.getMessage()} , locale));
		}
		model.addAttribute("newUser", new User());
		model.addAttribute("userList", userList);
		return "adminUsers";
	}
	@RequestMapping(value = "/checkTexts", method = RequestMethod.GET)
	public String checkTexts(Locale locale, Model model, @ModelAttribute("searchRequest") SearchRequestDTO searchRequest) {
		logger.info("MainController.submit("+searchRequest+")");
		if(searchRequest!=null && searchRequest.getRequest() != null && !searchRequest.getRequest().equals("")){
			String lang=searchRequest.getLanguage();
			model.addAttribute("language", lang);
			model.addAttribute("request", searchRequest.getRequest());
			if(lang.equals("eng")){
				model.addAttribute("languageLabel", "inglés");
			}else{
				model.addAttribute("languageLabel", "español");
			}
			searchValidator.setLocale(locale);
			List<SearchResponseDTO> srList=searchExecutor.search(searchRequest,false);
			SearchValidatorDTO validacion=searchValidator.searchResponseValidate(srList, searchRequest.getRequest());
			if(validacion.getCodError().equals(Constants.COD_ERROR_KO)){
				model.addAttribute("dangerMSG", validacion.getDesError());
			}else{
				model.addAttribute("responseList", srList);
			}
		}
		return "checkTexts";
	}

	@RequestMapping(value = "/reset", method = RequestMethod.GET)
    public String reset() {
		logger.info("INICIO");
		try{
			userDao.deleteAll();
			User user=new User("admin", "admin", "admin", "admin", "admin@admin.admin", "ADMIN");
			userDao.save(user);
			logger.info("usuarios reseteados");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			Properties props = new Properties();
			props.setProperty("lastUpdateDate", "");
			String UPDATE_PROPERTIES_FILENAME="update.properties";
			File f = new File(UPDATE_PROPERTIES_FILENAME);
			logger.info("fichero="+f.getAbsolutePath());
			OutputStream out = new FileOutputStream( f );
			DefaultPropertiesPersister p = new DefaultPropertiesPersister();
			p.store(props, out, "Header Comment");
			searchResponseDao.deleteAll();
			logger.info("textos borrados");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		logger.info("reset - FIN");
		return "redirect:/deferredHarvest";
	}

	@RequestMapping(value = "/harvested/{codResultado}", method = RequestMethod.GET)
	public String harvestedOK(Locale locale, Model model,@PathVariable("codResultado")String codResultado, @ModelAttribute("searchRequest") SearchRequestDTO searchRequest) {
		logger.info("MainController.harvestedOK - codResultado="+codResultado);
		model.addAttribute("request", "");
		model.addAttribute("language", "esp");
		if(Constants.STATUS_NO_CONNECTIVITY.equals(codResultado)){
			model.addAttribute("warningMSG", messageSource.getMessage("label.harvest.status.noConnection",null , locale));
		}else if(Constants.STATUS_NO_NEW_RECORDS.equals(codResultado)){
			model.addAttribute("infoMSG", messageSource.getMessage("label.harvest.status.noNewRecords",null , locale));
		}else{
			model.addAttribute("successMSG", messageSource.getMessage("label.maincontroller.harvest.ok",null , locale));
		}
		return "checkTexts";
	}

}
