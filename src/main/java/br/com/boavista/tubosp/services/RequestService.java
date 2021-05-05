package br.com.boavista.tubosp.services;

import java.util.List;

import br.com.boavista.tubosp.models.ResumoProtestos;
import br.com.boavista.tubosp.models.Titulo;
import br.com.boavista.tubosp.ports.outbound.ConsultaDetalhada;
import br.com.boavista.tubosp.ports.outbound.ConsultaSimples;

public interface RequestService {
	String consultar(String documento, String tipoDocumento);
	List<ResumoProtestos> fazerConsultaSimples();
	List<Titulo> fazerConsultaDetalhada(List<ResumoProtestos> protestos);
}
