package tests;




import DataBaseHandling.DatabaseConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.sql.Connection;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;


class DatabaseConnectionTest {
    @Test
    @DisplayName("Check if connection done.")
    void testGetConnection(){
        try(Connection connection= DatabaseConnection.getConnection()){
            assertNotNull(connection,"Connection must not be null");
            assertFalse(connection.isClosed(),"Connection must be open");
            System.out.println("Connection Done");
        } catch (SQLException e) {
            fail("Faield to connect"+e.getMessage());
        }


    }


}
