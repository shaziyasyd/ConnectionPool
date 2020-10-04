package com.shaziyasyd.pool;

public class ConnectionPool extends ObjectPool<Connection> {

    private static ConnectionPool instance;

    private ConnectionPool(int maxPoolSize) {
        super(maxPoolSize);
    }
    public static ConnectionPool getInstance(int maxPoolSize) {
        if(instance == null) {
            instance = new ConnectionPool(maxPoolSize);
        }
        return instance;
    }

    @Override
    protected Connection create() {
        return new Connection();
    }

    @Override
    protected void close(Connection connection) {
        connection.close();
    }

    @Override
    protected boolean isValid(Connection connection) {
        return !connection.isClosed();
    }
}
