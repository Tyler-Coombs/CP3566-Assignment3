package com.example.assignment3new;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Test {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Connection conn = DBConnection.initDatabase();
        Statement stmt = conn.createStatement();
        String sql = "SELECT * FROM titles";

        ResultSet rs = stmt.executeQuery(sql);

        while (rs.next()) {
            System.out.println(rs.getString("title"));
        }

    }
}
