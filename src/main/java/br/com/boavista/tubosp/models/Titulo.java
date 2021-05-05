package br.com.boavista.tubosp.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Titulo implements Serializable {
	private static final long serialVersionUID = 5882586700709970844L;
	public String cod_cartorio;
	public String tipo_documento_devedor;
	public String documento_devedor;
	public String nome_devedor;
	public String endereco_devedor;
	public String cep_devedor;
	public String bairro_devedor;
	public String cidade_devedor;
	public String uf_devedor;
	public String livro_protesto;
	public String folha_protesto;
	public String protocolo;
	public String data_protocolo;
	public String especie;
	public String numero_titulo;
	public String data_emissao;
	public String data_vencimento;
	public String data_protesto;
	public String valor_original;
	public String valor_protestado;
	public String erro_metodo;
	public String erro_metodo_descricao;
}

