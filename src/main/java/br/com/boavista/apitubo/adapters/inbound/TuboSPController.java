package br.com.boavista.apitubo.adapters.inbound;

import br.com.boavista.apitubo.models.EntityParameters;
import br.com.boavista.apitubo.ports.inbound.ProtestoRequestPort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Profile("!test")
public class TuboSPController {
	@Autowired
	private ProtestoRequestPort protestoService;

	@PostMapping(value = "/apitubo")
	public String InitTuboSP(@RequestBody EntityParameters parameters) {
		beforeClass();
		String responseJson = null;
		protestoService.iniciaProtestoService();
		responseJson = protestoService.consultar(parameters.getCpfcnpj(), parameters.getTipodoc().toString());
//		if (response.getHttpStatusCode() == 200){
//			responseJson = response.getSuccessResponse();
//		} else {
//			responseJson = response.getErrorResponse();
//		}
		return responseJson;
	}
	
	public static void beforeClass() {
		System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.Log4JLogger");
		System.setProperty("org.apache.commons.logging.LogFactory", "org.apache.commons.logging.impl.LogFactoryImpl");
	}
}
