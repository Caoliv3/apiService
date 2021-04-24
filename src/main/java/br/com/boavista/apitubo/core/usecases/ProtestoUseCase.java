package br.com.boavista.apitubo.core.usecases;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.core.domain.ProtestoCache;
import br.com.boavista.apitubo.core.domain.ProtestoLimiteDiario;
import br.com.boavista.apitubo.core.domain.Validador;
import br.com.boavista.apitubo.models.Auditoria;
import br.com.boavista.apitubo.models.ConsultaDetalhadaResponse;
import br.com.boavista.apitubo.models.ConsultaSimplificadaResponse;
import br.com.boavista.apitubo.models.ErrorResponse;
import br.com.boavista.apitubo.ports.outbound.ConsultaPort;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;

public class ProtestoUseCase {
    private ProtestoCache cache;
    private ConsultaPort consultas;
    private ProtestoPort baseProtestos;
    private DetalheProtestos detalheProtestos;
    private ProtestoLimiteDiario limiteDiario;
    private Auditoria auditoria = new Auditoria();


    public ProtestoUseCase(ProtestoCache cache, ConsultaPort consultas, ProtestoPort baseProtestos, ProtestoLimiteDiario limiteDiario, DetalheProtestos detalheProtestos){
        this.cache = cache;
        this.consultas = consultas;
        this.baseProtestos = baseProtestos;
        this.limiteDiario = limiteDiario;
        this.detalheProtestos = detalheProtestos;
    }

    public String retornarProtestos(String documento, String tipoDocumento){

        String retorno = null;
        if (Validador.ehDocumentoValido(documento, tipoDocumento)){
            auditoria.setTipoDocumento(Integer.valueOf(tipoDocumento));
            auditoria.setDocumento(documento);
            auditoria.setIdAuditoria(detalheProtestos.getId());
            this.cache.setQuantidadeProtesto(baseProtestos.getQtdadeProtestoDocumento(documento));
            if (this.cache.utilizarCache()){
                detalheProtestos.setRecuperadaBase(this.baseProtestos.recuperarDetalheProtestos(documento));
                retorno = detalheProtestos.getJson();
                auditoria.setFonteConsulta(1);
                auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
                baseProtestos.with(auditoria).salvar();
            } else {
                if (limiteDiario.excedeuLimiteDiario()) {
                    auditoria.setFonteConsulta(4);
                    retorno = baseProtestos.recuperarJsonConsultaDetalhada(documento);
                    auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
                    baseProtestos.with(auditoria).salvar();
                } else {
                    retorno = getConsulta(documento, tipoDocumento);
                }
            }
        } else {
            ErrorResponse error = ErrorResponse.builder()
                    .codigo(612)
                    .descricao("DOCUMENTO INVALIDO")
                    .documento(documento)
                    .tipoDocumento(tipoDocumento)
                    .build();
            retorno = detalheProtestos.getErrorJson(error);
        }
        return retorno;
    }

    public String getConsulta(String documento, String tipoDocumento){

        String json = null;
        ConsultaSimplificadaResponse consultaSimplificada =  consultas.fazerConsultaSimplifcada(documento, tipoDocumento);
        auditoria.setFonteConsulta(2);
        auditoria.setIdSimplificada(detalheProtestos.getId());
        auditoria.setJsonSimplificada(detalheProtestos.getJsonSimplificada(consultaSimplificada));
        detalheProtestos.setRecuperadaBase(this.baseProtestos.recuperarDetalheProtestos(documento));
        if (consultaSimplificada.isSuccess()){
            auditoria.setIdSimplificada(detalheProtestos.getId());
            detalheProtestos.setSimplificada(consultaSimplificada.getProtestos());
            detalheProtestos.atualizarProstetos(documento, tipoDocumento);
            if (detalheProtestos.getDetalhada().size() > 0) {
                json = getConsultaDetalhada();
            } else {
                json = detalheProtestos.getJson();
                auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
                this.baseProtestos.with(auditoria).salvar(detalheProtestos);
            }
        } else {
            json = detalheProtestos.getJson();
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
            this.baseProtestos.with(auditoria).salvar();
        }
        return json;
    }

    public String getConsultaDetalhada (){

        String json;
        auditoria.setFonteConsulta(3);
        auditoria.setIdDetalhada(detalheProtestos.getId());
        auditoria.setQuantidadeConsulta(detalheProtestos.getDetalhada().size());
        ConsultaDetalhadaResponse consultaDetalhada = consultas.fazerConsultaDetalhadaTest();
        if(consultaDetalhada.isSuccess()) {
            detalheProtestos.setIncluir(consultaDetalhada.getTitulos());
            json = detalheProtestos.getJson();
            auditoria.setJsonDetalhada(json);
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
            this.baseProtestos.with(auditoria).salvar(detalheProtestos);
        } else {
            json = detalheProtestos.getErrorJson(consultaDetalhada.getError());
            auditoria.setJsonDetalhada(json);
            auditoria.setJsonAuditoria(detalheProtestos.getJsonAuditoria());
        }
        return json;
    }
}
