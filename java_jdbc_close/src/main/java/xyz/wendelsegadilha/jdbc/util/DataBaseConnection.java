package xyz.wendelsegadilha.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static final String url = "jdbc:mysql://localhost:3306/java_curso?serverTimezone=America/Sao_Paulo";
    private static final String username = "root";
    private static final String password = "root";
    private static Connection connection;

    public static Connection getInstace() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

}
