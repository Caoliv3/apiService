package br.com.boavista.tubosp.adapters.outbound;

import br.com.boavista.tubosp.models.CartorioSOAP;
import br.com.boavista.tubosp.models.ConsultaSOAP;
import br.com.boavista.tubosp.models.ConsultaSimplesFiltro;
import br.com.boavista.tubosp.models.ResumoProtestos;
import br.com.boavista.tubosp.ports.outbound.ConsultaSimples;
import br.com.boavista.tubosp.www.ServerRemessaLocator;
import br.com.boavista.tubosp.www.ServerRemessaPortType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConsultaSimplesSoapAdapter implements ConsultaSimples {
	@Setter private List<ResumoProtestos> listaResumoProtestos;
	
	@Override
	public List<ResumoProtestos> consultar(ConsultaSimplesFiltro filtro) {
		ConsultaSOAP consulta = new ConsultaSOAP();
		List<ResumoProtestos> resposta = new ArrayList<ResumoProtestos>();
		String ip = null;

		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			ServerRemessaLocator locator = new ServerRemessaLocator();
			ServerRemessaPortType remessa = locator.getServerRemessaPort();
			consulta = ConsultaSOAP.Xml2Object(remessa.consulta(filtro.getTipoDocumento(), filtro.getDocumento(), ip));
			log.info("Cartórios retornados {}.", consulta.conteudo.size());
			log.info("Cartórios retornados {}.", consulta.conteudo.toString());
			int qtdeProtestos = 0;
			for (CartorioSOAP car :consulta.conteudo) {
				resposta.add(new ResumoProtestos(filtro.getTipoDocumento(), filtro.getDocumento(), car.id_cartorio_boavista, car.protestos));
				qtdeProtestos +=  Integer.valueOf(car.protestos);
				log.info("Simplificada -> Doc: {} {} - Cartorio: {} " , filtro.getTipoDocumento() , filtro.getDocumento() , car.toString());
			}
			log.info("Total de Protestos Doc:{}, {} protestos.", filtro.getDocumento(), qtdeProtestos);
		}

		catch(Exception e) {
			e.printStackTrace();
		}

		return resposta;
	}

}
