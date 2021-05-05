package br.com.boavista.tubosp.models;

public class ConsultaSimplificadaModel {
	public String documento;
	
	public String id_cartorio_boavista;
	
	public String protestos;

	public String getDocumento() {
		return documento;
	}
	
	public ConsultaSimplificadaModel(String documento, String id_cartorio_boavista, String protestos) {
		this.documento = documento;
		this.id_cartorio_boavista = id_cartorio_boavista;
		this.protestos = protestos;
	}

	public void setDocumento(String documento) {
		this.documento = documento;
	}

	public String getId_cartorio_boavista() {
		return id_cartorio_boavista;
	}

	public void setId_cartorio_boavista(String id_cartorio_boavista) {
		this.id_cartorio_boavista = id_cartorio_boavista;
	}

	public String getProtestos() {
		return protestos;
	}

	public void setProtestos(String protestos) {
		this.protestos = protestos;
	}

}
