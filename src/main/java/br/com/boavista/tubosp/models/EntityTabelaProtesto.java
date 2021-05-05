package br.com.boavista.tubosp.models;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityTabelaProtesto implements Serializable{
	private ChaveComposta composta;
	private Integer tipo_documento;
	private String especie;
	private String uf_cartorio;
	private String data_inclusao;
	private String data_emissao;
	private String data_vencimento;
	private String data_inativacao;
	private String valor_protestado;
	private String retorno_consulta;

}
