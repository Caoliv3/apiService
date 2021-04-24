package br.com.boavista.apitubo.ports.inbound;

public interface ProtestoRequestPort {
    String consultar(String documento, String tipoDocumento);
    void iniciaProtestoService();
}
