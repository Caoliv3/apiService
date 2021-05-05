package br.com.boavista.tubosp.domain;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

 

@Component
public class AtualizaCacheBaseProtesto implements AtualizaCache {
    @Value("${service.tubo-sp.protestos.quantidade}")
    private String quantidades; //= "1,4,10,20,50,51";
    @Value("${service.tubo-sp.protestos.dias-em-cache}")
    private String diasEmCache; //= "2,7,10,15,25,30";   
    @Value("${service.tubo-sp.protestos.atualizacao-forcada}")
    private String atualizacaoForcada; //= "1,1,1,1,0,0";


    private Integer quantidadeProtesto;
    private String dataBase;
    private long diasDiferenca;

	@Override
    public boolean atualizar() {
        boolean retorno = false;
        long diasCorridos = this.calcularDiasCorridos(this.dataBase);

        String[] qtdes = quantidades.split(",");
        String[] diasCache = diasEmCache.split(",");
        String[] atualizacao = atualizacaoForcada.split(",");
        
     //   if(quantidadeProtesto >= 1000) {
     //   	return false;
     //   }

        for (int i = 0; i < qtdes.length; i++) {
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
                    retorno = true;
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
