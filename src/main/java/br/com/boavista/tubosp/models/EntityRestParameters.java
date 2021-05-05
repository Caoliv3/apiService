package br.com.boavista.tubosp.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({ "cpfcnpj" })
public class EntityRestParameters implements Serializable {

	@JsonProperty("cpfcnpj")
	private String cpfcnpj;
	@JsonProperty("tipodoc")
	private Integer tipodoc;

	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -6805910840654832911L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public EntityRestParameters() {
	}

	/**
	 *
	 * @param cpfcnpj 
	 */
	public EntityRestParameters(String cpfcnpj, Integer tipodoc) {
		super();
		this.cpfcnpj = cpfcnpj;
		this.tipodoc = tipodoc;
	}

	@JsonProperty("cpfcnpj")
	public String getCpfCnpj() {
		return cpfcnpj;
	}

	@JsonProperty("cpfcnpj")
	public void setCpfCnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}
	
	@JsonProperty("tipodoc")
	public Integer getTipodoc() {
		return tipodoc;
	}

	@JsonProperty("tipodoc")
	public void setTipodoc(Integer tipodoc) {
		this.tipodoc = tipodoc;
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