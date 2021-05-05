package br.com.boavista.tubosp.models;

public class EntityRestParametersErrors {
	private String cpfcnpj;

	public EntityRestParametersErrors(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}
	// Getter Methods

	public String getCnpj() {
		return cpfcnpj;
	}

	// Setter Methods

	public void setCnpj(String cpfcnpj) {
		this.cpfcnpj = cpfcnpj;
	}

}
