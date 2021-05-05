package br.com.boavista.tubosp.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsultaDetalhadaFiltro {
	private String tipoDocumento;
	private String documento;
	private String codigoMunicipio;
	private String numeroCartorio;
	private String numeroIp;
	private String metaDados;
	
	public static ConsultaDetalhadaFiltro buildFromProtesto(ResumoProtestos protesto) {
		ConsultaDetalhadaFiltro filtro = new ConsultaDetalhadaFiltro();
		filtro.setTipoDocumento(protesto.getTipo_documento());
		filtro.setDocumento(protesto.getDocumento());
		filtro.setNumeroCartorio(protesto.getId_cartorio_boavista());
		
		return filtro;
	}
}
