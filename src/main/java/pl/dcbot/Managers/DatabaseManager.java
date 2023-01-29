package pl.dcbot.Managers;

import com.zaxxer.hikari.HikariDataSource;
import pl.dcbot.DragonBotBridge;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetFactory;
import javax.sql.rowset.RowSetProvider;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseManager {

    private static final DragonBotBridge plugin = DragonBotBridge.getInstance();

    private static HikariDataSource ds;

    private static DatabaseManager instance = null;
    private static Connection connection = null;

    static String host = plugin.getConfig().getString("host");
    static String database = plugin.getConfig().getString("baza");
    static String password = plugin.getConfig().getString("haslo");
    static String tabela = plugin.getConfig().getString("tabela");
    static int port = plugin.getConfig().getInt("port");

    private RowSetFactory factory;

    public static synchronized DatabaseManager get() {
        return instance == null ? instance = new DatabaseManager() : instance;
    }

    public static void openConnection() {
        try {

            if (getConnection() == null || getConnection().isClosed()) {

                ds = new HikariDataSource();
                ds.setJdbcUrl("jdbc:mysql://" + host + ":" + port + "/" + database);
                ds.setUsername(database);
                ds.setPassword(password);
                ds.addDataSourceProperty("cachePrepStmts", "true");
                ds.addDataSourceProperty("useSSL", "false");
                ds.addDataSourceProperty("verifyServerCertificate", "false");
                ds.setMaximumPoolSize(4);

                synchronized(plugin) {
                    if (getConnection() == null || getConnection().isClosed()) {
                        connection = ds.getConnection();
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void closeConnection() {
        try {
            if (getConnection() == null || getConnection().isClosed()) {
                getConnection().close();
                ds.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private CachedRowSet createCachedRowSet() throws SQLException {
        if (factory == null) {
            factory = RowSetProvider.newFactory();
        }
        return factory.createCachedRowSet();
    }


    public void executeStatement(String sql, Object... parameters) {
        executeStatement(sql, false, parameters);
    }

    public ResultSet executeResultStatement(String sql, Object... parameters) {
        return executeStatement(sql, true, parameters);
    }

    private synchronized ResultSet executeStatement(String sql, boolean result, Object... parameters) {
        try (Connection connection = ds.getConnection(); PreparedStatement statement = connection.prepareStatement(sql)) {

            for (int i = 0; i < parameters.length; i++) {
                statement.setObject(i + 1, parameters[i]);
            }

            if (result) {
                CachedRowSet results = createCachedRowSet();
                results.populate(statement.executeQuery());
                return results;
            }
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static Connection getConnection() {
        try {
            if(connection != null && !connection.isClosed() && ds.isRunning()) {
                return connection;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
