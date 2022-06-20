package ch.hug.simed.covid.frontend.client.service;

import java.io.Serializable;

public class Info implements Serializable {
	private String version;
	private String host;
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	

}
