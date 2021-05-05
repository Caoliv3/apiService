package br.com.boavista.tubosp.ports.outbound;

import java.util.List;

import br.com.boavista.tubosp.models.ConsultaSimplesFiltro;
import br.com.boavista.tubosp.models.ResumoProtestos;

public interface ConsultaSimples {
	List<ResumoProtestos> consultar(ConsultaSimplesFiltro filtro);

}
