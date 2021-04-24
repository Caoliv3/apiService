package br.com.boavista.apitubo.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Auditoria {

    public int quantidadeConsulta;
    private String idAuditoria;
    private String idSimplificada;
    private String idDetalhada;
    private String jsonSimplificada;
    private String jsonDetalhada;
    private String jsonAuditoria;
    private int tipoDocumento;
    private String documento;
    private int fonteConsulta;
}
