package br.com.boavista.apitubo.ports.outbound;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.models.Auditoria;
import br.com.boavista.apitubo.models.Titulo;

import java.util.List;

public interface ProtestoPort {
    abstract List<Titulo> recuperarDetalheProtestos(String documento);

    abstract String recuperarJsonConsultaDetalhada(String documento);

    ProtestoPort with(Auditoria auditoria);

    ProtestoPort salvar();

    ProtestoPort salvar(DetalheProtestos detalheProtestos);

    int getQtdadeProtestoDocumento(String documento);

    abstract String getDataAtualizacao(String documento);

    int getQtdadeConsultas();
}
