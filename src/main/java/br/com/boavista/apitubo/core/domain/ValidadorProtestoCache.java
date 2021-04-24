package br.com.boavista.apitubo.core.domain;

import br.com.boavista.apitubo.infrastructure.Configuracao;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ValidadorProtestoCache implements ProtestoCache {
    private String quantidades;
    private String diasEmCache;
    private String atualizacaoForcada;
    private Integer quantidadeProtesto;
    private String dataBase;
    private long diasDiferenca;

    public ValidadorProtestoCache(Configuracao config){
        this.quantidades = config.getQuantidadeProtestos();
        this.diasEmCache = config.getDiasEmCache();
        this.atualizacaoForcada = config.getAtualizacaoForcada();
    }

	@Override
    public boolean utilizarCache() {
        boolean retorno = false;
        if(this.dataBase == null) {
            return false;
        }
        long diasCorridos = this.calcularDiasCorridos(this.dataBase);

        String[] qtdes = quantidades.split(",");
        String[] diasCache = diasEmCache.split(",");
        String[] atualizacao = atualizacaoForcada.split(",");

        for (int i = 0; i <= qtdes.length; i++) {
            if (this.quantidadeProtesto <= Integer.valueOf(qtdes[i])) {
                if (atualizacao[i].equals("0")) {
                    if (diasCorridos > Integer.valueOf(diasCache[i])) {
                        retorno = true;
                        break;
                    } else {
                        retorno = false;
                        break;
                    }
                } else {
                    retorno = false;
                    break;
                }
            }
        }
        return retorno;
    }

    private long calcularDiasCorridos(String dataBase2) {
    	LocalDate d1 = LocalDate.parse(dataBase2, DateTimeFormatter.ISO_LOCAL_DATE);
    	LocalDate d2 = LocalDate.now();
    	Duration diff = Duration.between(d1.atStartOfDay(), d2.atStartOfDay());
    	long diffDays = diff.toDays();
    	setDiasDiferenca(diffDays);
    	return diffDays;
    }


    @Override
    public void setQuantidadeProtesto(Integer quantidadeProtesto) {
        this.quantidadeProtesto = quantidadeProtesto;
    }

    @Override
    public void setDataBase(String dataBase) {
        this.dataBase = dataBase;
    }
    
    public long getDiasDiferenca() {
		return diasDiferenca;
	}

    public void setDiasDiferenca(long diasDiferenca) {
		this.diasDiferenca = diasDiferenca;
	}
}
