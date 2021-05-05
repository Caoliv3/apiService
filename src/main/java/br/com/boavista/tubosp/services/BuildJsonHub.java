package br.com.boavista.tubosp.services;

import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.boavista.tubosp.models.*;
import br.com.boavista.tubosp.www.ServerRemessaLocator;
import br.com.boavista.tubosp.www.ServerRemessaPortType;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BuildJsonHub {
	public ServerRemessaLocator locator;
	public ServerRemessaPortType remessa;
	public ConsultaSOAP consulta;
	public ConsultaDetalhadaSOAP consultaDetalhada;
	public CenprotSOAP cenprot;
	public TitulosSOAP titulossoap;
	public String data;

	public String BuildJsonSuccess(String documento, String tipodoc, Integer quantidade, Integer quantidadeCartorios, List<EntityRestSP> listsp ) throws JsonProcessingException,
			ServiceException, RemoteException, JAXBException, XMLStreamException, FactoryConfigurationError {
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);

		Integer tipodocInt = Integer.parseInt(tipodoc);
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat formaterdatehour = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String dateFormated = formater.format(date);
		String dateHourFormated = formaterdatehour.format(date);

		EntityRestJson entityjson = new EntityRestJson();

		entityjson.setCode(200);
		entityjson.setCodeMessage("A consulta foi realizada com sucesso e retornou um resultado.");
		entityjson.setHeader(new EntityRestHeader("v1", "tubosp",
				new EntityRestParameters(documento, tipodocInt), "Boa Vista Servicos S/A", "Boa Vista Servicos S/A",
				"", System.getenv("TOKEN_NAME"), dateFormated, 21138));
		entityjson.setDataCount(quantidadeCartorios);
		entityjson.setData(new EntityRestData(new EntityRestCartorios(listsp), dateFormated,
				dateHourFormated, documento, quantidade));

		entityjson.setErrors(null);
		entityjson.setReceipt(new EntityRestReceipt(null));

		String postJson = mapper.writeValueAsString(entityjson);

		return postJson.toString();
	}

	public String BuildJsonError(String cpfcnpj, String tipoerro, Integer code) throws JsonProcessingException,
			ServiceException, RemoteException, JAXBException, XMLStreamException, FactoryConfigurationError {

		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		String postJson = null;

		try {
			Date date = new Date();
			SimpleDateFormat formater = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");
			data = formater.format(date);

			EntityRestJsonErrors entityjson = new EntityRestJsonErrors();
			EntityBodyJson bodyjson = new EntityBodyJson();

			bodyjson.setRequested_at(data.substring(0, 10));

			entityjson.setCode(code);
			entityjson.setCode_message(tipoerro);
			entityjson.setHeader(new EntityRestHeaderErrors("v1", "tubosp",
					new EntityRestParametersErrors(cpfcnpj), bodyjson.client, bodyjson.client_name, bodyjson.token,
					bodyjson.token_name, bodyjson.billable, bodyjson.credits, bodyjson.has_limit, bodyjson.limit,
					bodyjson.used, bodyjson.cache_hit, bodyjson.cached_at, bodyjson.requested_at,
					bodyjson.elapsed_time_in_milliseconds));
			entityjson.setData_count(0);
			entityjson.setData(null);
			entityjson.setErrors(null);
			entityjson.setReceipt(new EntityRestReceiptErrors(bodyjson.url, bodyjson.id, bodyjson.key, null));
			postJson = mapper.writeValueAsString(entityjson);
		} catch (JsonProcessingException ex) {
			ex.printStackTrace();
		}

		return postJson;

	}

}