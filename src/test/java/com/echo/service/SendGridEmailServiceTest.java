package com.echo.service;

import java.util.Arrays;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.echo.domain.EmailVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring-config.xml" })
public class SendGridEmailServiceTest {

	@Autowired
	private SendGridEmailService sendGridEmailService;
	
	private EmailVO emailVo;
	
	@Before
	public void init() {
		this.emailVo = new EmailVO();
		emailVo.setRecipients(Arrays.asList("echozbb@gmail.com","echotest@gmail.com"));
		emailVo.setHeader("Echo test sendGrid email");
		emailVo.setBody("This is a testing email from SendGrid! Have a nice day!");
		emailVo.setCcList(Arrays.asList("echotestcc@gmail.com", "echotestcc2@gmail.com"));
		emailVo.setBccList(Arrays.asList("echotestbcc@gmail.com"));
	}
	
	@Test
	public void sendEmail_success() {
		Boolean result = sendGridEmailService.send(this.emailVo);
		Assert.assertTrue(result);
	}
	
	@Test
	public void sendEmail_failed() {
		this.emailVo.setRecipients(Collections.emptyList());
		Boolean result = sendGridEmailService.send(this.emailVo);
		Assert.assertFalse(result);
	}
}
