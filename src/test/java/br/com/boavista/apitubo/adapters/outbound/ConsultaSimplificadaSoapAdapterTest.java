package br.com.boavista.apitubo.adapters.outbound;


import br.com.boavista.apitubo.models.ConsultaSimplificadaFiltro;
import br.com.boavista.apitubo.models.ConsultaSimplificadaResponse;
import br.com.boavista.apitubo.models.Protesto;
import br.com.boavista.apitubo.ports.outbound.ConsultaSimplificada;

public class ConsultaSimplificadaSoapAdapterTest implements ConsultaSimplificada {


    @Override
    public ConsultaSimplificadaResponse consultar(ConsultaSimplificadaFiltro filtro) {
        return null;
    }

    @Override
    public ConsultaSimplificadaResponse consultarTest(ConsultaSimplificadaFiltro filtro) {
        ConsultaSimplificadaResponse resposta = new ConsultaSimplificadaResponse();
        resposta.setRetorno("Retorno sucesso");
        resposta.setSuccess(true);
        resposta.setDataConsulta("06-04-2021");
        resposta.setSituacao("Sucesso");
        resposta.setValorTotalProtestos("1200.00");
        resposta.getProtestos().add(Protesto.builder()
                .tipoDocumento(filtro.getTipoDocumento())
                .documento(filtro.getDocumento())
                .idCartorioBoavista("00996109005")
                .quantidadeProtestos("2")
                .valorProtestado("900.00")
                .build());
        resposta.getProtestos().add(Protesto.builder()
                .tipoDocumento(filtro.getTipoDocumento())
                .documento(filtro.getDocumento())
                .idCartorioBoavista("00996109003")
                .quantidadeProtestos("1")
                .valorProtestado("300.00")
                .build());

        return  resposta;
    }

}


