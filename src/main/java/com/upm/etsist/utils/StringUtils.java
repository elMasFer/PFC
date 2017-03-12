package com.upm.etsist.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class StringUtils {
	private final static String reemplazoToHTML="<:&lt;,>:&gt;,&:&amp;,\":&quot;,':&apos;,ñ:&ntilde;,Ñ:&Ntilde;,á:&aacute;,é:&eacute;,í:&iacute;,ó:&oacute;,ú:&uacute;,Á:&Aacute;,É:&Eacute;,Í:&Iacute;,Ó:&Oacute;,Ú:&Uacute;,€:&euro;";
	private final static String reemplazoFromHTML="&lt;:<,&gt;:>,&amp;:&,&quot;:\",&nbsp;: ,&apos;:',&ntilde;:ñ,&Ntilde;:Ñ,&aacute;:á,&eacute;:é,&iacute;:í,&oacute;:ó,&uacute;:ú,&Aacute;:Á,&Eacute;:É,&Iacute;:Í,&Oacute;:Ó,&Uacute;:Ú,&euro;:€";
	private static final Logger logger = LoggerFactory.getLogger(StringUtils.class);

	
	public static String fromHTML(String entrada){
		return sustituyeCaracteres(entrada,reemplazoFromHTML);
	}
	
	public static String toHTML(String entrada){
		String retorno=sustituyeCaracteres(entrada,reemplazoToHTML);
		logger.info("toHTML - retorno=["+retorno+"]");
		return retorno;
	}
	
	public static String getFormattedDateAndString(String dateFormat, String newMessage) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		String prefix=sdf.format(new Date());
		return prefix+" - "+newMessage;
	}
	
	private static String sustituyeCaracteres(String cadena, String cadenaReemplazos) {
		String retorno=new String(cadena);
		String[] arrayReemplazos=cadenaReemplazos.split(",");
		for(int i=0;i<arrayReemplazos.length;i++){
			String reemplazo=arrayReemplazos[i];
			String[]reemplazos=reemplazo.split(":");
			retorno=retorno.replaceAll(reemplazos[0], reemplazos[1]);
		}
		return retorno;
	}
	public static String highlightTextInString(String cadena, String texto){
		String retorno=new String(cadena);
		int index=0;
		int long_texto=texto.length();
		int pos=0;
		while((pos=retorno.toLowerCase().indexOf(texto.toLowerCase(), index)) != -1){
			retorno=retorno.substring(0,pos)+"<kbd>"+retorno.substring(pos,pos+long_texto)+"</kbd>"+retorno.substring(pos+long_texto);
			index=pos+11+long_texto;
		}
		return retorno;
	}
}
