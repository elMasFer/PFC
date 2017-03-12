package com.upm.etsist.executor;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.upm.etsist.dao.SearchResponseDAO;
import com.upm.etsist.dto.SearchRequestDTO;
import com.upm.etsist.dto.SearchResponseDTO;
import com.upm.etsist.utils.StringUtils;

public class SearchExecutor {
	
    @Autowired  
    private SearchResponseDAO searchResponseDao;  

	private static final Logger logger = LoggerFactory.getLogger(SearchExecutor.class);

	public void setSearchResponseDao(SearchResponseDAO searchResponseDao) {
		this.searchResponseDao = searchResponseDao;
	}

	public List<SearchResponseDTO> search(SearchRequestDTO sr,boolean highlight){
		logger.info("SearchProcessor.search("+sr+")");
		String lang=sr.getLanguage();
		String request=sr.getRequest();
		List<SearchResponseDTO> retorno=new ArrayList<SearchResponseDTO>();
		if(lang.equals("eng")){
			retorno=searchResponseDao.findENG(sr.getRequest());
			for(int i=0;i<retorno.size();i++){
				SearchResponseDTO response=retorno.get(i);
				if(highlight){
					response.setEnglishText(StringUtils.highlightTextInString(response.getEnglishText(),request));
				}
			}
		}else{
			retorno=searchResponseDao.findSPA(sr.getRequest());
			for(int i=0;i<retorno.size();i++){
				SearchResponseDTO response=retorno.get(i);
				if(highlight){
					response.setSpanishText(StringUtils.highlightTextInString(response.getSpanishText(),request));
				}
			}
		}
		return retorno;
	}
}
