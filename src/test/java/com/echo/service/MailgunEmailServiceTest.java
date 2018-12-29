package com.echo.service;

import java.util.Arrays;

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
public class MailgunEmailServiceTest {

	@Autowired
	private MailgunEmailService mailgunEmailSerice;
	
	private EmailVO emailVo;
	
	@Before
	public void init() {
		this.emailVo = new EmailVO();
		emailVo.setRecipients(Arrays.asList("echozbb@gmail.com","wsas123@gmail.com"));
		emailVo.setHeader("Echo test Mailgun email");
		emailVo.setBody("This is a testing email from Mailgun! Have a nice day!");
	    emailVo.setCcList(Arrays.asList("echozbb@gmail.com", "echozbb@gmail.com"));
		emailVo.setBccList(Arrays.asList("echozbb@gmail.com","echozbb@gmail.com","wsas123@gmail.com"));
	}
	
	@Test
	public void sendEmail_success() {
		Boolean result = mailgunEmailSerice.send(this.emailVo);
		Assert.assertTrue(result);
	}
	
	@Test
	public void sendEmail_failed() {
		this.emailVo.setCcList(Arrays.asList("testing@gmail.com"));
		Boolean result = mailgunEmailSerice.send(this.emailVo);
		Assert.assertFalse(result);
	}
}
