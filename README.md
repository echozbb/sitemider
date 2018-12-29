# sitemider
email sending restful api
url: http://localhost:8080/emailrouter/send
method: POST
body sample:
{
	"recipients": ["echozbb@gmail.com","wsas123@gmail.com","absdf@gmail.com"],
	"body":"hello, it is a testing email from echo!",
	"header": "header is header",
	"cc": ["cc1test@gmail.com","cc2test@gmail.com"],
	"bcc": ["bcc1test@gmail.com","bcc2test@gmail.com"]
}
return value: boolean 
   True - email send success;
   False - email send failed.

