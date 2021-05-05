package br.com.boavista.tubosp.models;

import java.io.Serializable;
import java.util.ArrayList;
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
@JsonPropertyOrder({ "codigo","atualizacao_data", "quantidade","protestos" })
public class EntityRestSP implements Serializable {

	@JsonProperty("codigo")
	private String codigo;
	@JsonProperty("atualizacao_data")
	private String atualizacaoData;
	@JsonProperty("quantidade")
	private int quantidade;
	@JsonProperty("protestos")
	private List<EntityRestProtesto> protestos = new ArrayList<EntityRestProtesto>();
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = 5389286640915870597L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public EntityRestSP() {
	
	}

	/**
	 *
	 * @param codigo
	 * @param telefone
	 * @param cidade
	 * @param protestos
	 * @param endereco
	 * @param municipio
	 * @param bairro
	 * @param nome
	 * @param cidadeCodigoIbge
	 * @param atualizacaoData
	 * @param cidadeCodigo
	 * @param periodoPesquisa
	 * @param quantidade
	 */
	public EntityRestSP(String codigo, String atualizacaoData, int quantidade, List<EntityRestProtesto> protestos) {
		super();
		this.codigo = codigo;
		this.atualizacaoData = atualizacaoData;
		this.quantidade = quantidade;
		this.protestos = protestos;
	}

	@JsonProperty("codigo")
	public String getCodigo() {
		return codigo;
	}

	@JsonProperty("codigo")
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	@JsonProperty("atualizacao_data")
	public String getAtualizacaoData() {
		return atualizacaoData;
	}

	@JsonProperty("atualizacao_data")
	public void setAtualizacaoData(String atualizacaoData) {
		this.atualizacaoData = atualizacaoData;
	}

	@JsonProperty("quantidade")
	public int getQuantidade() {
		return quantidade;
	}

	@JsonProperty("quantidade")
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	@JsonProperty("protestos")
	public List<EntityRestProtesto> getProtestos() {
		return protestos;
	}

	@JsonProperty("protestos")
	public void setProtestos(List<EntityRestProtesto> protestos) {
		this.protestos = protestos;
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