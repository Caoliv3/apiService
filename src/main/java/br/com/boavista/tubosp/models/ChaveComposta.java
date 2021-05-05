package br.com.boavista.tubosp.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChaveComposta implements Serializable{
	private String id_codigo_cartorio;
	private String documento;
	private String datahora ;
	private String identificador;
}
