package br.com.boavista.tubosp.adapters.inbound;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import br.com.boavista.tubosp.models.EntityParameters;
import br.com.boavista.tubosp.models.EntityRestParameters;
import br.com.boavista.tubosp.models.Titulo;
import br.com.boavista.tubosp.ports.inbound.Rest;
import br.com.boavista.tubosp.services.RequestService;

@RestController
public class TuboSPRestAdapter implements Rest {
	
	@Autowired
	private RequestService protestoService;	

	@Override
	public String InitTuboSP(EntityParameters parameters) {
		beforeClass();
		String response = protestoService.consultar(parameters.getCpfcnpj(), parameters.getTipodoc().toString());
		return response;
	}
	
	public static void beforeClass() {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Log4JLogger");
		System.setProperty("org.apache.commons.logging.LogFactory", "org.apache.commons.logging.impl.LogFactoryImpl");
	}

}
