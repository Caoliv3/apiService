//package br.com.boavista.tubosp.core.domain;
//
//import java.sql.SQLException;
//
//import br.com.boavista.tubosp.DAO.ConnectDB;
//
//public class ValidacaoQuantidade {
//
//	private Integer quantidadeBanco;
//
//	public String Validar(String cpfcnpj, Integer quantidade)
//			throws SQLException {
//
//		ConnectDB sql = new ConnectDB();
//		quantidadeBanco = sql.listarQuantidadePorDoc(cpfcnpj);
//
//		if (quantidadeBanco == 0) {
//			return "NAO EXISTE REGISTRO NA BASE";
//		} else if (quantidade.equals(quantidadeBanco)) {
//			return "QUANTIDADE DO INSTITUTO IGUAL A BASE";
//		} else if (quantidade != quantidadeBanco) {
//			return "QUANTIDADE DO INSTITUTO DIFERENTE DA BASE";
//		}
//
//		sql.closeConnection();
//
//		return "";
//	}
//
//	public Integer getQuantidadeBanco() {
//		return quantidadeBanco;
//	}
//
//	public void setQuantidadeBanco(Integer quantidadeBanco) {
//		this.quantidadeBanco = quantidadeBanco;
//	}
//}
