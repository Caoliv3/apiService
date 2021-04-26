package br.com.boavista.apitubo.adapters.inbound;

import br.com.boavista.apitubo.adapters.outbound.ProtestoRepository;
import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.infrastructure.DataSourceConfigurationTest;
import br.com.boavista.apitubo.models.EntityParameters;
import br.com.boavista.apitubo.models.Protesto;
import br.com.boavista.apitubo.models.Titulo;
import br.com.boavista.apitubo.ports.inbound.ProtestoRequestPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Profile("test")
public class TuboSPControllerTest {

	private String documento = "22643898850";
	private String tipoDocumento = "1";
	@Autowired
	private DataSourceConfigurationTest dataSource;
	private ProtestoPort repository;



	@PostMapping(value = "/apitubo")
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
