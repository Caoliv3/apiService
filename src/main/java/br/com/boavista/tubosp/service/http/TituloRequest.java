package br.com.boavista.tubosp.service.http;

import java.util.List;
import java.util.concurrent.Callable;

import br.com.boavista.tubosp.models.ConsultaDetalhadaFiltro;
import br.com.boavista.tubosp.models.Titulo;
import br.com.boavista.tubosp.ports.outbound.ConsultaDetalhada;

public class TituloRequest implements Callable<List<Titulo>> {
	private final ConsultaDetalhada consultaDetalhada;
	private final ConsultaDetalhadaFiltro consultaDetalhadaFiltro;
	
	public TituloRequest(ConsultaDetalhada consultaDetalhada, ConsultaDetalhadaFiltro filtro) {
		this.consultaDetalhada = consultaDetalhada;
		this.consultaDetalhadaFiltro = filtro;
	}
	
	@Override
	public List<Titulo> call() {

		return this.consultaDetalhada.consultar(this.consultaDetalhadaFiltro);
	}
}