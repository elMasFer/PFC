package com.upm.etsist.validator;

import java.util.List;

import com.upm.etsist.dto.SearchRequestDTO;
import com.upm.etsist.dto.SearchResponseDTO;
import com.upm.etsist.dto.SearchValidatorDTO;
import com.upm.etsist.executor.SearchExecutor;

public class UpdateValidator {
	
	public static final String COD_ERROR_OK="0";
	public static final String COD_ERROR_KO="1";
	static final String DES_ERROR_NO_RESULTADOS_CADENA_REEMPLAZAR="_request_";
	static final String DES_ERROR_NO_RESULTADOS="No se encontraron resultados para <b>"+DES_ERROR_NO_RESULTADOS_CADENA_REEMPLAZAR+"</b>";
	static final String DES_ERROR_TEXTO_NO_INFORMADO="Se ha de informar algún valor en el campo de búsqueda";
	static final String DES_ERROR_ERROR_GENERICO="Se produjo un error inesperado";
	
	public static SearchValidatorDTO updateResponseValidate(List<SearchResponseDTO> srList,String request){
		SearchValidatorDTO retorno=new SearchValidatorDTO();
		if(srList.size() == 0){
			retorno.setCodError(COD_ERROR_KO);
			retorno.setDesError(DES_ERROR_NO_RESULTADOS.replace(DES_ERROR_NO_RESULTADOS_CADENA_REEMPLAZAR, request));
		}
		return retorno;
	}
}
