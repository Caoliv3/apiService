package br.com.boavista.tubosp.models;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntityViewTabelaProtesto implements Serializable{
	private String documento;
	private String id_codigo_cartorio;
	private Integer quantidade;
}
