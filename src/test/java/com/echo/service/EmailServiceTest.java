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
public class EmailServiceTest {

	@Autowired
	private EmailService emailService;
	
	private EmailVO emailVo;
	
	@Before
	public void init() {
		this.emailVo = new EmailVO();
		emailVo.setRecipients(Arrays.asList("echozbb@gmail.com","echotest@gmail.com"));
		emailVo.setHeader("Echo test send email service");
		emailVo.setBody("This is a testing email from SendGrid! Have a nice day!");
		emailVo.setCcList(Arrays.asList("echotestcc@gmail.com", "echotestcc2@gmail.com"));
		emailVo.setBccList(Arrays.asList("echotestbcc@gmail.com"));
	}
	
	@Test
	public void send_mail_success() throws Exception {
		Assert.assertTrue(emailService.sendEmail(emailVo));
	}
	
	@Test(expected = Exception.class)
	public void send_mail_exception() throws Exception {
		emailVo.setBody("");
		emailService.sendEmail(emailVo);
	}
	
	@Test
	public void send_mail_failed() throws Exception {
		emailVo.setRecipients(Arrays.asList("invalidemailaddress"));
		Assert.assertFalse(emailService.sendEmail(emailVo));
	}
}
