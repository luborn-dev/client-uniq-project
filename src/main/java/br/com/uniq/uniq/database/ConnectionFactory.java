package br.com.uniq.uniq.database;

import java.sql.*;
import java.util.logging.Logger;

public class ConnectionFactory {

    public static Connection getConnection() {
        String url = "jdbc:sqlserver://uniq-project.database.windows.net:1433;database=uniq;loginTimeout=45";
        String user = "uniq-admin";
        String password = "Projetointegrador4";

        try {
            return DriverManager.getConnection(url, user, password);

        } catch (Exception e) {
            System.out.println(e);
            Logger.getLogger("Erro ao conectar ao banco de dados, exception: " + e.getMessage());
        }
        return null;
    }

    public static void close(Connection connection) {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection, Statement stmt) {
        close(connection);
        try {
            if (stmt != null)
                stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void close(Connection connection, Statement stmt, ResultSet rs) {
        close(connection, stmt);
        try {
            if (rs != null)
                rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
