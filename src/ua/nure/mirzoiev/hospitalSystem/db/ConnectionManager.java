package ua.nure.mirzoiev.hospitalSystem.db;

import org.apache.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class ConnectionManager {
    private static final Logger LOG = Logger.getLogger(ConnectionManager.class);
    private static ConnectionManager instance;

    public static synchronized ConnectionManager getInstance() {
        if (instance == null) {
            instance = new ConnectionManager();
        }

        return instance;
    }

    private ConnectionManager() {
    }

    public Connection getConnection() throws SQLException {
        Connection connection = null;

        try {
            Context initContext = new InitialContext();
            Context envContext = (Context) initContext.lookup("java:/comp/env");

            DataSource dataSource = (DataSource) envContext.lookup("jdbc/hospitalTest2?useUnicode=true");
            connection = dataSource.getConnection();
        } catch (NamingException e) {
            LOG.error("Cannot obtain a connection from the pool", e);
        }

        return connection;
    }
}
