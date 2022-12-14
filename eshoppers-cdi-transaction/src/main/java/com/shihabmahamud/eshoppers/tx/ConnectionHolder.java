package com.shihabmahamud.eshoppers.tx;

import com.shihabmahamud.eshoppers.jdbc.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.RequestScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@RequestScoped
public class ConnectionHolder {
    private static final Logger LOGGER = LoggerFactory
            .getLogger(ConnectionHolder.class);

    private Connection connection;
    private final DataSource dataSource = ConnectionPool.getInstance().getDataSource();

    @PostConstruct
    public void createConnection() {
        try {
            this.connection = this.dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.info("Unable to get connection", e);
            throw new RuntimeException("Unable to get connection", e);
        }
    }

    public Connection getConnection() {
        if (connection == null)
            throw new RuntimeException("Connection is null");
        return connection;
    }

    @PreDestroy
    public void close() {
        LOGGER.info("closing Connection");
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.info("Unable to close connection");
        }
    }
}
