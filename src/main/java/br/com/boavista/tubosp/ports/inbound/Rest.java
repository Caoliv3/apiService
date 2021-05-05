package br.com.boavista.tubosp.ports.inbound;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import br.com.boavista.tubosp.models.EntityParameters;
import br.com.boavista.tubosp.models.EntityRestParameters;

public interface Rest {

	@PostMapping(value = "/tubosp")
	public String InitTuboSP(@RequestBody EntityParameters parameters);
	
}
