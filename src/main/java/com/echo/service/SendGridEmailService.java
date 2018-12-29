package com.echo.service;


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
import org.springframework.web.client.RestTemplate;

import com.echo.domain.EmailVO;
import com.echo.domain.sendgrid.Content;
import com.echo.domain.sendgrid.Email;
import com.echo.domain.sendgrid.Mail;
import com.echo.domain.sendgrid.Personalization;

@Service
public class SendGridEmailService implements EmailProvider{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(SendGridEmailService.class);

	private static final String API_KEY = "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";
	
	private static final String URL = "https://api.sendgrid.com/v3/mail/send";
	
	private static final int SUCCESS = 202;
	
	private static final String FROM = "echotest@gmail.com";
	
	private RestTemplate rest;
	
	private HttpHeaders jsonHeaders;
	
	@PostConstruct
	public void init() {
		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
 		requestFactory.setReadTimeout(60000);
 		this.rest = new org.springframework.web.client.RestTemplate(requestFactory);
 		
 		this.jsonHeaders = new HttpHeaders();
 		jsonHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
 		jsonHeaders.add(HttpHeaders.CONTENT_ENCODING, "gzip");
 		jsonHeaders.setBearerAuth(API_KEY);
	}
	
	@Override
	public Boolean send(EmailVO emailVo) {
		Email from = new Email(FROM);
	    String subject = emailVo.getHeader();
	    
	    Mail mail = new Mail();
	    mail.setSubject(subject);
	    mail.setFrom(from);
	    mail.addContent(new Content(MediaType.TEXT_PLAIN_VALUE, emailVo.getBody()));
	    Personalization personalization = new Personalization();
	    emailVo.getRecipients().forEach(rec -> {
	    	personalization.addTo(new Email(rec));
	    });
	    emailVo.getCcList().forEach(cc -> {
	    	personalization.addCc(new Email(cc));
	    });
	    
	    emailVo.getBccList().forEach(bcc -> {
	    	personalization.addBcc(new Email(bcc));
	    });

	    mail.addPersonalization(personalization);
	    
	    LOGGER.info("Sending email to server");
	    try {
	    	
	 		LOGGER.info("request body:" + mail.buildPretty());
	 		HttpEntity<String> requestEntity = new HttpEntity<String>(mail.build(), jsonHeaders);
	 		ResponseEntity<String> responseEntity = rest.exchange(URL, HttpMethod.POST, requestEntity, String.class);
	 		LOGGER.info("Response code: " + responseEntity.getStatusCode());
	 		LOGGER.info("Response content: " +  responseEntity.getBody());
	 
	 		if (SUCCESS == responseEntity.getStatusCodeValue()) {
	 			LOGGER.info("Send email success");
	 			return Boolean.TRUE;
	 		}
	    }catch (Exception e) {
	    	LOGGER.error("Call sendGrid service failed.", e);
	    }
	   
		return Boolean.FALSE;
		
	}

	
}
