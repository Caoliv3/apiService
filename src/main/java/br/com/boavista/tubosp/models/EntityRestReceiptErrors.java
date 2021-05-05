package br.com.boavista.tubosp.models;

import java.util.ArrayList;

public class EntityRestReceiptErrors {
	private String url;
	private String id;
	private String key;
	ArrayList<Object> sites_urls = new ArrayList<Object>();

	// Getter Methods
	
	public EntityRestReceiptErrors(String url, String id, String key, ArrayList sites_urls) {
		this.url = url;
		this.id = id;
		this.key = key;
		this.sites_urls = sites_urls;
	}

	public String getUrl() {
		return url;
	}

	public String getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	// Setter Methods

	public void setUrl(String url) {
		this.url = url;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}
}
