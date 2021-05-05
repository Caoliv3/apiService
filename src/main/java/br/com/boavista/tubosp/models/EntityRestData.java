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
@JsonPropertyOrder({ "cartorios", "consulta_data", "consulta_datahora", "documento_pesquisado", "quantidade_titulos" })
public class EntityRestData implements Serializable {

	@JsonProperty("cartorios")
	private EntityRestCartorios cartorios;
	@JsonProperty("consulta_data")
	private String consultaData;
	@JsonProperty("consulta_datahora")
	private String consultaDatahora;
	@JsonProperty("documento_pesquisado")
	private String documentoPesquisado;
	@JsonProperty("quantidade_titulos")
	private int quantidadeTitulos;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -6468665173601870840L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public EntityRestData() {
 
	}

	/**
	 *
	 * @param consultaData
	 * @param quantidadeTitulos
	 * @param cartorios
	 * @param consultaDatahora
	 * @param documentoPesquisado
	 */
	public EntityRestData(EntityRestCartorios cartorios, String consultaData, String consultaDatahora, String documentoPesquisado,
			int quantidadeTitulos) {
		super();
		this.cartorios = cartorios;
		this.consultaData = consultaData;
		this.consultaDatahora = consultaDatahora;
		this.documentoPesquisado = documentoPesquisado;
		this.quantidadeTitulos = quantidadeTitulos;	
	}

	@JsonProperty("consulta_data")
	public String getConsultaData() {
		return consultaData;
	}

	@JsonProperty("consulta_data")
	public void setConsultaData(String consultaData) {
		this.consultaData = consultaData;
	}

	@JsonProperty("consulta_datahora")
	public String getConsultaDatahora() {
		return consultaDatahora;
	}

	@JsonProperty("consulta_datahora")
	public void setConsultaDatahora(String consultaDatahora) {
		this.consultaDatahora = consultaDatahora;
	}

	@JsonProperty("documento_pesquisado")
	public String getDocumentoPesquisado() {
		return documentoPesquisado;
	}

	@JsonProperty("documento_pesquisado")
	public void setDocumentoPesquisado(String documentoPesquisado) {
		this.documentoPesquisado = documentoPesquisado;
	}

	@JsonProperty("quantidade_titulos")
	public int getQuantidadeTitulos() {
		return quantidadeTitulos;
	}

	@JsonProperty("quantidade_titulos")
	public void setQuantidadeTitulos(int quantidadeTitulos) {
		this.quantidadeTitulos = quantidadeTitulos;
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