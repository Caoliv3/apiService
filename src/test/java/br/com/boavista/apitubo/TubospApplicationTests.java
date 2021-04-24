package br.com.boavista.apitubo;

import br.com.boavista.apitubo.adapters.outbound.ProtestoRepository;
import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.infrastructure.DataSourceConfiguration;
import br.com.boavista.apitubo.infrastructure.DataSourceConfigurationTest;
import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class TubospApplicationTests {

	private String documento;
	private String tipoDocumento;
	@Autowired
	private DataSourceConfigurationTest dataSource;
	private ProtestoPort repository;

	@Test
	void contextLoads() {
	}

	@Test
	public void dadoConsultaSimples_quandoNaoHouverAlteracoes_entaoRetornaJsonBase(){
		List<Titulo> titulosBase = new ArrayList<>();
		List<Protesto> simplificada = new ArrayList<>();
		this.repository = new ProtestoRepository(dataSource.getConnection());

		DetalheProtestos detalheProtestos = new DetalheProtestos();
		detalheProtestos.setRecuperadaBase(repository.recuperarDetalheProtestos(this.documento));
		detalheProtestos.setSimplificada(simplificada);

		detalheProtestos.atualizarProstetos(this.documento, this.tipoDocumento);

		Assert.isTrue(detalheProtestos.getIncluir().size() == 0, "inclusão indevida");
		Assert.isTrue(detalheProtestos.getBaixar().size() == 0, "baixa indevida");
		Assert.isTrue(detalheProtestos.getDetalhada().size() == 0, "consulta indevida");

	}

}
