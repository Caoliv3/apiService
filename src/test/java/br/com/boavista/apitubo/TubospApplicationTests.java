package br.com.boavista.apitubo;

import br.com.boavista.apitubo.adapters.outbound.ConsultaTestAdapter;
import br.com.boavista.apitubo.adapters.outbound.ConsultaTestFactory;
import br.com.boavista.apitubo.adapters.outbound.ProtestoRepository;
import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.infrastructure.DataSourceConfigurationTest;
import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

@SpringBootTest
class TubospApplicationTests {

	@Autowired
	private DataSourceConfigurationTest dataSource;
	private ProtestoPort repository;
	private static ConsultaTestFactory consultaTestFactory;
	private static ConsultaPort consultas;

	@Test
	void contextLoads() {
	}

	@BeforeAll
	public static void init(){
		consultaTestFactory = new ConsultaTestFactory();
		consultas = new ConsultaTestAdapter();
		consultas.setConsultaFactory(consultaTestFactory);
	}

	@Test
	public void QtdeConsultaSimplificada_Igual_QtdeTitulosBase_entaoRetornaJsonBase(){

		ConsultaSimplificadaResponse consultaSimplificada =  consultas.fazerConsultaSimplifcada("43131896884", "1");

		DetalheProtestos detalheProtestos = new DetalheProtestos();
		detalheProtestos.setRecuperadaBase(this.getRepository().recuperarDetalheProtestos("43131896884"));
		detalheProtestos.setSimplificada(consultaSimplificada.getProtestos());

		detalheProtestos.atualizarProstetos("43131896884", "1");

		Assert.isTrue(detalheProtestos.getIncluir().size() == 0, "inclusão indevida");
		Assert.isTrue(detalheProtestos.getBaixar().size() == 0, "baixa indevida");
		Assert.isTrue(detalheProtestos.getDetalhada().size() == 0, "consulta indevida");
	}

	@Test
	public void QtdeConsultaSimplificada_Igual_QtdeTitulosBase_ValorProtestoDiferente(){
		ConsultaSimplificadaResponse consultaSimplificada =  consultas.fazerConsultaSimplifcada("28787126000177", "2");

		DetalheProtestos detalheProtestos = new DetalheProtestos();
		detalheProtestos.setRecuperadaBase(this.getRepository().recuperarDetalheProtestos("28787126000177"));
		detalheProtestos.setSimplificada(consultaSimplificada.getProtestos());

		detalheProtestos.atualizarProstetos("28787126000177", "2");
		ConsultaDetalhadaResponse consultaDetalhada = consultas.fazerConsultaDetalhada(detalheProtestos.getDetalhada());
		detalheProtestos.setIncluir(consultaDetalhada.getTitulos());

		Assert.isTrue(detalheProtestos.getIncluir().size() > 0, "inclusão indevida");
		Assert.isTrue(detalheProtestos.getBaixar().size() > 0, "baixa indevida");
		Assert.isTrue(detalheProtestos.getDetalhada().size() > 0, "consulta indevida");
	}

	@Test
	public void QtdeConsultaSimplificada_Diferente_QtdeTitulosBase_ValorProtestoDiferente() {

		ConsultaSimplificadaResponse consultaSimplificada =  consultas.fazerConsultaSimplifcada("45543915000181", "2");

		DetalheProtestos detalheProtestos = new DetalheProtestos();
		detalheProtestos.setRecuperadaBase(this.getRepository().recuperarDetalheProtestos("45543915000181"));
		detalheProtestos.setSimplificada(consultaSimplificada.getProtestos());

		detalheProtestos.atualizarProstetos("45543915000181", "2");
		ConsultaDetalhadaResponse consultaDetalhada = consultas.fazerConsultaDetalhada(detalheProtestos.getDetalhada());
		detalheProtestos.setIncluir(consultaDetalhada.getTitulos());

		Assert.isTrue(detalheProtestos.getIncluir().size() > 0, "inclusão indevida");
		Assert.isTrue(detalheProtestos.getBaixar().size() > 0, "baixa indevida");
		Assert.isTrue(detalheProtestos.getDetalhada().size() > 0, "consulta indevida");
	}

	public ProtestoPort getRepository(){
		if (this.repository == null){
			this.repository = new ProtestoRepository(dataSource.getConnection());
		}
		return this.repository;
	}
}
