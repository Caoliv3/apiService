package br.com.boavista.apitubo.infrastructure;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter  @Builder
public class Configuracao {
    private String quantidadeProtestos;
    private String diasEmCache;
    private String atualizacaoForcada;
    private String limiteDiario;
}
