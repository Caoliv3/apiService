package br.com.boavista.tubosp.domain;

import java.util.Date;

public interface AtualizaCache {
      void setQuantidadeProtesto(Integer quantidadeProtesto);
      void setDataBase(String data);
      boolean atualizar();
      long getDiasDiferenca();
}