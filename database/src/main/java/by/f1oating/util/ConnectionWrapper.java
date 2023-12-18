package by.f1oating.util;

import java.sql.Connection;

public class ConnectionWrapper implements AutoCloseable{
    private final Connection connection;

    public ConnectionWrapper(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void close() {
        ConnectionManager.pool.add(this.connection);
    }

}
