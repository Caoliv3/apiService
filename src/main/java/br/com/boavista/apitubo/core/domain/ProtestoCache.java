package br.com.boavista.apitubo.core.domain;

public interface ProtestoCache {
      void setQuantidadeProtesto(Integer quantidadeProtesto);
      void setDataBase(String data);
      boolean utilizarCache();
      long getDiasDiferenca();

}