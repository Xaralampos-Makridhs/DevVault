package DataBaseHandling;


import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;


public class InitializeTable {
    public static void createTable() throws SQLException{
        String sql= """
               CREATE TABLE IF NOT EXISTS snippets(
                  id INTEGER PRIMARY KEY AUTOINCREMENT,
                  title TEXT NOT NULL UNIQUE,
                  snippet_code TEXT NOT NULL,
                  programming_language TEXT NOT NULL,
                  tags TEXT,
                  timestamp DATETIME NOT NULL
               );
               """;


        try(Connection conn=DatabaseConnection.getConnection();
            Statement statement=conn.createStatement()){
            statement.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
