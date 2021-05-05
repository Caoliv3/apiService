package br.com.boavista.tubosp.models;

public class EntityRestHeaderErrors {
	public String api_version;
	private String service;
	EntityRestParametersErrors ParametersObject;
	private String client;
	private String client_name;
	private String token;
	private String token_name;
	private boolean billable;
	private Integer credits;
	private boolean has_limit;
	private Integer limit;
	private Integer used;
	private boolean cache_hit;
	private String cached_at = null;
	private String requested_at;
	private Integer elapsed_time_in_milliseconds;

	public EntityRestHeaderErrors(String api_version, String service, EntityRestParametersErrors parameters, String client,
									String client_name, String token, String token_name, boolean billable, Integer credits,
									boolean has_limit, Integer limit, Integer used, Boolean cache_hit, String cached_at,
									String requested_at, Integer elapsed_time_in_milliseconds) {
		
		this.api_version = api_version;
		this.service = service;
		this.ParametersObject = parameters;
		this.client = client;
		this.client_name = client_name;
		this.token = token;
		this.token_name = token_name;
		this.billable = billable;
		this.credits = credits;
		this.has_limit = has_limit;
		this.limit = limit;
		this.used = used;
		this.cache_hit = cache_hit;
		this.cached_at = cached_at;
		this.requested_at = requested_at;
		this.elapsed_time_in_milliseconds = elapsed_time_in_milliseconds;
	}
	
	// Getter Methods

	public String getApi_version() {
		return api_version;
	}

	public String getService() {
		return service;
	}

	public EntityRestParametersErrors getParameters() {
		return ParametersObject;
	}

	public String getClient() {
		return client;
	}

	public String getClient_name() {
		return client_name;
	}

	public String getToken() {
		return token;
	}

	public String getToken_name() {
		return token_name;
	}

	public boolean getBillable() {
		return billable;
	}

	public Integer getCredits() {
		return credits;
	}

	public boolean getHas_limit() {
		return has_limit;
	}

	public Integer getLimit() {
		return limit;
	}

	public Integer getUsed() {
		return used;
	}

	public boolean getCache_hit() {
		return cache_hit;
	}

	public String getCached_at() {
		return cached_at;
	}

	public String getRequested_at() {
		return requested_at;
	}

	public Integer getElapsed_time_in_milliseconds() {
		return elapsed_time_in_milliseconds;
	}

	// Setter Methods

	public void setApi_version(String api_version) {
		this.api_version = api_version;
	}

	public void setService(String service) {
		this.service = service;
	}

	public void setParameters(EntityRestParametersErrors parametersObject) {
		this.ParametersObject = parametersObject;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setToken_name(String token_name) {
		this.token_name = token_name;
	}

	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public void setHas_limit(boolean has_limit) {
		this.has_limit = has_limit;
	}

	public void setLimit(Integer limit) {
		this.limit = limit;
	}

	public void setUsed(Integer used) {
		this.used = used;
	}

	public void setCache_hit(boolean cache_hit) {
		this.cache_hit = cache_hit;
	}

	public void setCached_at(String cached_at) {
		this.cached_at = cached_at;
	}

	public void setRequested_at(String requested_at) {
		this.requested_at = requested_at;
	}

	public void setElapsed_time_in_milliseconds(Integer elapsed_time_in_milliseconds) {
		this.elapsed_time_in_milliseconds = elapsed_time_in_milliseconds;
	}

}
