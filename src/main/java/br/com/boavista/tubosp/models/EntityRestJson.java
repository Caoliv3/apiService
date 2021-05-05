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
@JsonPropertyOrder({ "code", "code_message", "header", "data_count", "data", "errors", "receipt" })
public class EntityRestJson implements Serializable {

	@JsonProperty("code")
	private int code = 0;
	@JsonProperty("code_message")
	private String codeMessage = "";
	@JsonProperty("header")
	private EntityRestHeader header;
	@JsonProperty("data_count")
	private int dataCount;
	@JsonProperty("data")
	private EntityRestData data;
	@JsonProperty("errors")
	private Object errors;
	@JsonProperty("receipt")
	private EntityRestReceipt receipt;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = 8449057169443922534L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public EntityRestJson() {
	}

	/**
	 *
	 * @param code
	 * @param codeMessage
	 * @param data
	 * @param header
	 * @param receipt
	 * @param errors
	 * @param dataCount
	 */
	public EntityRestJson(int code, String codeMessage, EntityRestHeader header, int dataCount, EntityRestData data, Object errors,
			EntityRestReceipt receipt) {
		super();
		this.code = code;
		this.codeMessage = codeMessage;
		this.header = header;
		this.dataCount = dataCount;
		this.data = data;
		this.errors = errors;
		this.receipt = receipt;
	}

	@JsonProperty("code")
	public int getCode() {
		return code;
	}

	@JsonProperty("code")
	public void setCode(int code) {
		this.code = code;
	}

	@JsonProperty("code_message")
	public String getCodeMessage() {
		return codeMessage;
	}

	@JsonProperty("code_message")
	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}

	@JsonProperty("header")
	public EntityRestHeader getHeader() {
		return header;
	}

	@JsonProperty("header")
	public void setHeader(EntityRestHeader header) {
		this.header = header;
	}

	@JsonProperty("data_count")
	public int getDataCount() {
		return dataCount;
	}

	@JsonProperty("data_count")
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}

	@JsonProperty("data")
	public EntityRestData getData() {
		return data;
	}

	@JsonProperty("data")
	public void setData(EntityRestData data) {
		this.data = data;
	}

	@JsonProperty("errors")
	public Object getErrors() {
		return errors;
	}

	@JsonProperty("errors")
	public void setErrors(Object errors) {
		this.errors = errors;
	}

	@JsonProperty("receipt")
	public EntityRestReceipt getReceipt() {
		return receipt;
	}

	@JsonProperty("receipt")
	public void setReceipt(EntityRestReceipt receipt) {
		this.receipt = receipt;
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