<%@ page import="com.chargehive.config.DbConfig, java.sql.*" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Database Connection Test</title>
    <style>
        .success { color: green; }
        .error { color: red; }
        pre { background: #f5f5f5; padding: 10px; }
    </style>
</head>
<body>
    <h1>Database Connection Test</h1>
    
    <%
    try {
        Connection conn = DbConfig.getDbConnection();
        out.println("<p class='success'>✅ Connection successful!</p>");
        
        // Display connection details
        DatabaseMetaData meta = conn.getMetaData();
    %>
        <h3>Connection Details:</h3>
        <ul>
            <li>Database: <%= conn.getCatalog() %></li>
            <li>URL: <%= meta.getURL() %></li>
            <li>Driver: <%= meta.getDriverName() %> v<%= meta.getDriverVersion() %></li>
            <li>Auto-commit: <%= conn.getAutoCommit() %></li>
        </ul>
        
        <h3>Test Query:</h3>
    <%
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT 1+1 AS test_result")) {
            if (rs.next()) {
                out.println("<p>Query result: " + rs.getInt("test_result") + "</p>");
            }
        }
        
        // Show tables if you want
        out.println("<h3>Tables in Database:</h3>");
        ResultSet tables = meta.getTables(null, null, "%", new String[]{"TABLE"});
        while (tables.next()) {
            out.println("<li>" + tables.getString("TABLE_NAME") + "</li>");
        }
        
        conn.close();
    } catch (Exception e) {
        out.println("<p class='error'>❌ Connection failed!</p>");
        out.println("<pre>");
        e.printStackTrace(new java.io.PrintWriter(out));
        out.println("</pre>");
    }
    %>
</body>
</html>