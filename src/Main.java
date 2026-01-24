import DataBaseHandling.InitializeTable;
import GUI.SnippetGUI;


import javax.swing.*;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        InitializeTable.createTable();
        SwingUtilities.invokeLater(() -> new SnippetGUI().setVisible(true));
    }
}
