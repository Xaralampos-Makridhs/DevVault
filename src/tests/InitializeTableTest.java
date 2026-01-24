package tests;


import DataBaseHandling.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.assertTrue;


class InitializeTableTest {
    @Test
    @DisplayName("Initialize Tables")
    void testTablesExit() throws SQLException{
        try(Connection conn= DatabaseConnection.getConnection()){
            DatabaseMetaData meta=conn.getMetaData();


            ResultSet rs=meta.getTables(null,null,"snippets",null);
            assertTrue(rs.next(),"Snippets table must have created");
        }
    }
}
