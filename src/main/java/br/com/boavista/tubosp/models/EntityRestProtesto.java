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
@JsonPropertyOrder({ "cpf_cnpj", "data_protesto", "data_vencimento", "valor_string", "uf_cartorio", "especie"})
public class EntityRestProtesto implements Serializable {

	@JsonProperty("cpf_cnpj")
	private String cpfCnpj;
	@JsonProperty("data_protesto")
	private String dataProtesto;
	@JsonProperty("data_vencimento")
	private String dataVencimento;
	@JsonProperty("valor_string")
	private String valor_string;
	@JsonProperty("uf_cartorio")
	private String uf_cartorio;
	@JsonProperty("especie")
	private String especie;
	@JsonIgnore
	private Map<String, Object> additionalProperties = new HashMap<String, Object>();
	private final static long serialVersionUID = -1320802279551200566L;

	/**
	 * No args constructor for use in serialization
	 *
	 */
	public EntityRestProtesto() {
	}

	/**
	 *
	 * @param nomeApresentante
	 * @param nomeCedente
	 * @param data
	 * @param valorString
	 * @param dataVencimento
	 * @param temAnuencia
	 * @param dataProtesto
	 * @param valor
	 * @param chave
	 * @param dataProtestoString
	 * @param cpfCnpj
	 * @param dataVencimentoString
	 */
	public EntityRestProtesto(String cpfCnpj, String dataProtesto, String dataVencimento,String valor, String uf_cartorio, String especie) {
		super();
		this.cpfCnpj = cpfCnpj;
		this.dataProtesto = dataProtesto;
		this.dataVencimento = dataVencimento;
		this.valor_string = valor;
		this.uf_cartorio = uf_cartorio;
		this.especie = especie;
	}

	@JsonProperty("cpf_cnpj")
	public String getCpfCnpj() {
		return cpfCnpj;
	}

	@JsonProperty("cpf_cnpj")
	public void setCpfCnpj(String cpfCnpj) {
		this.cpfCnpj = cpfCnpj;
	}

	@JsonProperty("data_protesto")
	public String getDataProtesto() {
		return dataProtesto;
	}

	@JsonProperty("data_protesto")
	public void setDataProtesto(String dataProtesto) {
		this.dataProtesto = dataProtesto;
	}

	@JsonProperty("data_vencimento")
	public String getDataVencimento() {
		return dataVencimento;
	}

	@JsonProperty("data_vencimento")
	public void setDataVencimento(String dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	@JsonProperty("valor_string")
	public String getValor() {
		return valor_string;
	}

	@JsonProperty("valor_string")
	public void setValor(String valor) {
		this.valor_string = valor;
	}
	
	@JsonProperty("uf_cartorio")
	public String getUf_cartorio() {
		return uf_cartorio;
	}

	@JsonProperty("uf_cartorio")
	public void setUf_cartorio(String uf_cartorio) {
		this.uf_cartorio = uf_cartorio;
	}
	
	@JsonProperty("especie")
	public String getEspecie() {
		return especie;
	}

	@JsonProperty("especie")
	public void setEspecie(String especie) {
		this.especie = especie;
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