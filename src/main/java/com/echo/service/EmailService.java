package com.echo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.echo.domain.EmailVO;

@Service
public class EmailService {
	
	private List<EmailProvider> providerList;
	
	public EmailService(List<EmailProvider> providers) {
		if (CollectionUtils.isEmpty(providers)) {
			this.providerList = Collections.emptyList();
		}
		this.providerList = providers;
	}

	public Boolean sendEmail(EmailVO emailVo) throws Exception {
		
		if (emailVo == null) {
			throw new Exception("Email is empty");
		}
		
		if (CollectionUtils.isEmpty(emailVo.getRecipients()) ) {
			throw new Exception("Recipients is empty");
		}
		
		if (StringUtils.isEmpty(emailVo.getHeader())) {
			throw new Exception("Header is empty");
		}
		
		if (StringUtils.isEmpty(emailVo.getBody())) {
			throw new Exception("Body is empty");
		}
		
		for (EmailProvider provider: this.providerList) {
			if (Boolean.TRUE.equals(provider.send(emailVo))) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}


}
