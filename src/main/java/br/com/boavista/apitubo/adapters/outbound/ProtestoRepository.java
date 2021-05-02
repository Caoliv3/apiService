package br.com.boavista.apitubo.adapters.outbound;

import br.com.boavista.apitubo.core.domain.DetalheProtestos;
import br.com.boavista.apitubo.core.domain.DataCorrente;
import br.com.boavista.apitubo.models.*;
import br.com.boavista.apitubo.ports.outbound.ProtestoPort;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ProtestoRepository implements ProtestoPort {

    private Connection conn = null;
    private PreparedStatement sql = null;
    private static String SQL_INSERT_SIMPLIFICADA = "insert into protesto_simplificada (id_simplificada, id_auditoria, data_inclusao, hora_inclusao, json) values(?,?,?,?,?)";
    private static String SQL_INSERT_AUDITORIA = "insert into protesto_auditoria (id_auditoria, tipo_documento, documento, fonte_consulta, quantidade_consulta, data_inclusao, hora_inclusao, json) " +
            " values(?,?,?,?,?,?,?,?)";
    private static String SQL_INSERT_DETALHADA = "insert into protesto_detalhada (id_detalhada, id_auditoria, data_inclusao, hora_inclusao, json)  values(?,?,?,?,?)";
    private static String SQL_SELECT_PROTESTOS = "select data_protesto, data_vencimento,valor_protestado,uf_devedor, especie , id_codigo_cartorio, tipo_documento, documento, data_inclusao from tabela_protesto " +
            "  where documento =  ?" +
            "  and status = true" +
            "  order by id_codigo_cartorio ";
    private static String SQL_SELECT_ULTIMA_ATUALIZACAO = "select max(data_inclusao) as data_inclusao from protesto_auditoria " +
            " where documento =  ?";
    private static String SQL_INSERT_PROTESTO = "insert into tabela_protesto(id_codigo_cartorio, documento, datahora, identificador, tipo_documento, nome_devedor, endereco_devedor," +
            " cep_devedor, bairro_devedor, cidade_devedor, uf_devedor, livro_protesto, folha_protesto, protocolo," +
            " data_protocolo, especie, numero_titulo, data_emissao, data_vencimento, data_protesto, valor_original," +
            " valor_protestado, data_inclusao, data_inativacao, data_consulta_detalhada,data_retorno_consulta_detalhada," +
            " retorno_consulta, status)" +
            " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ,?,?)";
    private static String SQL_SELECT_JSON_DETALHADA = "select json from protesto_detalhada " +
            " where documento = ? " +
            "   and data_inclusao = ? ";
    private static String SQL_UPDATE_BAIXAR_PROTESTOS = "update tabela_protesto set data_inativacao = ?  , status = false " +
            " where documento = ? " +
            "   and id_codigo_cartorio in (%S) " +
            "   and  status = true ";
    private static String SQL_SELECT_QTDADE_CONSULTA = "select sum(quantidade_consulta) as quantidade_consulta from protesto_auditoria " +
            " where data_inclusao = ? " +
            "   and fonte_consulta = 3";
    private static String SQL_SELECT_QTDADE_PROTESTOS = "select count(*) as quantidades from tabela_protesto " +
            " where documento = ? " +
            "   and status = true " +
            "   and data_inativacao is null";


    private DataCorrente data = new DataCorrente();
    private Auditoria auditoria = new Auditoria();
    private DetalheProtestos detalheProtestos = new DetalheProtestos();

    public ProtestoRepository(Connection conexao) {
        this.conn = conexao;
    }

    @Override
    public List<Titulo> recuperarDetalheProtestos(String documento) {

        DetalheProtestos detalheProtestos = new DetalheProtestos();
        List<Titulo> listTitulo = new ArrayList<>();
        String codigoCartorio = "";
        int qtdadeCartorio = 0;

        try {
            sql = conn.prepareStatement(SQL_SELECT_PROTESTOS);
            sql.setString(1, documento);
            ResultSet rs = sql.executeQuery();
            while (rs.next()) {
                Titulo titulo = new Titulo();
                titulo.setData_protesto(rs.getString("data_protesto"));
                titulo.setData_vencimento(rs.getString("data_vencimento"));
                titulo.setValor_protestado(rs.getString("valor_protestado"));
                titulo.setUf_devedor(rs.getString("uf_devedor"));
                titulo.setEspecie(rs.getString("especie"));
                titulo.setCod_cartorio(rs.getString("id_codigo_cartorio"));
                titulo.setTipo_documento_devedor(String.valueOf(rs.getInt("tipo_documento")));
                titulo.setDocumento_devedor(rs.getString("documento"));
                titulo.setData_inclusao(rs.getString("data_inclusao"));
                listTitulo.add(titulo);
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar protesto da base Protestos {} ", e.getMessage());
        }
        return listTitulo;
    }

    @Override
    public String recuperarJsonConsultaDetalhada(String documento) {

        String retorno = "";
        String dataInclusao = getDataAtualizacao(documento);
        try {
            sql = conn.prepareStatement(SQL_SELECT_JSON_DETALHADA);
            sql.setString(1, documento);
            sql.setString(2, dataInclusao);

            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                retorno = rs.getString("json");
            }

        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar json detalha {} ", e.getMessage());
        }

        return retorno;
    }

    @Override
    public ProtestoPort with(Auditoria auditoria) {
        this.auditoria = auditoria;
        return this;
    }

    @Override
    public ProtestoPort salvar() {
        this.salvar(null);
        return this;
    }

    @Override
    public int getQtdadeConsultas() {

        int retorno = 0;
        try {
            sql = conn.prepareStatement(SQL_SELECT_QTDADE_CONSULTA);
            sql.setString(1, data.getDataCorrente());
            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                if (rs.getInt("quantidade_consulta") != 0) {
                    retorno = rs.getInt("quantidade_consulta");
                }
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar uma quantidade consulta {}", e.getMessage());

        }
        return retorno;
    }
    @Override
    public int getQtdadeProtestoDocumento(String documento){

        int retorno= 0;
        try {
            sql = conn.prepareStatement(SQL_SELECT_QTDADE_PROTESTOS);
            sql.setString(1, documento);
            ResultSet resultSet = sql.executeQuery();
            if (resultSet.next()){
                retorno = resultSet.getInt("quantidades");
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar quantidade de protestos do documento {}", e.getMessage());
        }
        return retorno;
    }

    @Override
    public String getDataAtualizacao(String documento) {

        String data = null;
        try {
            sql = conn.prepareStatement(SQL_SELECT_ULTIMA_ATUALIZACAO);
            sql.setString(1, documento);
            ResultSet rs = sql.executeQuery();
            if (rs.next()) {
                if (rs.getString("data_inclusao") != null) {
                    data = rs.getString("data_inclusao");
                }
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Selecionar uma data de Atualização {}", e.getMessage());
        }
        return data;
    }

    @Override
    public ProtestoPort salvar(DetalheProtestos detalheProtestos) {
        this.detalheProtestos = detalheProtestos;
        try {
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_UNCOMMITTED);
            salvarAuditoria(this.auditoria);
//            if (this.detalheProtestos != null) {
//                salvarDetalheProtesto(detalheProtestos);
//            }
            conn.commit();
        } catch (Exception e) {
            try {
                conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            log.info(" SQLSERROr - Inclusao  de Protestos: {}", e.getMessage());
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return this;
    }

    private void salvarDetalheProtesto(DetalheProtestos protestos) {

        if (protestos.getBaixar().size() > 0) {
            List<ResumoProtestos> resumoProtestos = protestos.getBaixar();
            baixarProtestos(resumoProtestos);
        }
        if (protestos.getIncluir().size() > 0) {
            List<Titulo> titulos = protestos.getIncluir();
            incluirProtesto(titulos);
        }
    }

    private void baixarProtestos(List<ResumoProtestos> resumoProtestos) {

       List<String> idCartorios = new ArrayList<>();
        String documento = "";

        int paramCount = 0;
        for (ResumoProtestos res : resumoProtestos) {
            idCartorios.add(res.idCartorioBoavista);
            documento = res.documento;
            paramCount++;
        }

        try {
            sql = conn.prepareStatement(parametros(paramCount));
            sql.setString(1, data.getDataCorrente());
            sql.setString(2, documento);
            paramCount = 3;
            for (String id: idCartorios) {
                sql.setString(paramCount, id);
                paramCount++;
            }
            sql.executeUpdate();

        } catch (SQLException e) {
            log.info(" SQLSERROr - Update de Protestos: {}", e.getMessage());
        }
    }

    private static String  parametros(int paramCount){

        String parm = ",?";
        String params = parm.repeat(paramCount);
        params = params.substring(1);
        return String.format(SQL_UPDATE_BAIXAR_PROTESTOS, params);
    }

    private void incluirProtesto(List<Titulo> titulos) {

        try {
            for (Titulo tit : titulos) {
                sql = conn.prepareStatement(SQL_INSERT_PROTESTO);
                sql.setString(1, tit.getCod_cartorio());
                sql.setString(2, tit.getDocumento_devedor());
                sql.setString(3, data.getDataCorrente());
                sql.setString(4, String.valueOf(Instant.now().truncatedTo(ChronoUnit.MICROS)));
                sql.setInt(5, Integer.parseInt(tit.getTipo_documento_devedor()));
                sql.setString(6, tit.getNome_devedor());
                sql.setString(7, tit.getEndereco_devedor());
                sql.setString(8, tit.getCep_devedor());
                sql.setString(9, tit.getBairro_devedor());
                sql.setString(10, tit.getCidade_devedor());
                sql.setString(11, tit.getUf_devedor());
                sql.setString(12, tit.getLivro_protesto());
                sql.setString(13, tit.getFolha_protesto());
                sql.setString(14, tit.getProtocolo());
                sql.setString(15, tit.getData_protocolo());
                sql.setString(16, tit.getEspecie());
                sql.setString(17, tit.getNumero_titulo());
                sql.setString(18, tit.getData_emissao());
                sql.setString(19, tit.getData_vencimento());
                sql.setString(20, tit.getData_protesto());
                sql.setString(21, tit.getValor_original());
                sql.setString(22, tit.getValor_protestado());
                sql.setString(23, data.getDataCorrente());
                sql.setString(24, null);
                sql.setString(25, null);
                sql.setString(26, null);
                sql.setString(27, null);
                sql.setInt(28, 1);

                sql.execute();
            }
        } catch (SQLException e) {
            log.info("SQLERROr - Incluir protestos na Base: {}", e.getMessage());
        }
    }

    private void salvarAuditoria(Auditoria auditoria) {

        incluirAuditoria(auditoria);

        if (auditoria.getIdSimplificada() != null) {
            incluirConsultaSimplificada(auditoria);
        }

        if (auditoria.getIdDetalhada() != null) {
            incluirDetalhada(auditoria);
        }
    }

    private void incluirConsultaSimplificada(Auditoria auditoria) {

        try {
            sql = conn.prepareStatement(SQL_INSERT_SIMPLIFICADA);
            sql.setString(1, auditoria.getIdSimplificada());
            sql.setString(2, auditoria.getIdAuditoria());
            sql.setString(3, data.getDataCorrente());
            sql.setString(4, data.getHoraCorrente());
            sql.setString(5, auditoria.getJsonSimplificada());
            sql.execute();

        } catch (SQLException e) {
            log.info("SQLERROR - Inclusão da consulta simplificada: {}", e.getMessage());
        }
    }

    private void incluirAuditoria(Auditoria auditoria) {

        try {
            sql = conn.prepareStatement(SQL_INSERT_AUDITORIA);
            sql.setString(1, auditoria.getIdAuditoria());
            sql.setInt(2, auditoria.getTipoDocumento());
            sql.setString(3, auditoria.getDocumento());
            sql.setInt(4, auditoria.getFonteConsulta());
            sql.setInt(5, auditoria.getQuantidadeConsulta());
            sql.setString(6, data.getDataCorrente());
            sql.setString(7, data.getHoraCorrente());
            sql.setString(8, auditoria.getJsonAuditoria());
            sql.execute();

        } catch (SQLException e) {
            log.info("SQLERROR - Inclusão na base auditoria: {}", e.getMessage());
        }
    }

    private void incluirDetalhada(Auditoria auditoria) {

        try {
            sql = conn.prepareStatement(SQL_INSERT_DETALHADA);
            sql.setString(1, auditoria.getIdDetalhada());
            sql.setString(2, auditoria.getIdAuditoria());
            sql.setString(3, data.getDataCorrente());
            sql.setString(4, data.getHoraCorrente());
            sql.setString(5, auditoria.getJsonDetalhada());
            sql.execute();

        } catch (SQLException e) {
            log.info("SQLERROR - Inclusão na base detalhada: {}", e.getMessage());
        }
    }


}
