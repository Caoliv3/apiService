package br.com.boavista.tubosp.models;

import java.util.ArrayList;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"sites_urls"})
public class EntityRestReceipt implements Serializable {

	@JsonProperty("sites_urls")
	private List<String> sitesUrls = new ArrayList<String>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -1804815454991321028L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public EntityRestReceipt() {
		
	}

	/**
	 *
	 * @param sitesUrls
	 * @param id
	 * @param url
	 * @param key
	 */
	public EntityRestReceipt(List<String> sitesUrls) {
		super();
		this.sitesUrls = sitesUrls;
	}

	@JsonProperty("sites_urls")
	public List<String> getSitesUrls() {
		return sitesUrls;
	}

	@JsonProperty("sites_urls")
	public void setSitesUrls(List<String> sitesUrls) {
		this.sitesUrls = sitesUrls;
	}

	@JsonAnyGetter
	public Map<String, Object> getAdditionalProperties() {
		return this.additionalProperties;
	}

	@JsonAnySetter
	public void setAdditionalProperty(String name, Object value) {
		this.additionalProperties.put(name, value);
	}

}