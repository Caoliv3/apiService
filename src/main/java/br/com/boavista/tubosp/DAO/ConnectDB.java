package br.com.boavista.tubosp.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLDataException;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.mysql.cj.jdbc.exceptions.MySQLTimeoutException;

import br.com.boavista.tubosp.models.EntityRestProtesto;
import br.com.boavista.tubosp.models.EntityRestSP;
import br.com.boavista.tubosp.models.ResumoProtestos;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConnectDB {
	private Connection connection = null;
	private Statement statement1 = null;
	private ResultSet resultset = null;
	private int resultsetUpdate = 0;
	public List<String> codigo_cartorio = new ArrayList<String>();
		
	public void Connect() throws ClassNotFoundException, SQLException {

		String servidor = System.getenv("DB_HOST");
		String usuario = System.getenv("DB_USER");
		String senha = System.getenv("DB_PASS");

		try {
			this.connection = DriverManager.getConnection(servidor, usuario, senha);
			this.statement1 = this.connection.createStatement();
		} catch(MySQLTimeoutException e) {
			e.printStackTrace();
		}	
	}
	
//	public boolean connected() {
//		if(this.connection != null) {
//			return true;
//		} else {
//			return false;
//		}
//	}
	
	public void closeConnection() {
		try {
			connection.close();
		}
		catch(SQLException e) {
			System.out.println("Não conseguiu fechar conexão com o Banco");
			e.printStackTrace();
		}
	}
	
	public Integer listarQuantidadePorDoc(String documento) throws SQLException {
		Integer quantidadeTotal = 0;
		
		String query = "select count(0) as quantidade from tabela_protesto" + 
				" where documento = " + "'" + documento + "'" + 
				"  and status = true";

		this.resultset = this.statement1.executeQuery(query);
		this.statement1 = this.connection.createStatement();
		
		while(this.resultset.next()) {
			quantidadeTotal += this.resultset.getInt("quantidade");
		}

		return quantidadeTotal;
	}
	
	public String listarData(String cpfcnpj) throws SQLException {
		String query = "select data_inclusao from tabela_protesto "
				+ "where documento = " + "'" + cpfcnpj + "'" +
				  " and status = true" +
				  " order by data_inclusao desc";
		this.resultset = this.statement1.executeQuery(query);
		this.statement1 = this.connection.createStatement();
		String data = "";

		if(resultset.next()) {
			data = resultset.getString("data_inclusao");
		}
		
		return data;
		
	}
	
	public void Salvar(String cod_cartorio, String documento, String datahora, String id, int tipo_doc, String nome_devedor, String endereco_devedor, String cep_devedor, String bairro_devedor,
			String cidade_devedor, String uf_devedor, String livro_protesto, String folha_protesto, String protocolo, String data_protocolo, String especie, String numero_titulo,
			String data_emissao, String data_vencimento, String data_protesto, String valor_original, String valor_protestado, String data_inclusao, String data_inativacao, 
			String data_consulta_detalhada, String data_retorno_consulta_detalhada, String retorno_consulta, String status) throws SQLException {
		try {
			PreparedStatement stmt = connection.prepareStatement(
					"insert into tabela_protesto(id_codigo_cartorio," 
											   + "documento,"
											   + "datahora,"
											   + "identificador,"
											   + "tipo_documento,"
											   + "nome_devedor,"
											   + "endereco_devedor,"
											   + "cep_devedor,"
											   + "bairro_devedor,"
											   + "cidade_devedor,"
											   + "uf_devedor,"
											   + "livro_protesto,"
											   + "folha_protesto,"
											   + "protocolo,"
											   + "data_protocolo,"
											   + "especie,"
											   + "numero_titulo,"
											   + "data_emissao,"
											   + "data_vencimento,"
											   + "data_protesto,"
											   + "valor_original,"
											   + "valor_protestado,"
											   + "data_inclusao,"
											   + "data_inativacao,"
											   + "data_consulta_detalhada,"
											   + "data_retorno_consulta_detalhada,"											   
											   + "retorno_consulta,"
											   + "status)"
											   + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stmt.setString(1, cod_cartorio);
			stmt.setString(2, documento);
			stmt.setString(3, datahora);
			stmt.setString(4, id);
			stmt.setInt(5, tipo_doc);
			stmt.setString(6, nome_devedor);
			stmt.setString(7, endereco_devedor);
			stmt.setString(8, cep_devedor);
			stmt.setString(9, bairro_devedor);
			stmt.setString(10, cidade_devedor);
			stmt.setString(11, uf_devedor);
			stmt.setString(12, livro_protesto);
			stmt.setString(13, folha_protesto);
			stmt.setString(14, protocolo);
			stmt.setString(15, data_protocolo);
			stmt.setString(16, especie);
			stmt.setString(17, numero_titulo);
			stmt.setString(18, data_emissao);
			stmt.setString(19, data_vencimento);
			stmt.setString(20, data_protesto);
			stmt.setString(21, valor_original);
			stmt.setString(22, valor_protestado);
			stmt.setString(23, data_inclusao);
			stmt.setString(24, data_inativacao);
			stmt.setString(25, data_consulta_detalhada);
			stmt.setString(26, data_retorno_consulta_detalhada);
			stmt.setString(27, retorno_consulta);
			stmt.setString(28, status);
			stmt.execute();
			
		} catch(SQLDataException e) {
			e.printStackTrace();
		}
	}

//	public Integer listarRetornoLimiteDiario() throws SQLException {
//		//String query = "select totalreg from tabela_protesto_limite_diario_view";
//
//		Date date = new Date();
//		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
//		String dateFormated = formater.format(date);
//
//		String query = "select count(0) as totalreg from tabela_protesto" +
//						" where data_inclusao = " + "'" + dateFormated + "'" +
//						" and status = true";
//
//		this.resultset = this.statement1.executeQuery(query);
//		this.statement1 = this.connection.createStatement();
//		Integer retorno = 0;
//
//		if(this.resultset.next()) {
//			retorno = this.resultset.getInt("totalreg");
//		}
//
//		return retorno;
//	}
	
	public String validaRetornoConsulta(String documento) throws SQLException {
		String query = "select retorno_consulta from tabela_protesto" +
					   " where documento = " + "'" + documento + "'" +
					   " and status = true" +
					   " limit 1";
		
		String retorno = "";
		
		this.resultset = this.statement1.executeQuery(query);
		this.statement1 = this.connection.createStatement();
		
		if(this.resultset.next()) {
			retorno = this.resultset.getString("retorno_consulta");
		}
		
		return retorno;
	}
	
	public Integer retornoQtdeTotalPorDoc(String documento) throws SQLException {
		String query = "select count(0) as totalprotestos from tabela_protesto where documento =" + "'" + documento + "'" +
				       " and status = true";
		
		Integer retorno = 0;
		
		this.resultset = this.statement1.executeQuery(query);
		this.statement1 = this.connection.createStatement();
		
		if(this.resultset.next()) {
			retorno = this.resultset.getInt("totalprotestos");
		}
		
		return retorno;
		
	}
	
	public List<EntityRestSP> retornoJsonBanco(String documento) throws SQLException {
		List<EntityRestSP> sps = new ArrayList<EntityRestSP>();

		log.info("geranovoJsonBanco - documento: {}", documento);

		String query = "select data_protesto, data_vencimento,valor_protestado,uf_devedor, especie , data_inclusao, id_codigo_cartorio from tabela_protesto" +
				" where documento = " + "'" + documento + "'" +
				" and status = true " +
				" order by id_codigo_cartorio ";

		String codigoCartorio = "";
		int qtdadeCartorio = 0;

		ResultSet resultProtestos = statement1.executeQuery(query);
		statement1 = this.connection.createStatement();
		EntityRestSP entityRespSP = null;

		log.info("geranovoJsonBanco - iniciando processamento ");
		while (resultProtestos.next()) {

			if (!codigoCartorio.equals(resultProtestos.getString("id_codigo_cartorio"))) {
				if (qtdadeCartorio > 0 && entityRespSP != null) {
					entityRespSP.setQuantidade(qtdadeCartorio);
					sps.add(entityRespSP);
				}
				codigoCartorio = resultProtestos.getString("id_codigo_cartorio");
				entityRespSP = new EntityRestSP();
				entityRespSP.setCodigo(codigoCartorio);
				entityRespSP.setAtualizacaoData(resultProtestos.getString("data_inclusao"));

				entityRespSP.setProtestos(new ArrayList<EntityRestProtesto>());
				qtdadeCartorio = 0;
			}
			EntityRestProtesto protesto = new EntityRestProtesto();
			protesto.setCpfCnpj(documento);
			protesto.setDataProtesto(resultProtestos.getString("data_protesto"));
			protesto.setDataVencimento(resultProtestos.getString("data_vencimento"));
			protesto.setValor(resultProtestos.getString("valor_protestado").replace(".", ",").trim());
			protesto.setUf_cartorio(resultProtestos.getString("uf_devedor"));
			protesto.setEspecie(resultProtestos.getString("especie"));
			entityRespSP.getProtestos().add(protesto);
			qtdadeCartorio++;
		}

		if (!(entityRespSP == null)) {
			entityRespSP.setQuantidade(qtdadeCartorio);
			sps.add(entityRespSP);
		}
		log.info("geranovoJsonBanco - termino de processamento ");
		return sps;
	}
	
	public Integer retornoQtdeCartoriosPorDoc(String documento) throws SQLException {
		List<Integer> qtdeCart = new ArrayList<Integer>();
		
		String query = "select count(0) as quantidade from tabela_protesto" + 
				" where documento = " + "'" + documento + "'" + 
				" and status = true";
		
		this.resultset = this.statement1.executeQuery(query);
		this.statement1 = this.connection.createStatement();
		
		while(this.resultset.next()) {
			qtdeCart.add(this.resultset.getInt("quantidade"));			
		}	
		
		return qtdeCart.size();
	}
	
	public void atualizarCampoRetornoConsulta(String documento, String json) {
		
		String query = "UPDATE tabela_protesto "
					 + "SET retorno_consulta = " + "'" + json + "'" + 
					 " WHERE documento = " + "'" + documento + "'" +
					 " AND status = true";
		
		try {
			this.resultsetUpdate = this.statement1.executeUpdate(query);//executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void atualizarCampoInativacao(String documento, String cod_cartorio) {
		
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String dateFormated = formater.format(date);
		
		String query = "UPDATE tabela_protesto"
					 + " SET data_inativacao = " + "'" + dateFormated + "'" +
				      " ,status = false" +
					  " ,retorno_consulta = ''" +
					  " WHERE documento = " + "'" + documento + "'" +
					  " AND id_codigo_cartorio = " + "'00" + cod_cartorio + "'";
		
		try {
			this.resultsetUpdate = this.statement1.executeUpdate(query);//executeQuery(query);
			log.info("Registros Inativados : {} ", this.resultsetUpdate);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void atualizarCampoInativacaoPorDoc(String documento) {
		
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String dateFormated = formater.format(date);
		
		String query = "UPDATE tabela_protesto"
					 + " SET data_inativacao = " + "'" + dateFormated + "'," 
					 + " status = false " +
					  " WHERE documento = " + "'" + documento + "'";
		
		try {
			this.resultsetUpdate = this.statement1.executeUpdate(query);//executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public String retornaCartoriosDetalhada(String documento, String cod_cartorio, Integer quantidade) throws SQLException {
//		//String query = "select * from tabela_protesto_view where documento = " + "'" + documento + "'" + " and id_codigo_cartorio = " + "'" + cod_cartorio + "'";
//
//		String query = "select count(0) as quantidade from tabela_protesto" +
//					" where documento = " + "'" + documento + "'" +
//				    "  and id_codigo_cartorio = " + "'00" + cod_cartorio + "'" +
//					"  and status = true";
//
//		String retorno = "";
//		Integer quantidadeBanco = 0;
//
//		this.resultset = this.statement1.executeQuery(query);
//		this.statement1 = this.connection.createStatement();
//
//		if(this.resultset.next()) {
//			quantidadeBanco = (this.resultset.getInt("quantidade"));
//
//			if(quantidadeBanco == quantidade) {
//					return retorno;
//			} else {
//					retorno = cod_cartorio;
//				}
//			}
//		return cod_cartorio;
//		}


    public List<ResumoProtestos> retornaResumoCartorios(String documento) throws SQLException {

        String query = "select id_codigo_cartorio, count(0) as quantidade from tabela_protesto" +
                " where documento = " + "'" + documento + "'" +
                "  and status = true" +
                "   group by id_codigo_cartorio";


        List<ResumoProtestos> resumoCartorioBase = new ArrayList<ResumoProtestos>();

        this.resultset = this.statement1.executeQuery(query);
        this.statement1 = this.connection.createStatement();

        while(this.resultset.next()) {
            resumoCartorioBase.add(new ResumoProtestos("", documento, this.resultset.getString("id_codigo_cartorio"), this.resultset.getString("quantidade")));

        }
        return resumoCartorioBase;
    }
	
	public void atualizarCampoDataInclusao(String documento, String cod_cartorio) throws SQLException {
		
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String dateFormated = formater.format(date);
		
		String query = "UPDATE tabela_protesto"
					 + " SET data_inclusao = " + "'" + dateFormated + "'" +
					   " WHERE documento = " + "'" + documento + "'" +
					   " AND id_codigo_cartorio = " + "'00" + cod_cartorio + "'";
		
		try {
			this.resultsetUpdate = this.statement1.executeUpdate(query);//executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	public List<String> validaCartorios(String documento, List<String> cod_cartorio) throws SQLException {
//
//		List<String> retorno = new ArrayList<String>();
//
//		for(String cod_cart : cod_cartorio) {
//
//		String query = "select id_codigo_cartorio from tabela_protesto" +
//				" where documento = " + "'" + documento + "'" +
//				"  and id_codigo_cartorio = " + "'00" + cod_cart + "'" +
//				"  and status = true";
//
//		this.resultset = this.statement1.executeQuery(query);
//		this.statement1 = this.connection.createStatement();
//
//	   if(this.resultset.next()) {
//			String cod_cartorio_banco = (this.resultset.getString("id_codigo_cartorio"));
//
//			if(cod_cart.equals(cod_cartorio_banco)) {
//				retorno.add(cod_cartorio_banco);
//			}
//		}
//
//		}
//	   return retorno;
//	}
	
	public void alterarDataInclusaoPorDoc(String documento) {
		
		Date date = new Date();
		SimpleDateFormat formater = new SimpleDateFormat("yyyy-MM-dd");
		String dateFormated = formater.format(date);
		
		String query = "UPDATE tabela_protesto"
				 + " SET data_inclusao = " + "'" + dateFormated + "'" +
				   " WHERE documento = " + "'" + documento + "'" +
				   " AND status = true";
	
	try {
		this.resultsetUpdate = this.statement1.executeUpdate(query);//executeQuery(query);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		}
	}
}