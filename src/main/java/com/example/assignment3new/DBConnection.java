package com.example.assignment3new;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    static final private String USER = "root";
    static final private String PASS = "gordie28";
    static final String JDBC_DRIVER = "org.mariadb.jdbc.Driver";
    static final private String DB_URL = "jdbc:mariadb://localhost:3306/books";
    static private Driver d = null;


    public static Connection initDatabase() throws SQLException, ClassNotFoundException {
        // Class.forName(JDBC_DRIVER);
        // Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);

        d = new org.mariadb.jdbc.Driver();
        DriverManager.registerDriver(d);
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
        return conn;

    }
}
