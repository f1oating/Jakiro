package by.f1oating.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class ConnectionManager {

    private final static String URL_KEY = "db.url";
    private final static String USERNAME_KEY = "db.username";
    private final static String PASSWORD_KEY = "db.password";
    private final static int DEFAULT_POOL_SIZE = 10;
    private final static String POOL_SIZE_KEY = "db.pool.size";
    public static BlockingQueue<Connection> pool;

    static{
        loadConnectionPool();
    }

    private static void loadConnectionPool() {
        String poolSize = PropertiesUtil.getByKey(POOL_SIZE_KEY);
        int size = poolSize == null ? DEFAULT_POOL_SIZE : Integer.parseInt(poolSize);
        pool = new ArrayBlockingQueue<>(size);

        for(int i = 0; i < size; i++){
            pool.add(open().getConnection());
        }
    }

    public static ConnectionWrapper get() {
        try {
            return new ConnectionWrapper(pool.take());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static ConnectionWrapper open(){
        try {
            Class.forName("org.postgresql.Driver");
            var connection = DriverManager.getConnection(
                    PropertiesUtil.getByKey(URL_KEY),
                    PropertiesUtil.getByKey(USERNAME_KEY),
                    PropertiesUtil.getByKey(PASSWORD_KEY)
            );
            return new ConnectionWrapper(connection);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private ConnectionManager() {
    }

}
