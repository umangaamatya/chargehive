package com.chargehive.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {
    // Try BOTH ports (3307 and 3306)
    private static final String[] PORTS_TO_TRY = {"3307"};
    private static final String DB_NAME = "chargehive";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public static Connection getDbConnection() throws SQLException {
        SQLException lastException = null;

        // Try all ports in sequence
        for (String port : PORTS_TO_TRY) {
            String url = buildConnectionUrl(port);
            System.out.println("Attempting connection to: " + url);
            
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection conn = DriverManager.getConnection(url, USERNAME, PASSWORD);
                System.out.println("✅ Successfully connected to port " + port);
                return conn;
            } catch (SQLException e) {
                lastException = e;
                System.err.println("❌ Failed on port " + port + ": " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("MySQL JDBC Driver not found!", e);
            }
        }

        throw new SQLException("Failed to connect to all ports", lastException);
    }

    private static String buildConnectionUrl(String port) {
        return "jdbc:mysql://localhost:" + port + "/" + DB_NAME
                + "?useSSL=false"
                + "&allowPublicKeyRetrieval=true"
                + "&autoReconnect=true"
                + "&connectTimeout=5000"
                + "&socketTimeout=30000";
    }

    public static void testConnection() {
        try (Connection conn = getDbConnection()) {
            System.out.println("Connection test successful!");
            System.out.println("Database: " + conn.getCatalog());
            System.out.println("Auto-commit: " + conn.getAutoCommit());
        } catch (Exception e) {
            System.err.println("Connection test failed!");
            e.printStackTrace();
        }
    }
}