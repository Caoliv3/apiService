package br.com.boavista.tubosp.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResumoProtestos implements Serializable{
	private static final long serialVersionUID = 6647787560432926704L;
	public String tipo_documento;
	public String documento;
	public String id_cartorio_boavista;
	public String protestos;
}

