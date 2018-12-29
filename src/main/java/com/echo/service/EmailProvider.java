package com.echo.service;

import com.echo.domain.EmailVO;

public interface EmailProvider {
	
	Boolean send(EmailVO emailVo);
}
