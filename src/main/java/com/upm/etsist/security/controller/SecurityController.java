package com.upm.etsist.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.upm.etsist.dto.SearchRequestDTO;
import com.upm.etsist.security.dao.UserDAO;
import com.upm.etsist.security.dto.ChangePasswordDTO;
import com.upm.etsist.security.model.User;
import com.upm.etsist.utils.Constants;

import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.exception.ConstraintViolationException;
import org.apache.log4j.Logger;

@Controller
public class SecurityController {

	@Autowired
    private MessageSource messageSource;

	@Autowired  
	private UserDAO userDAO;  

	private static final Logger logger = Logger.getLogger(SecurityController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String loginPage(Locale locale, Model model) {
        return "login";
    }
 
    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
    	logger.info("MainController.logout()");
    	try{
	        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	        if (auth != null){
	            new SecurityContextLogoutHandler().logout(request, response, auth);
	        }
    	}catch(Exception e){
    		e.printStackTrace();
    	}
        return "redirect:/";
    }
 
    private String getPrincipal(){
        String userName = null;
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 
        if (principal instanceof UserDetails) {
            userName = ((UserDetails)principal).getUsername();
        } else {
            userName = principal.toString();
        }
        return userName;
    }

    @RequestMapping(value = "/updatePassword", method = RequestMethod.GET)
    public String changeUserPassword() {
        return "changePassword";
    }
 

    @RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
    public String changeUserPassword(Locale locale, Model model,RedirectAttributes rm, @ModelAttribute("changePassword") ChangePasswordDTO changePassword) {
    	logger.info("MainController.changeUserPassword("+changePassword+")");
		User user = userDAO.findBySSO(getPrincipal());
        if (!user.getPassword().equals(changePassword.getOldPassword())) {
        	model.addAttribute("dangerMSG", messageSource.getMessage("label.maincontroller.updatePassword.validation.ko",null , locale));
        	return "changePassword";
        }else{
        	user.setPassword(changePassword.getNewPassword());
    		try{
    			userDAO.update(user);
    		}catch(ConstraintViolationException e){
    			model.addAttribute("dangerMSG", messageSource.getMessage("label.generic.error.codeAndDesc",new Object[]{e.getErrorCode(),e.getMessage()} , locale));
    			return "changePassword";
    		}
       	
        	rm.addFlashAttribute("successMSG", messageSource.getMessage("label.maincontroller.updatePassword.ok",null , locale));
        	return "redirect:/";
       	
        }
    }

    @RequestMapping(value = "/loggedOut/{codResultado}", method = RequestMethod.GET)
	public String loggedOut(Locale locale, Model model,@PathVariable("codResultado")String codResultado, @ModelAttribute("searchRequest") SearchRequestDTO searchRequest) {
		logger.info("MainController.harvestedOK - codResultado="+codResultado);
		model.addAttribute("request", "");
		model.addAttribute("language", "esp");
		if(Constants.STATUS_OK.equals(codResultado)){
			model.addAttribute("successMSG", messageSource.getMessage("label.logout.OK",null , locale));
		}
		return "searchTexts";
	}

}
