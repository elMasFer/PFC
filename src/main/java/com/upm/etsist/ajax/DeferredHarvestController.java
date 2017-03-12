package com.upm.etsist.ajax;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.async.DeferredResult;


/**
 * Long pole example - uses the match update example.
 * 
 * @author Roger
 * 
 */
@Controller()
public class DeferredHarvestController {
	
	private static final Logger logger = Logger.getLogger(DeferredHarvestController.class);

	@Autowired
	private DeferredResultService deferredResultService;
	
    @RequestMapping(value = "/deferredHarvest", method = RequestMethod.GET)
    public String loginPage() {
    	logger.info("deferredHarvest()");
        return "deferredHarvest";
    }
 


	@RequestMapping(value = "/harvest/begin" + "", method = RequestMethod.GET)
	@ResponseBody
	public String start(Locale locale) {
    	logger.info("start()");
		deferredResultService.subscribe();
		return "OK";
	}

	@RequestMapping("/harvest/deferred")
	@ResponseBody
	public DeferredResult<Message> getUpdate(Locale locale) {
    	logger.info("getUpdate()");
		final DeferredResult<Message> result = new DeferredResult<Message>();
		deferredResultService.getUpdate(result);
		return result;
	}



	public DeferredResultService getDeferredResultService() {
		return deferredResultService;
	}



	public void setDeferredResultService(DeferredResultService deferredResultService) {
		this.deferredResultService = deferredResultService;
	}
}
