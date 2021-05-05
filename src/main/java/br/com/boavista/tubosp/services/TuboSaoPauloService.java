package br.com.boavista.tubosp.services;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.xml.bind.JAXBException;
import javax.xml.rpc.ServiceException;
import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLStreamException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;

import br.com.boavista.tubosp.DAO.ConnectDB;
import br.com.boavista.tubosp.adapters.outbound.ConsultaDetalhadaSoapAdapter;
import br.com.boavista.tubosp.adapters.outbound.ConsultaSimplesSoapAdapter;
import br.com.boavista.tubosp.domain.AtualizaCache;
import br.com.boavista.tubosp.domain.ValidacaoConsultaSimplificada;
import br.com.boavista.tubosp.domain.ValidacaoDocumento;
import br.com.boavista.tubosp.domain.ValidacaoQuantidade;
import br.com.boavista.tubosp.models.ConsultaDetalhadaFiltro;
import br.com.boavista.tubosp.models.ConsultaSimplesFiltro;
import br.com.boavista.tubosp.models.EntityRestProtesto;
import br.com.boavista.tubosp.models.EntityRestSP;
import br.com.boavista.tubosp.models.ResumoProtestos;
import br.com.boavista.tubosp.models.Titulo;
import br.com.boavista.tubosp.service.http.TituloRequest;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TuboSaoPauloService implements RequestService {
	@Setter private String documento;
	@Setter private String tipoDocumento;
	
	@Autowired 
	public AtualizaCache cache;
	
	@Autowired
	public ValidacaoConsultaSimplificada validaConsultaSimp;

	private List<EntityRestSP> lista = null;
	private BuildJsonHub buildjson = new BuildJsonHub();
	private ConnectDB sql = new ConnectDB();

	@Override
	public String consultar(String documento, String tipoDocumento) {
		Integer tipoDocumentoInt = Integer.parseInt(tipoDocumento);
		ValidacaoDocumento validadoc = new ValidacaoDocumento();
		String validaDocumento = "";

		String validarConsulta = "";
		ValidacaoQuantidade validaQuantidade = new ValidacaoQuantidade();
		String retornoValidacaoQuantidade = "";
		String validaRetornoConsulta = "";
		String ip = "";
		String json = null;
		String codigo_cartorio = "";
		this.documento = documento;		
		this.tipoDocumento = tipoDocumento;

		List<Titulo> titulos;
		
		try {
			ip = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}	
		
		try {
			validaDocumento = validadoc.Validar(tipoDocumento, documento);
		} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException | XMLStreamException
				| FactoryConfigurationError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			validarConsulta = validaConsultaSimp.ValidaExisteProtesto(tipoDocumento, documento, ip);
		} catch (RemoteException | JsonProcessingException | ClassNotFoundException | JAXBException | XMLStreamException
				| FactoryConfigurationError | ServiceException | SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String dateFormated = formater.format(date);
		
		Integer quantidade = 0;
		Integer quantidadeValidacao = 0;
		
		if(validaDocumento.equals("")) {		
			if(validarConsulta.equals("")) {

				try {
					sql.Connect();
				} catch (ClassNotFoundException | SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				List<String> codigos_cartorios = new ArrayList<String>();
				List<ResumoProtestos> protestos = this.fazerConsultaSimples();
				List<ResumoProtestos> cartorioDetalhada = new ArrayList<ResumoProtestos>();
				List<ResumoProtestos> cartorioDetalhadaSemRegistro = new ArrayList<ResumoProtestos>();
				List<ResumoProtestos> resumoCartorioBase = new ArrayList<ResumoProtestos>();
				
				for(ResumoProtestos validaQtdeProtestos : protestos) {
					Integer qtdeParse = Integer.parseInt(validaQtdeProtestos.protestos);
					quantidadeValidacao += qtdeParse;
				}
				quantidade = quantidadeValidacao;
				log.info("quantidade de protestos do Intituto SP : {}", quantidadeValidacao);

				try {
					resumoCartorioBase = sql.retornaResumoCartorios(documento);
				}catch (SQLException e){
					log.info("SQlError resumo da base de cartorio : {}", e.getMessage());
				}

				// Verificar se o cartorio da base de protesto retornou na consulta simplicada
				// caso o cartorio da base nao esteja na simplicada o mesmo e inativado
				for(ResumoProtestos protestosBase : resumoCartorioBase) {

					int idCartorio = Integer.parseInt(protestosBase.getId_cartorio_boavista());
					ResumoProtestos protestosSimplificada = this.getProtestosPorCartorio(idCartorio , protestos);
					 if(protestosSimplificada == null ) {
						 sql.atualizarCampoInativacao(documento, String.valueOf(idCartorio));
					 }
					 else
					 	if (!protestosSimplificada.getProtestos().equals(protestosBase.getProtestos())) {
							codigos_cartorios.add(String.valueOf(idCartorio));
							cartorioDetalhada.add(new ResumoProtestos(tipoDocumento, documento, String.valueOf(idCartorio), protestosBase.getProtestos()));
						}
					cartorioDetalhadaSemRegistro.add(new ResumoProtestos(tipoDocumento, documento,String.valueOf(idCartorio),protestosBase.getProtestos()));
				}

				// Listar os cartorio da simplicada que nao esta na base
				for(ResumoProtestos protesto : protestos) {

					int idCartorio =  Integer.parseInt(protesto.getId_cartorio_boavista());
					ResumoProtestos protestosBase = this.getProtestosPorCartorio(idCartorio , resumoCartorioBase);
					if(protestosBase == null ) {
						codigos_cartorios.add(String.valueOf(idCartorio));
						cartorioDetalhada.add(new ResumoProtestos(tipoDocumento, documento, String.valueOf(idCartorio), protesto.getProtestos()));
					}
					cartorioDetalhadaSemRegistro.add(new ResumoProtestos(tipoDocumento, documento,String.valueOf(idCartorio),protesto.getProtestos()));
				}

				//Gera novo Json
				this.geraNovoJson(documento, quantidade);

				try {
					retornoValidacaoQuantidade = validaQuantidade.Validar(documento, quantidadeValidacao);
					log.info("quantidade de protesto na base: {}", retornoValidacaoQuantidade);
				} catch (ClassNotFoundException | SQLException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
								
				String dataInclusao = "";
				
				try {
					dataInclusao = sql.listarData(documento);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}		
				String data = dataInclusao.replaceAll(" ", "");
				
				try {
					validaRetornoConsulta = sql.validaRetornoConsulta(documento);
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
						
				if(retornoValidacaoQuantidade.equals("QUANTIDADE DO INSTITUTO IGUAL A BASE")) {
					cache.setQuantidadeProtesto(validaQuantidade.getQuantidadeBanco());
					cache.setDataBase(data);

					//if(cache.atualizar()) {
						if(validaRetornoConsulta.trim().equals("")) {
							try {
								lista = sql.retornoJsonBanco(documento);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								json = buildjson.BuildJsonSuccess(documento, tipoDocumento, sql.retornoQtdeTotalPorDoc(documento) ,sql.retornoQtdeCartoriosPorDoc(documento), lista);
							} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
									| XMLStreamException | FactoryConfigurationError | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							if (quantidade < 3000){
								sql.atualizarCampoRetornoConsulta(documento, json);

								try {
									validaRetornoConsulta = sql.validaRetornoConsulta(documento);

								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else {
								validaRetornoConsulta = json;
							}

							log.info("Retorno Hub: {}" , validaRetornoConsulta);
							return validaRetornoConsulta;
						}
						else {
							log.info("Retorno Hub: {}" , validaRetornoConsulta);
							return validaRetornoConsulta;
						}

					/*} else {
						
						try {
							return buildjson.BuildJsonError(documento, "IMPOSSIVEL REALIZAR A CONSULTA DETALHADA, POIS O MESMO DOCUMENTO JÁ FOI VERIFICADO HÁ " + cache.getDiasDiferenca() +" DIAS", 613);
						} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
								| XMLStreamException | FactoryConfigurationError e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}*/
					
				}  
				
				else if(retornoValidacaoQuantidade.equals("QUANTIDADE DO INSTITUTO DIFERENTE DA BASE")) {

					cache.setQuantidadeProtesto(validaQuantidade.getQuantidadeBanco());
					cache.setDataBase(data);

					if(cache.atualizar()) {
						if(codigos_cartorios.isEmpty()) {
							try {
								lista = sql.retornoJsonBanco(documento);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							try {
								json = buildjson.BuildJsonSuccess(documento, tipoDocumento, sql.retornoQtdeTotalPorDoc(documento) ,sql.retornoQtdeCartoriosPorDoc(documento), lista);
							} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
									| XMLStreamException | FactoryConfigurationError | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							if (quantidade < 3000) {
								sql.atualizarCampoRetornoConsulta(documento, json);

								try {
									validaRetornoConsulta = sql.validaRetornoConsulta(documento);
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							} else{
								validaRetornoConsulta = json;
							}
							
							return validaRetornoConsulta;
						}
						
						if(cartorioDetalhada.isEmpty()) {
							try {
								return buildjson.BuildJsonError(documento, "NÃO EXISTE REGISTRO PARA SER CONSULTADO, NA CONSULTA DETALHADA", 613);
							} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
									| XMLStreamException | FactoryConfigurationError e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
						
					    Date dateConsultaDetalhada = new Date();
					    DateFormat formatDetalhada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					    String stringDateDetalhada = formatDetalhada.format(dateConsultaDetalhada);
					    //String dataConsultaDetalhada = stringDateDetalhada.replaceAll("-","").replaceAll(" ", "").replaceAll(":", "").replace(".", "");
						
						titulos = this.fazerConsultaDetalhada(cartorioDetalhada);
						
					    Date dateRetornoConsultaDetalhada = new Date();
					    DateFormat formatRetornoDetalhada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
					    String stringDateRetornoDetalhada = formatRetornoDetalhada.format(dateRetornoConsultaDetalhada);
					    //String dataRetornoConsultaDetalhada = stringDateRetornoDetalhada.replaceAll("-","").replaceAll(" ", "").replaceAll(":", "").replace(".", "");
						
						Integer i = 0;						
						
						List<String> retornoInativacao = null;
							try {
									for(String cod : codigos_cartorios) {
										sql.atualizarCampoInativacao(documento, cod);
									}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						
											
							for(Titulo titulosalvar : titulos) {
								
								if(titulosalvar.erro_metodo.equals("true")) {
									try {
										return buildjson.BuildJsonError(documento, titulosalvar.erro_metodo_descricao, 613);
									} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
											| XMLStreamException | FactoryConfigurationError e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
								
								String dataChave = "";
								String dataChaveCompost = "";
							    Date dateChave = new Date();
							    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
							    String stringDate = sdf.format(dateChave);
							    dataChave = stringDate.replaceAll("-","").replaceAll(" ", "").replaceAll(":", "").replace(".", "");
							    dataChaveCompost = dataChave.substring(0,8);
							    i++;
							    String campoIdentificador = dataChave.toString() + i.toString();
							    if(quantidade >= 1) {
								try {
									sql.Salvar("00" + titulosalvar.cod_cartorio, documento, dataChaveCompost.toString(), campoIdentificador, tipoDocumentoInt, titulosalvar.nome_devedor, titulosalvar.endereco_devedor, titulosalvar.cep_devedor, titulosalvar.bairro_devedor, titulosalvar.cidade_devedor, titulosalvar.uf_devedor, titulosalvar.livro_protesto, titulosalvar.folha_protesto, titulosalvar.protocolo, titulosalvar.data_protocolo, titulosalvar.especie, titulosalvar.numero_titulo, titulosalvar.data_emissao, titulosalvar.data_vencimento, titulosalvar.data_protesto, titulosalvar.valor_original, titulosalvar.valor_protestado, dateFormated, null, stringDateDetalhada, stringDateRetornoDetalhada, "", "1");
								} catch (SQLException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							  }															
							}
							
							sql.alterarDataInclusaoPorDoc(documento);

							
							if(!codigos_cartorios.isEmpty()) {
								try {
									for(String cod_cart : codigos_cartorios) {
										sql.atualizarCampoDataInclusao(documento, cod_cart);
									}
								} catch (SQLException e2) {
									// TODO Auto-generated catch block
									e2.printStackTrace();
								}
							}
							
							try {
								lista = sql.retornoJsonBanco(documento);
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
							try {
								json = buildjson.BuildJsonSuccess(documento, tipoDocumento, sql.retornoQtdeTotalPorDoc(documento) ,sql.retornoQtdeCartoriosPorDoc(documento), lista);
							} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
									| XMLStreamException | FactoryConfigurationError | SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

						if (quantidade < 3000) {
							sql.atualizarCampoRetornoConsulta(documento, json);

							try {
								validaRetornoConsulta = sql.validaRetornoConsulta(documento);
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						} else{
							validaRetornoConsulta =json;
						}

							
							return validaRetornoConsulta;
						
					}
					else {
						
						try {
							return buildjson.BuildJsonError(documento, "IMPOSSIVEL REALIZAR A CONSULTA DETALHADA, POIS O MESMO DOCUMENTO JÁ FOI VERIFICADO HÁ " + cache.getDiasDiferenca() +" DIAS", 613);
						} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
								| XMLStreamException | FactoryConfigurationError e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
													
				} else if(retornoValidacaoQuantidade.equals("NAO EXISTE REGISTRO NA BASE")) {
					
					if(cartorioDetalhadaSemRegistro.isEmpty()) {
						try {
							return buildjson.BuildJsonError(documento, "NÃO EXISTE REGISTRO PARA SER CONSULTADO, NA CONSULTA DETALHADA", 613);
						} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
								| XMLStreamException | FactoryConfigurationError e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					
				    Date dateConsultaDetalhada = new Date();
				    DateFormat formatDetalhada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    String stringDateDetalhada = formatDetalhada.format(dateConsultaDetalhada);
				    //String dataConsultaDetalhada = stringDateDetalhada.replaceAll("-","").replaceAll(" ", "").replaceAll(":", "").replace(".", "");
					
					titulos = this.fazerConsultaDetalhada(cartorioDetalhadaSemRegistro);
					
				    Date dateRetornoConsultaDetalhada = new Date();
				    DateFormat formatRetornoDetalhada = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
				    String stringDateRetornoDetalhada = formatRetornoDetalhada.format(dateRetornoConsultaDetalhada);
				   // String dataRetornoConsultaDetalhada = stringDateRetornoDetalhada.replaceAll("-","").replaceAll(" ", "").replaceAll(":", "").replace(".", "");
					
					Integer i = 0;
						for(Titulo titulosalvar : titulos) {
							
							if(titulosalvar.erro_metodo.equals("true")) {
								try {
									return buildjson.BuildJsonError(documento, titulosalvar.erro_metodo_descricao, 613);
								} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
										| XMLStreamException | FactoryConfigurationError e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
							
							String dataChave = "";
							String dataChaveCompost = "";
						    Date dateChave = new Date();
						    DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
						    String stringDate = sdf.format(date);
						    dataChave = stringDate.replaceAll("-","").replaceAll(" ", "").replaceAll(":", "").replace(".", "");
						    dataChaveCompost = dataChave.substring(0,8);
						    i++;
						    String campoIdentificador = dataChave.toString() + i.toString();
						    
							try {
								sql.Salvar("00" + titulosalvar.cod_cartorio, documento, dataChaveCompost.toString(), campoIdentificador, tipoDocumentoInt, titulosalvar.nome_devedor, titulosalvar.endereco_devedor, titulosalvar.cep_devedor, titulosalvar.bairro_devedor, titulosalvar.cidade_devedor, titulosalvar.uf_devedor, titulosalvar.livro_protesto, titulosalvar.folha_protesto, titulosalvar.protocolo, titulosalvar.data_protocolo, titulosalvar.especie, titulosalvar.numero_titulo, titulosalvar.data_emissao, titulosalvar.data_vencimento, titulosalvar.data_protesto, titulosalvar.valor_original, titulosalvar.valor_protestado, dateFormated, null, stringDateDetalhada, stringDateRetornoDetalhada, "", "1");
							} catch (SQLException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
					}
						this.geraNovoJson(documento, quantidade);
						
						try {
							validaRetornoConsulta = sql.validaRetornoConsulta(documento);
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						log.info("Retorno Hub: {}" , validaRetornoConsulta);
						return validaRetornoConsulta;					
				}			 
			}
			
			else {
				log.info("Retorno Hub: {}" , validarConsulta);
				return validarConsulta;
			}
		}
		else {
			return validaDocumento;
		}
					
		return "";
	}

	@Override
	public List<ResumoProtestos> fazerConsultaSimples() {
		return new ConsultaSimplesSoapAdapter().consultar(new ConsultaSimplesFiltro(this.documento, this.tipoDocumento));
	}

	@Override
	public List<Titulo> fazerConsultaDetalhada(List<ResumoProtestos> protestos)  {
		ExecutorService executor = Executors.newFixedThreadPool(protestos.size() > 120 ? 120 : protestos.size());
		
		List<Callable<List<Titulo>>> callables = new ArrayList<>();
		
		for (ResumoProtestos protesto: protestos) {
			callables.add(new TituloRequest(new ConsultaDetalhadaSoapAdapter(), ConsultaDetalhadaFiltro.buildFromProtesto(protesto)));	
		}
		
		long inicio = System.currentTimeMillis();
		List<Titulo> result = new ArrayList<>();
		
		try {
			executor.invokeAll(callables)
			    .stream()
			    .map(future -> {
			        try {
			            return future.get();
			        }
			        catch (Exception e) {
			        	log.info("erro --> " + e.getMessage());
			            throw new IllegalStateException(e);
			        }
			    })
			    .forEach(data -> result.addAll(data));
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		executor.shutdown();
		
		long delta = System.currentTimeMillis() - inicio;
		//System.out.println("Delta time: " + delta);		
		
		return result;
	}

	public ResumoProtestos getProtestosPorCartorio(int idCatorio, List<ResumoProtestos> protestos){

		for (ResumoProtestos cartorio: protestos ) {

			if(Integer.parseInt(cartorio.getId_cartorio_boavista()) == idCatorio){
				return cartorio;
			}
		}
		return null;
	}

	public void geraNovoJson(String documento, int quantidade){

		String json = "";
		try {
			lista = sql.retornoJsonBanco(documento);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			 json = buildjson.BuildJsonSuccess(documento, tipoDocumento, sql.retornoQtdeTotalPorDoc(documento) ,sql.retornoQtdeCartoriosPorDoc(documento), lista);
		} catch (JsonProcessingException | RemoteException | ServiceException | JAXBException
				| XMLStreamException | FactoryConfigurationError | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (quantidade < 3000) {
			sql.atualizarCampoRetornoConsulta(documento, json);
		}
	}

}