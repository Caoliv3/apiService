package br.com.boavista.apitubo.ports.outbound;

import br.com.boavista.apitubo.models.ConsultaDetalhadaFiltro;
import br.com.boavista.apitubo.models.Titulo;

import java.util.List;

public interface ConsultaDetalhada {
	List<Titulo> consultar(ConsultaDetalhadaFiltro filtro);
}
