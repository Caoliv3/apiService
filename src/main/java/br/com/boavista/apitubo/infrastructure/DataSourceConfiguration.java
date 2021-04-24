package br.com.boavista.apitubo.infrastructure;

import java.sql.Connection;
import java.sql.SQLException;

public interface DataSourceConfiguration {
    Connection getConnection() ;
}
