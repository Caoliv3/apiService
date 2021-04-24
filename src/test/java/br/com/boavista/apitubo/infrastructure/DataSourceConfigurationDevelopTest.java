package br.com.boavista.apitubo.infrastructure;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.SQLException;

@Component
@Slf4j
@Profile("test")
public class DataSourceConfigurationDevelopTest implements DataSourceConfigurationTest {
    private HikariConfig config;
    private HikariDataSource ds;
//    @Value("${service.apitubo.protestos.servidor}")
//    private String servidor;
//    @Value("${service.apitubo.protestos.usuario}")
//    private String usuario;
//    @Value("${service.apitubo.protestos.senha}")
//    private String senha;
//    @Value("S{service.apitubo.protestos.documento}")
//    private String documento;
//    @Value("${service.apitubo.protestos.tipo-documento}")
//    private String tipoDocumento;

    @Override
    public Connection getConnection() {
        if (this.config == null){
//            config.setJdbcUrl( "jdbc:h2:mem:test;INIT=runscript from './sql/create.sql';runscript from '~/init.sql';" );
            this.config = new HikariConfig();
            config.setJdbcUrl( "jdbc:h2:mem:testdb ");
            config.setUsername( "sa" );
            config.setPassword( "" );
            config.addDataSourceProperty( "cachePrepStmts" , "true" );
            config.addDataSourceProperty( "prepStmtCacheSize" , "25" );
            config.addDataSourceProperty( "prepStmtCacheSqlLimit" , "2048" );
            ds = new HikariDataSource( config );
        }
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            log.info("SQLERROr - Nao foi possivel conectar a base de dados {}" , e.getMessage());
            return null;
        }
    }
}
