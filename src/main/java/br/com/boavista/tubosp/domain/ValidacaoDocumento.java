package br.com.boavista.tubosp.domain;

import java.rmi.RemoteException;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.boavista.tubosp.services.BuildJsonHub;

public class ValidacaoDocumento {

	public String Validar(String tipodoc, String cpfcnpj) throws JsonProcessingException, RemoteException, ServiceException, JAXBException, XMLStreamException, FactoryConfigurationError {
		Integer tipodocInt = Integer.parseInt(tipodoc);
		Integer cpfcnpjNumero = cpfcnpj.length();
		BuildJsonHub json = new BuildJsonHub();
		String retorno = "";
		
		
		if(tipodocInt.equals(1)) {
			if(!cpfcnpjNumero.equals(11)) 
				retorno = json.BuildJsonError(cpfcnpj, "CPF - DOCUMENTO INVALIDO", 613);
		}
		
		else if(tipodocInt.equals(2)) {
			if(!cpfcnpjNumero.equals(14)) 
				retorno = json.BuildJsonError(cpfcnpj, "CNPJ - DOCUMENTO INVÁLIDO", 613);
		}
		
		
		return retorno;
	}

}
