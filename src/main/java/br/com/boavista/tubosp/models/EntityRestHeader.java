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
@JsonPropertyOrder({ "api_version", "service", "parameters", "client", "client_name", "token", "token_name", "billable",
		"credits", "has_limit", "limit", "used", "cache_hit", "cached_at", "requested_at",
		"elapsed_time_in_milliseconds" })
public class EntityRestHeader implements Serializable {

	@JsonProperty("api_version")
	private String apiVersion;
	@JsonProperty("service")
	private String service;
	@JsonProperty("parameters")
	private EntityRestParameters parameters;
	@JsonProperty("client")
	private String client;
	@JsonProperty("client_name")
	private String clientName;
	@JsonProperty("token")
	private String token;
	@JsonProperty("token_name")
	private String tokenName;
	@JsonProperty("requested_at")
	private String requestedAt;
	@JsonProperty("elapsed_time_in_milliseconds")
	private int elapsedTimeInMilliseconds;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -6810440025700933181L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public EntityRestHeader() {
		parameters = new EntityRestParameters("",0);
	}

	/**
	 *
	 * @param elapsedTimeInMilliseconds
	 * @param clientName
	 * @param tokenName
	 * @param used
	 * @param billable
	 * @param token
	 * @param apiVersion
	 * @param credits
	 * @param hasLimit
	 * @param requestedAt
	 * @param service
	 * @param limit
	 * @param cacheHit
	 * @param client
	 * @param cachedAt
	 * @param parameters
	 */
	public EntityRestHeader(String apiVersion, String service, EntityRestParameters parameters, String client,
			String clientName, String token, String tokenName,String requestedAt, int elapsedTimeInMilliseconds) {
		super();
		this.apiVersion = apiVersion;
		this.service = service;
		this.parameters = parameters;
		this.client = client;
		this.clientName = clientName;
		this.token = token;
		this.tokenName = tokenName;
		this.requestedAt = requestedAt;
		this.elapsedTimeInMilliseconds = elapsedTimeInMilliseconds;
	}

	@JsonProperty("api_version")
	public String getApiVersion() {
		return apiVersion;
	}

	@JsonProperty("api_version")
	public void setApiVersion(String apiVersion) {
		this.apiVersion = apiVersion;
	}

	@JsonProperty("service")
	public String getService() {
		return service;
	}

	@JsonProperty("service")
	public void setService(String service) {
		this.service = service;
	}

	@JsonProperty("parameters")
	public EntityRestParameters getParameters() {
		return parameters;
	}

	@JsonProperty("parameters")
	public void setParameters(EntityRestParameters parameters) {
		this.parameters = parameters;
	}

	@JsonProperty("client")
	public String getClient() {
		return client;
	}

	@JsonProperty("client")
	public void setClient(String client) {
		this.client = client;
	}

	@JsonProperty("client_name")
	public String getClientName() {
		return clientName;
	}

	@JsonProperty("client_name")
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	@JsonProperty("token")
	public String getToken() {
		return token;
	}

	@JsonProperty("token")
	public void setToken(String token) {
		this.token = token;
	}

	@JsonProperty("token_name")
	public String getTokenName() {
		return tokenName;
	}

	@JsonProperty("token_name")
	public void setTokenName(String tokenName) {
		this.tokenName = tokenName;
	}

	@JsonProperty("requested_at")
	public String getRequestedAt() {
		return requestedAt;
	}

	@JsonProperty("requested_at")
	public void setRequestedAt(String requestedAt) {
		this.requestedAt = requestedAt;
	}

	@JsonProperty("elapsed_time_in_milliseconds")
	public int getElapsedTimeInMilliseconds() {
		return elapsedTimeInMilliseconds;
	}

	@JsonProperty("elapsed_time_in_milliseconds")
	public void setElapsedTimeInMilliseconds(int elapsedTimeInMilliseconds) {
		this.elapsedTimeInMilliseconds = elapsedTimeInMilliseconds;
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
