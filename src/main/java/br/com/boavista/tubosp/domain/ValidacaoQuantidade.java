package br.com.boavista.tubosp.domain;

import java.sql.SQLException;
import java.util.List;

import br.com.boavista.tubosp.DAO.ConnectDB;
import br.com.boavista.tubosp.models.EntityViewTabelaProtesto;

public class ValidacaoQuantidade {

	private Integer quantidadeBanco;

	public String Validar(String cpfcnpj, Integer quantidade)
			throws ClassNotFoundException, SQLException {

		ConnectDB sql = new ConnectDB();
		sql.Connect();
		quantidadeBanco = sql.listarQuantidadePorDoc(cpfcnpj);

		if (quantidadeBanco == 0) {
			return "NAO EXISTE REGISTRO NA BASE";
		} else if (quantidade.equals(quantidadeBanco)) {
			return "QUANTIDADE DO INSTITUTO IGUAL A BASE";
		} else if (quantidade != quantidadeBanco) {
			return "QUANTIDADE DO INSTITUTO DIFERENTE DA BASE";
		}

		sql.closeConnection();

		return "";
	}

	public Integer getQuantidadeBanco() {
		return quantidadeBanco;
	}

	public void setQuantidadeBanco(Integer quantidadeBanco) {
		this.quantidadeBanco = quantidadeBanco;
	}
}
