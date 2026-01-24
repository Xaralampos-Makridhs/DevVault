package DataBaseHandling;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;


public final class DatabaseConnection {
    private static final String DB_URL="jdbc:sqlite:DevVault.db";


    public static Connection getConnection(){
        try{
            Connection conn= DriverManager.getConnection(DB_URL);
            enableForeignKeys(conn);
            return conn;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    private static void enableForeignKeys(Connection connection) throws SQLException{
        try(Statement statement= connection.createStatement()){
            statement.execute("PRAGMA foreign_keys= ON");
        }
    }
}
