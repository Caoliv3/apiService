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
@JsonPropertyOrder({ "SP" })
public class EntityRestCartorios implements Serializable {

	@JsonProperty("SP")
	private List<EntityRestSP> sP = new ArrayList<EntityRestSP>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = 4869563739972673285L;

/**
* No args constructor for use in serialization
*
*/
public EntityRestCartorios() {
	
}

/**
*
* @param sP
*/
public EntityRestCartorios(List<EntityRestSP> sP) {
super();
this.sP = sP;
}

	@JsonProperty("SP")
	public List<EntityRestSP> getSP() {
		return sP;
	}

	@JsonProperty("SP")
	public void setSP(List<EntityRestSP> sP) {
		this.sP = sP;
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