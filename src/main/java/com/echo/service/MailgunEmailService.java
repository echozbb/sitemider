package com.echo.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.echo.domain.EmailVO;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Service
public class MailgunEmailService implements EmailProvider {

	private final static Logger LOGGER = LoggerFactory.getLogger(MailgunEmailService.class);
	
	private final static String URL = "https://api.mailgun.net/v3/";
	
	private final static String API_KEY = "3e20539d8953510b6f4b3e9d03c61341-41a2adb4-6638ebb2";
	
	private final static String DOMAIN_NAME = "sandbox77adc5f670f44c859792f229f2323d3a.mailgun.org";
	
	private final static int SUCCESS = 200;
	
	
	private static final ObjectMapper SORTED_MAPPER = new ObjectMapper();
	  static {
	    SORTED_MAPPER.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
	  }

	private RestTemplate rest;
	
	private HttpHeaders jsonHeaders;
	  
	@PostConstruct
	public void init() {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
 		requestFactory.setReadTimeout(60000);
 		this.rest = new org.springframework.web.client.RestTemplate(requestFactory);
 		
 		this.jsonHeaders = new HttpHeaders();
 		jsonHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);
 		jsonHeaders.setBasicAuth("api", API_KEY);
	}
	
	@Override
	public Boolean send(EmailVO emailVo){
		
	   LOGGER.info("Sending request to server.");

	   try {
		   Map<String,String> map = new HashMap<String, String>();
		   map.put("to",convertRecipients(emailVo.getRecipients()));
		   map.put("from", "Mailgun Sandbox <postmaster@sandbox77adc5f670f44c859792f229f2323d3a.mailgun.org>");
		   
		   if (!CollectionUtils.isEmpty(emailVo.getCcList())){
			   map.put("cc", convertRecipients(emailVo.getCcList()));
		   }
		  
		   if (!CollectionUtils.isEmpty(emailVo.getBccList())) {
			   map.put("bcc", convertRecipients(emailVo.getBccList()));
		   }
		  
		   map.put("subject", emailVo.getHeader() == null ? "" : emailVo.getHeader());
		   map.put("text", emailVo.getBody() == null ? "" : emailVo.getBody());
		   
		   String encodeString = urlEncodeUTF8(map);
		   LOGGER.info("request: " + encodeString);
		   HttpEntity<String> requestEntity = new HttpEntity<String>(encodeString, jsonHeaders);

		   ResponseEntity<String> responseEntity = rest.exchange(URL + DOMAIN_NAME + "/messages", HttpMethod.POST, requestEntity, String.class);
		   LOGGER.info("Response code: " + responseEntity.getStatusCode());
	 	   LOGGER.info("Response content: " +  responseEntity.getBody());
	 	   
	 	   if (SUCCESS == responseEntity.getStatusCodeValue()) {
	 		   LOGGER.info("Send email success");
	 		   return Boolean.TRUE;
	 	   }
	   }catch (Exception e) {
		   LOGGER.error("send email with error", e);
	   }
	   
	   return Boolean.FALSE;
	 
	}
	
	private static String urlEncodeUTF8(Map<?,?> map) throws UnsupportedEncodingException {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<?,?> entry : map.entrySet()) {
            if (sb.length() > 0) {
                sb.append("&");
            }
            sb.append(String.format("%s=%s",
            		URLEncoder.encode(entry.getKey().toString(), "UTF-8"),
            		URLEncoder.encode(entry.getValue().toString(), "UTF-8")
            ));
        }
        return sb.toString();       
    }
	
	private static String convertRecipients(List<String> recipients) {
		if (CollectionUtils.isEmpty(recipients)) {
			return null;
		}
		return StringUtils.arrayToDelimitedString(recipients.toArray(new String[recipients.size()]), ";");
	}

	

}
