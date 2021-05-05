package br.com.boavista.tubosp.models;

public class EntityBodyJson {
	public String client = "Boa Vista Servicos S/A";
	public String client_name = "Boa Vista Servicos S/A";
	public String token = "lMv4VxBbhQ...";
	public String token_name = System.getenv("TOKEN_NAME");
	public boolean billable = true;
	public Integer credits = 2;
	public boolean has_limit = false;
	public Integer limit = 10000;
	public Integer used = 556164;
	public boolean cache_hit = false;
	public String cached_at = null;
	public String requested_at = "";
	public Integer elapsed_time_in_milliseconds = 0;
	public String url = System.getenv("URL_ENDPOINT");
	public String id = "441470385";
	public String key = "wMB9AumTDcMv6nHLFf1Ph_1thZuncNJpjoTpo9X6";
	public String sites_urls = null;
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getClient_name() {
		return client_name;
	}
	public void setClient_name(String client_name) {
		this.client_name = client_name;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getToken_name() {
		return token_name;
	}
	public void setToken_name(String token_name) {
		this.token_name = token_name;
	}
	public boolean isBillable() {
		return billable;
	}
	public void setBillable(boolean billable) {
		this.billable = billable;
	}
	public Integer getCredits() {
		return credits;
	}
	public void setCredits(Integer credits) {
		this.credits = credits;
	}
	public boolean isHas_limit() {
		return has_limit;
	}
	public void setHas_limit(boolean has_limit) {
		this.has_limit = has_limit;
	}
	public Integer getLimit() {
		return limit;
	}
	public void setLimit(Integer limit) {
		this.limit = limit;
	}
	public Integer getUsed() {
		return used;
	}
	public void setUsed(Integer used) {
		this.used = used;
	}
	public boolean isCache_hit() {
		return cache_hit;
	}
	public void setCache_hit(boolean cache_hit) {
		this.cache_hit = cache_hit;
	}
	public String getCached_at() {
		return cached_at;
	}
	public void setCached_at(String cached_at) {
		this.cached_at = cached_at;
	}
	public String getRequested_at() {
		return requested_at;
	}
	public void setRequested_at(String requested_at) {
		this.requested_at = requested_at;
	}
	public Integer getElapsed_time_in_milliseconds() {
		return elapsed_time_in_milliseconds;
	}
	public void setElapsed_time_in_milliseconds(Integer elapsed_time_in_milliseconds) {
		this.elapsed_time_in_milliseconds = elapsed_time_in_milliseconds;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getSites_urls() {
		return sites_urls;
	}
	public void setSites_urls(String sites_urls) {
		this.sites_urls = sites_urls;
	}

}
