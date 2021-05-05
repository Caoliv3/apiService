package br.com.boavista.tubosp.ports.outbound;

import java.util.List;

import br.com.boavista.tubosp.models.ConsultaDetalhadaFiltro;
import br.com.boavista.tubosp.models.Titulo;

public interface ConsultaDetalhada {
	List<Titulo> consultar(ConsultaDetalhadaFiltro filtro);
}
