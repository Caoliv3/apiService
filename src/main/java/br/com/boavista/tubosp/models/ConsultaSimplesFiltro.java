package br.com.boavista.tubosp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaSimplesFiltro {
	private String documento;
	private String tipoDocumento;
}

