package com.upm.etsist.executor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.apache.xerces.parsers.DOMParser;
import org.dlese.dpc.oai.harvester.Harvester;
import org.dlese.dpc.oai.harvester.Hexception;
import org.dlese.dpc.oai.harvester.OAIErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.util.DefaultPropertiesPersister;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.upm.etsist.ajax.Hook;
import com.upm.etsist.ajax.Message;
import com.upm.etsist.ajax.ShutdownService;
import com.upm.etsist.dao.SearchResponseDAO;
import com.upm.etsist.dto.SearchResponseDTO;
import com.upm.etsist.dto.HarvestRequestDTO;
import com.upm.etsist.handler.CutomHarvestMessageHandler;
import com.upm.etsist.utils.Constants;

public class HarvestExecutor implements Runnable{

	private MessageSource messageSource;

	private SearchResponseDAO searchResponseDao;
	
	private ShutdownService shutdownService;
	
	private HarvestRequestDTO harvestRequestDTO;
	
	private String proxyHost;
	private String proxyPort;
	private String proxyUser;
	private String proxyPwd;
	private String useProxy;
	
	private static final Logger logger = Logger.getLogger(SearchResponseDAO.class);

	private static final String UPDATE_PROPERTIES_FILENAME="update.properties";
	private static final String DATE_FORMAT="dd-M-yyyy HH:mm:ss";
	private static final int MAX_SIZE=10000;
	
	private Locale locale;
	
	private volatile boolean start = true;
	private Queue<Message> messagesQueue;
	private CutomHarvestMessageHandler cutomHarvestMessageHandler;
	private Hook hook;
	
	
	public CutomHarvestMessageHandler getCutomHarvestMessageHandler() {
		return cutomHarvestMessageHandler;
	}

	public void setCutomHarvestMessageHandler(CutomHarvestMessageHandler cutomHarvestMessageHandler) {
		this.cutomHarvestMessageHandler = cutomHarvestMessageHandler;
	}

	public HarvestExecutor() {
		super();
	}

	public void start() {

		if (start) {
			synchronized (this) {
				if (start) {
					start = false;
					logger.info("Comenzando el hilo del Cosechador...");
					Thread thread = new Thread(this, "Hilo del Cosechador");
					hook = shutdownService.createHook(thread);

					thread.start();
				}
			}
		} else {
			logger.warn("Cosecha en progreso");
		}
	}
	
	@Override
	public void run(){
		sleep(5);
		logger.info("HarvestExecutor.run()");
		addMessage(messageSource.getMessage("label.harvest.status.beginning",null , locale),0);
		Date ahora=new Date();
		harvestRequestDTO.setFrom(readLastUpdateDate());
		harvestRequestDTO.setUntil(ahora);
		if("true".equals(useProxy)){
	        System.setProperty("http.proxyHost", proxyHost);
	        System.setProperty("http.proxyPort", proxyPort);
	        System.setProperty("https.proxyHost", proxyHost);
	        System.setProperty("https.proxyPort", proxyPort);
	        System.setProperty("http.proxyUser", proxyUser);
	        System.setProperty("http.proxyPassword", proxyPwd);
			Authenticator.setDefault(
					  new Authenticator() {
					    public PasswordAuthentication getPasswordAuthentication() {
					      return new PasswordAuthentication("IMM0434", "mare0001".toCharArray());
					    }
					  }
					);
			System.setProperty("java.net.useSystemProxies", "true");
		}
		String[][] updateResponse=harvest();
		int numRegistros=0;
		if(updateResponse!=null){
			numRegistros=updateResponse.length;
		}
		if(numRegistros > 0){
			addMessage(messageSource.getMessage("label.harvest.status.records.got",new Object[]{harvestRequestDTO.getBaseURL(),numRegistros} , locale),50);
			this.saveResponse(updateResponse);
			writeLastUpdateDate(ahora);
		}
		if("".equals(cutomHarvestMessageHandler.getStatus())){
			cutomHarvestMessageHandler.setStatus(Constants.STATUS_OK);
		}
		addMessage(messageSource.getMessage("label.harvest.status.finished",null , locale),100,cutomHarvestMessageHandler.getStatus());
		start = true;
	}

	private String[][] harvest(){
		String[][] retorno=null;
		Harvester harvester = new Harvester();
		cutomHarvestMessageHandler.setLocale(locale);
		try {
			retorno=harvester.harvest(harvestRequestDTO.getBaseURL(),
					harvestRequestDTO.getMetadataPrefix(),
					harvestRequestDTO.getSet(),
					harvestRequestDTO.getFrom(),
					harvestRequestDTO.getUntil(),
					harvestRequestDTO.getOutdir(),
					harvestRequestDTO.isSplitBySet(),
					cutomHarvestMessageHandler,
					null, 
					harvestRequestDTO.isWriteHeaders(),
					harvestRequestDTO.isHarvestAll(),
					harvestRequestDTO.isHarvestAllIfNoDeletedRecord(),
					99999999);
		} catch (Hexception e) {
			e.printStackTrace();
		} catch (OAIErrorException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	private Date readLastUpdateDate(){
		Date retorno=null;
		try {
			InputStream in=new FileInputStream(UPDATE_PROPERTIES_FILENAME);
			DefaultPropertiesPersister p = new DefaultPropertiesPersister();
			Properties props = new Properties();
			p.load(props, in);
			String fecha=props.get("lastUpdateDate").toString();
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			retorno = sdf.parse(fecha);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return retorno;		
	}

	private void writeLastUpdateDate(Date fecha){
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);
			Properties props = new Properties();
			props.setProperty("lastUpdateDate", sdf.format(fecha));
			File f = new File(UPDATE_PROPERTIES_FILENAME);
			OutputStream out = new FileOutputStream( f );
			DefaultPropertiesPersister p = new DefaultPropertiesPersister();
			p.store(props, out, "Header Comment");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// write into it

	}

	private void saveResponse(String[][] updateResponseList){
		if(updateResponseList!=null){
			List<SearchResponseDTO> searchResponsesList=new ArrayList<SearchResponseDTO>();
			int updateResponseListLength=updateResponseList.length;
			addMessage(messageSource.getMessage("label.harvest.status.records.save.beginning",null, locale),50);
			for(int i=0;i<updateResponseListLength;i++){
				try{
					SearchResponseDTO srDTO=new SearchResponseDTO();
					String xml=updateResponseList[i][1];
					DOMParser parser = new DOMParser();
					parser.parse(new InputSource(new java.io.StringReader(xml)));
					Document doc = parser.getDocument();
					doc.getDocumentElement().normalize();
					NodeList nList1 = doc.getElementsByTagName("dc:description");
					boolean encontrado=false;
					for (int temp = 0; temp < nList1.getLength() && !encontrado; temp++) {
						Node nNode1 = nList1.item(temp);
						String descripcion=nNode1.getFirstChild().getNodeValue();
						if(descripcion.indexOf("ABSTRACT") != -1){
							encontrado=true;
							NodeList nList2 = doc.getElementsByTagName("dc:identifier");
							for (int j = 0; j < nList2.getLength(); j++) {
								Node nNode2 = nList2.item(temp);
								srDTO.setUrl(nNode2.getFirstChild().getNodeValue());
							}
							String spa=descripcion.substring(0, descripcion.indexOf("ABSTRACT"));
							String eng=descripcion.substring(descripcion.indexOf("ABSTRACT")+8).trim();
							if(eng.startsWith(".")){
								eng=eng.substring(1).trim();					
							}
							if(spa.length() <= MAX_SIZE && eng.length() <= MAX_SIZE){
								srDTO.setEnglishText(eng);
								srDTO.setSpanishText(spa);
								searchResponsesList.add(srDTO);
							}
						}
					}
				}catch(SAXException e){
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			addMessage(messageSource.getMessage("label.harvest.status.records.save.processed",new Object[]{searchResponsesList.size()} , locale),75);
			if(searchResponsesList.size()>0){
				searchResponseDao.saveAll(searchResponsesList);
			}
			addMessage(messageSource.getMessage("label.harvest.status.records.save.finished",null , locale),95);
		}
	}
	
	private void addMessage(String text,int progress){
		logger.info("addMessage("+text+")");
		messagesQueue.add(new Message(text,progress));
	}
	private void addMessage(String text,int progress,String status){
		logger.info("addMessage("+text+")");
		messagesQueue.add(new Message(text,progress,status));
	}
	
	private void sleep(int deplay) {
		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			logger.info("Interrupción del sueño del hilo...");
		}
	}
	public ShutdownService getShutdownService() {
		return shutdownService;
	}

	public void setShutdownService(ShutdownService shutdownService) {
		this.shutdownService = shutdownService;
	}

	public Queue<Message> getMessagesQueue() {
		return messagesQueue;
	}

	public void setMessagesQueue(Queue<Message> messagesQueue) {
		this.messagesQueue = messagesQueue;
	}

	public SearchResponseDAO getSearchResponseDao() {
		return searchResponseDao;
	}

	public void setSearchResponseDao(SearchResponseDAO searchResponseDao) {
		this.searchResponseDao = searchResponseDao;
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

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public void setProxyPort(String proxyPort) {
		this.proxyPort = proxyPort;
	}

	public void setProxyUser(String proxyUser) {
		this.proxyUser = proxyUser;
	}

	public void setProxyPwd(String proxyPwd) {
		this.proxyPwd = proxyPwd;
	}

	public void setUseProxy(String useProxy) {
		this.useProxy = useProxy;
	}

	public HarvestRequestDTO getHarvestRequestDTO() {
		return harvestRequestDTO;
	}

	public void setHarvestRequestDTO(HarvestRequestDTO harvestRequestDTO) {
		this.harvestRequestDTO = harvestRequestDTO;
	}

}
