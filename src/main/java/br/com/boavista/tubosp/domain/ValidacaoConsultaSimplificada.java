package br.com.boavista.tubosp.domain;

import java.net.InetAddress;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.boavista.tubosp.DAO.ConnectDB;
import br.com.boavista.tubosp.models.CartorioSOAP;
import br.com.boavista.tubosp.models.ConsultaSOAP;
import br.com.boavista.tubosp.models.EntityErrorsJson;
import br.com.boavista.tubosp.models.EntityRestSP;
import br.com.boavista.tubosp.services.BuildJsonHub;
import br.com.boavista.tubosp.www.ServerRemessaLocator;
import br.com.boavista.tubosp.www.ServerRemessaPortType;

@Slf4j
@Component
public class ValidacaoConsultaSimplificada {

	public String cpfcnpj;
	public String ip;
	public Integer tipodoc;
	public String tipoerro;
	public Integer erroCode;
	@Value("${service.tubo-sp.protestos.limite-diario}")
	public Integer limitediario;
	public String data;
	ConsultaSOAP consulta = new ConsultaSOAP();
	
	public ConsultaSOAP getConsulta() {
		return consulta;
	}

	public void setConsulta(ConsultaSOAP consulta) {
		this.consulta = consulta;
	}
	
	@Autowired
	public AtualizaCache cache;

	public String ValidaExisteProtesto(String tipodoc, String cpfcnpj, String ip)
			throws RemoteException, JAXBException, XMLStreamException, FactoryConfigurationError, JsonProcessingException, ServiceException, SQLException, ClassNotFoundException {
				
		Integer tipodocInt = Integer.parseInt(tipodoc);
		BuildJsonHub json = new BuildJsonHub();
		ConnectDB sql = new ConnectDB();
		sql.Connect();
		Integer quantidadetotal = sql.retornoQtdeTotalPorDoc(cpfcnpj);
		String dataInclusao = sql.listarData(cpfcnpj);		
		data = dataInclusao.replaceAll(" ", "");
		Integer limitediarioBanco = 0; //sql.listarRetornoLimiteDiario();

		try {	
			if(limitediarioBanco <= limitediario) {
			ServerRemessaLocator locator = new ServerRemessaLocator();
			ServerRemessaPortType remessa = locator.getServerRemessaPort();
			consulta = ConsultaSOAP.Xml2Object(remessa.consulta(tipodoc.toString(), cpfcnpj, ip));

		    if(consulta.erro_descricao.equals("NENHUM REGISTRO ENCONTRADO") && consulta.retorno.equals("false")) {
		    	if(quantidadetotal >= 1) {
		    		sql.atualizarCampoInativacaoPorDoc(cpfcnpj);
		    	}
				return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 612);
			}
		    else if(consulta.erro_descricao.contains("IP SEM PERMISSAO PARA CONSUMIR O WS") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }
		    else if(consulta.erro_descricao.contains("CNPJ - DOCUMENTO INVALIDO") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }
		    else if(consulta.erro_descricao.contains("CPF - DOCUMENTO INVALIDO") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }
		    else if(consulta.erro_descricao.contains("LIMITE DIARIO PARA CONSULTA EXCEDIDO") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao + " - ERRO INSTITUTO", 613);
		    }
		    else if(consulta.erro_descricao.contains("FALHA NA AUTENTICACAO") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }
		    else if(consulta.erro_descricao.contains("LIMITE DIARIO PARA CONSULTA NAO AUTORIZADO") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }
		    else if(consulta.erro_descricao.contains("PARAMETROS DE ENTRADA INVALIDOS") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }
		    else if(consulta.erro_descricao.contains("RG OU RNE – DOCUMENTO INVALIDO") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }
		    else if(consulta.erro_descricao.contains("NENHUM TABELIONATO ENCONTRADO") && consulta.retorno.equals("false")) {
		    	return json.BuildJsonError(cpfcnpj, consulta.erro_descricao, 613);
		    }		    		    
		} else {
			String retornojson = sql.validaRetornoConsulta(cpfcnpj);
			
			if(retornojson.equals("")) {
				return json.BuildJsonError(cpfcnpj, "LIMITE DIARIO EXCEDIDO PARA REALIZAR CONSULTAS", 612);
			}
			else {	
				
				if(quantidadetotal >= 1000) {
					return json.BuildJsonError(cpfcnpj, "DOCUMENTO COM MAIS PROTESTOS DO QUE O PERMITIDO", 613);
				}
							
				cache.setQuantidadeProtesto(quantidadetotal);
				cache.setDataBase(data);
				
				
				if(cache.atualizar()) {
					List<EntityRestSP> lista = sql.retornoJsonBanco(cpfcnpj);
					return json.BuildJsonSuccess(cpfcnpj, tipodoc, sql.retornoQtdeTotalPorDoc(cpfcnpj) ,sql.retornoQtdeCartoriosPorDoc(cpfcnpj), lista);
				}
				
				else {
					sql.closeConnection();
					return json.BuildJsonError(cpfcnpj, "LIMITE DIARIO EXCEDIDO PARA REALIZAR CONSULTAS", 612);
				}
				
			}
		}

		} catch (ServiceException service) {
			service.printStackTrace();
				return json.BuildJsonError(cpfcnpj, "ERRO DE COMUNICAÇÃO COM O INSTITUTO", 400);
		}
		return "";
	}
	
}
