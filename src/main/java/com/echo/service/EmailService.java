package com.echo.service;

import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import com.echo.domain.EmailVO;
import com.echo.exception.ValidationException;

@Service
public class EmailService {
	
	private List<EmailProvider> providerList;
	
	public EmailService(List<EmailProvider> providers) {
		if (CollectionUtils.isEmpty(providers)) {
			this.providerList = Collections.emptyList();
		}
		this.providerList = providers;
	}

	public Boolean sendEmail(EmailVO emailVo){

		for (EmailProvider provider: this.providerList) {
			if (Boolean.TRUE.equals(provider.send(emailVo))) {
				return Boolean.TRUE;
			}
		}
		
		return Boolean.FALSE;
	}
	
	public void validEmailVo(EmailVO emailVo) throws ValidationException {
		if (emailVo == null) {
			throw new ValidationException("Email is empty");
		}
		
		if (CollectionUtils.isEmpty(emailVo.getRecipients()) ) {
			throw new ValidationException("Recipient is empty");
		}
		
		if (StringUtils.isEmpty(emailVo.getHeader())) {
			throw new ValidationException("Header is empty");
		}
		
		if (StringUtils.isEmpty(emailVo.getBody())) {
			throw new ValidationException("Body is empty");
		}
	}


}
