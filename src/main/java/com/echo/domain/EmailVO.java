package com.echo.domain;

import java.util.Collections;
import java.util.List;

public class EmailVO {
	
	private List<String> recipients = Collections.emptyList();
	
	private List<String> ccList = Collections.emptyList();
	
	private List<String> bccList = Collections.emptyList();
	
	private String body;

	private String header;


	public List<String> getRecipients() {
		return recipients;
	}

	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	public List<String> getCcList() {
		return ccList;
	}

	public void setCcList(List<String> ccList) {
		this.ccList = ccList;
	}

	public List<String> getBccList() {
		return bccList;
	}

	public void setBccList(List<String> bccList) {
		this.bccList = bccList;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getHeader() {
		return header;
	}

	public void setHeader(String header) {
		this.header = header;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((bccList == null) ? 0 : bccList.hashCode());
		result = prime * result + ((body == null) ? 0 : body.hashCode());
		result = prime * result + ((ccList == null) ? 0 : ccList.hashCode());
		result = prime * result + ((header == null) ? 0 : header.hashCode());
		result = prime * result + ((recipients == null) ? 0 : recipients.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailVO other = (EmailVO) obj;
		if (bccList == null) {
			if (other.bccList != null)
				return false;
		} else if (!bccList.equals(other.bccList))
			return false;
		if (body == null) {
			if (other.body != null)
				return false;
		} else if (!body.equals(other.body))
			return false;
		if (ccList == null) {
			if (other.ccList != null)
				return false;
		} else if (!ccList.equals(other.ccList))
			return false;
		if (header == null) {
			if (other.header != null)
				return false;
		} else if (!header.equals(other.header))
			return false;
		if (recipients == null) {
			if (other.recipients != null)
				return false;
		} else if (!recipients.equals(other.recipients))
			return false;
		return true;
	}


}
