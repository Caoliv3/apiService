package br.com.boavista.apitubo.services;

import br.com.boavista.apitubo.adapters.outbound.ConsultaSoapAdapter;
import br.com.boavista.apitubo.adapters.outbound.ConsultaSoapFactory;
import br.com.boavista.apitubo.adapters.outbound.ProtestoRepository;
import br.com.boavista.apitubo.core.domain.*;
import br.com.boavista.apitubo.core.usecases.ProtestoUseCase;
import br.com.boavista.apitubo.infrastructure.Configuracao;
import br.com.boavista.apitubo.infrastructure.DataSourceConfiguration;
import br.com.boavista.apitubo.ports.ConsultaFactory;
import br.com.boavista.apitubo.ports.inbound.ProtestoRequestPort;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
@Profile("!test")
public class ProtestoService implements ProtestoRequestPort {
    @Value("${service.apitubo.protestos.quantidade}")
    private String quantidades;
    @Value("${service.apitubo.protestos.dias-em-cache}")
    private String diasEmCache;
    @Value("${service.apitubo.protestos.atualizacao-forcada}")
    private String atualizacaoForcada;
    @Value("${service.apitubo.protestos.limite-diario}")
    private String limiteDiario;

    private ProtestoCache cache;
    private ProtestoLimiteDiario excedidoLimiteDiario;
    private ProtestoPort repository;
    private ConsultaPort consultaPort;
    private ConsultaFactory consultaFactory;
    private DetalheProtestos detalheProtestos;
    @Autowired
    private DataSourceConfiguration dataSource;

    private ProtestoUseCase protestoUseCase;

    public void iniciaProtestoService(){
//        ConnectDB db = new ConnectDB();

        this.repository = new ProtestoRepository(dataSource.getConnection());

        this.consultaFactory = new ConsultaSoapFactory();
        consultaPort = new ConsultaSoapAdapter();
        consultaPort.setConsultaFactory(this.consultaFactory);

        Configuracao config = this.getConfig();
        this.cache = new ValidadorProtestoCache(config);
        this.excedidoLimiteDiario = new ValidadorLimiteDiario(this.repository, config);
        this.detalheProtestos = new DetalheProtestos();

        this.protestoUseCase = new ProtestoUseCase(this.cache, this.consultaPort, this.repository, this.excedidoLimiteDiario, this.detalheProtestos);
    }

    public String consultar(String documento, String tipoDocumento){
        this.cache.setDataBase(repository.getDataAtualizacao(documento));
        String retorno = this.protestoUseCase.retornarProtestos(documento, tipoDocumento);
        return retorno;
    }

    private Configuracao getConfig() {
        Configuracao config = Configuracao.builder()
                .quantidadeProtestos(this.quantidades)
                .diasEmCache(this.diasEmCache)
                .atualizacaoForcada(this.atualizacaoForcada)
                .limiteDiario(this.limiteDiario)
                .build();
        return config;
    }
}