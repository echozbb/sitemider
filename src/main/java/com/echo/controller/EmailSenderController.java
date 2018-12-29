package com.echo.controller;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.echo.domain.EmailVO;
import com.echo.service.EmailService;

@RestController
public class EmailSenderController {

	private final static Logger LOGGER = LoggerFactory.getLogger(EmailSenderController.class);
	
	@Autowired
	private EmailService emailService;

	
	@RequestMapping(value = "/send", method = RequestMethod.POST)
	public ResponseEntity<Boolean> send(@RequestBody(required = true) EmailVO emailVo) throws Exception {

		LOGGER.info("Received restful send email request");
		if (Boolean.TRUE.equals(emailService.sendEmail(emailVo))){
			return new ResponseEntity<Boolean>(Boolean.TRUE, HttpStatus.OK);
		}
		return new ResponseEntity<Boolean>(Boolean.FALSE, HttpStatus.BAD_REQUEST);
		
	}
}
