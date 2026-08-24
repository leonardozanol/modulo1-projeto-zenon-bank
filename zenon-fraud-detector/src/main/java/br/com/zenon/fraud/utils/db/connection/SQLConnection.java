package br.com.zenon.fraud.utils.db.connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/zenonDB?rewriteBatchedStatements=true";
    private static final String USER = "root";
    private static final String PASSWORD = System.getenv("MYSQL_ROOT_PASSWORD");

    public static Connection get() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

}
