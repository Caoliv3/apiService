package br.com.boavista.tubosp.adapters.outbound;

import br.com.boavista.tubosp.models.*;
import br.com.boavista.tubosp.ports.outbound.ConsultaDetalhada;
import br.com.boavista.tubosp.www.ServerRemessaLocator;
import br.com.boavista.tubosp.www.ServerRemessaPortType;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ConsultaDetalhadaSoapAdapter implements ConsultaDetalhada {
	public CenprotSOAP cenprotSOAP;
	private ObjectMapper mapper = new ObjectMapper();
	@Setter private List<ResumoProtestos> listaResumoProtestos;
	private String erro_metodo = null;
	private String erro_metodo_descricao = null;
	
	@Override
	public List<Titulo> consultar(ConsultaDetalhadaFiltro filtro) {	
		List<Titulo> resposta = new ArrayList<Titulo>();
		String ip = null;
		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	

		ServerRemessaLocator locator = new ServerRemessaLocator();
		ServerRemessaPortType remessa = null;
		
		try {
			remessa = locator.getServerRemessaPort();
		} catch (ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			try {
				log.info("Iniciando consulta detalhada {}", Instant.now());
				cenprotSOAP = CenprotSOAP.Xml2Object(remessa.consultaDetalhesBiro(filtro.getTipoDocumento(), filtro.getDocumento(), "", "", ip, "boavista;" + filtro.getNumeroCartorio()));
				log.info("Consulta detalhada finalizada {}", Instant.now());
			} catch (JAXBException | XMLStreamException | FactoryConfigurationError e) {
				log.info("Consulta detalhada Erro {}: {}", Instant.now(), e.getMessage());
			}
		} catch (RemoteException e) {
			log.info("Consulta detalhada Erro {}: {}", Instant.now(), e.getMessage());
		}

			for (ConsultaDetalhadaSOAP consultaDetalhes : cenprotSOAP.consultaDetalhes) {
				for (TitulosSOAP titulos : consultaDetalhes.titulos) {
					resposta.add(new Titulo(filtro.getNumeroCartorio(), filtro.getTipoDocumento(), filtro.getDocumento(), titulos.nome_devedor, titulos.endereco_devedor, titulos.cep_devedor,
							titulos.bairro_devedor, titulos.cidade_devedor, titulos.uf_devedor, titulos.livro_protesto, titulos.folha_protesto, titulos.protocolo, 
							titulos.data_protocolo, titulos.especie, titulos.numero_titulo, titulos.data_emissao, titulos.data_vencimento, titulos.data_protesto, titulos.valor_original, titulos.valor_protestado,consultaDetalhes.erro_metodo,consultaDetalhes.erro_metodo_descricao));
					log.info("Detalhada -> Doc: {} {} - Carotorio: {}" ,filtro.getTipoDocumento(), filtro.getDocumento() ,  titulos);
				}
			}
			return resposta;
		}

}