package GUI;


import daoMethods.SnippetDAO;
import model.Snippet;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


public class SnippetGUI extends JFrame {
    private final SnippetDAO dao = new SnippetDAO();
    private String currentSelectedTitle = null;


    // Explicitly defined text fields for user input
    private JTextField txtTitle = new JTextField(30);
    private JTextField txtLanguage = new JTextField(30);
    private JTextField txtTags = new JTextField(30);
    private JTextField txtSearch = new JTextField(20);
    private JTextArea txtContent = new JTextArea(10, 30);


    private JTable table;
    private DefaultTableModel tableModel;


    public SnippetGUI() {
        setTitle("Code Snippet Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 750);
        setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));


        // --- 1. INPUT FORM AREA ---
        JPanel formContainer = new JPanel();
        formContainer.setLayout(new BoxLayout(formContainer, BoxLayout.Y_AXIS));
        formContainer.setBorder(BorderFactory.createTitledBorder("Snippet Editor"));


        // Adding labeled fields
        formContainer.add(createLabelAndField("Title:", txtTitle));
        formContainer.add(createLabelAndField("Programming Language:", txtLanguage));
        formContainer.add(createLabelAndField("Tags (comma separated):", txtTags));


        // Code Area
        JPanel contentPanel = new JPanel(new BorderLayout(5, 5));
        contentPanel.add(new JLabel("Code Content:"), BorderLayout.NORTH);
        txtContent.setFont(new Font("Monospaced", Font.PLAIN, 13));
        contentPanel.add(new JScrollPane(txtContent), BorderLayout.CENTER);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        formContainer.add(contentPanel);


        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        JButton btnAdd = new JButton("Add Snippet");
        JButton btnUpdate = new JButton("Update Selected");
        JButton btnDelete = new JButton("Delete");
        JButton btnClear = new JButton("Clear Form");


        buttonPanel.add(btnAdd);
        buttonPanel.add(btnUpdate);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnClear);
        formContainer.add(buttonPanel);


        mainPanel.add(formContainer, BorderLayout.NORTH);


        // --- 2. SEARCH & TABLE AREA ---
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));


        // Search Section
        JPanel searchBox = new JPanel(new BorderLayout(10, 0));
        JButton btnSearch = new JButton("Search");
        JButton btnShowAll = new JButton("Show All");


        JPanel searchButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        searchButtons.add(btnSearch);
        searchButtons.add(btnShowAll);


        searchBox.add(new JLabel("Search Snippets:"), BorderLayout.WEST);
        searchBox.add(txtSearch, BorderLayout.CENTER);
        searchBox.add(searchButtons, BorderLayout.EAST);
        bottomPanel.add(searchBox, BorderLayout.NORTH);


        // Table
        String[] columns = {"ID", "Title", "Language", "Tags", "Timestamp"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        table = new JTable(tableModel);
        bottomPanel.add(new JScrollPane(table), BorderLayout.CENTER);


        mainPanel.add(bottomPanel, BorderLayout.CENTER);
        add(mainPanel);


        // --- ACTIONS ---
        refreshTable(dao.findAll());


        btnAdd.addActionListener(e -> {
            Snippet s = getSnippetFromUI();
            if (s.isValid()) {
                dao.addSnippet(s);
                refreshTable(dao.findAll());
                clearFields();
            } else {
                JOptionPane.showMessageDialog(this, "Title and Content cannot be empty!");
            }
        });


        btnUpdate.addActionListener(e -> {
            if (currentSelectedTitle != null) {
                dao.updateSnippetByTitle(getSnippetFromUI(), currentSelectedTitle);
                refreshTable(dao.findAll());
            }
        });


        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                dao.deleteById((int) tableModel.getValueAt(row, 0));
                refreshTable(dao.findAll());
                clearFields();
            }
        });


        btnSearch.addActionListener(e -> {
            String q = txtSearch.getText().trim();
            List<Snippet> filtered = dao.findAll().stream()
                    .filter(s -> s.matches(q))
                    .collect(Collectors.toList());
            refreshTable(filtered);
        });


        btnShowAll.addActionListener(e -> {
            txtSearch.setText("");
            refreshTable(dao.findAll());
        });


        btnClear.addActionListener(e -> clearFields());


        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1 && !e.getValueIsAdjusting()) {
                String title = tableModel.getValueAt(row, 1).toString();
                Snippet s = dao.findByTitle(title);
                if (s != null) {
                    txtTitle.setText(s.getTitle());
                    txtLanguage.setText(s.getLanguage());
                    txtTags.setText(s.getTagsAsString());
                    txtContent.setText(s.getContent());
                    currentSelectedTitle = s.getTitle();
                }
            }
        });
    }


    // Helper to build a clean row for input fields
    private JPanel createLabelAndField(String labelText, JTextField field) {
        JPanel p = new JPanel(new BorderLayout(5, 5));
        p.setBorder(BorderFactory.createEmptyBorder(2, 5, 2, 5));
        JLabel lbl = new JLabel(labelText);
        lbl.setPreferredSize(new Dimension(150, 25));
        p.add(lbl, BorderLayout.WEST);
        p.add(field, BorderLayout.CENTER);
        return p;
    }


    private void refreshTable(List<Snippet> list) {
        tableModel.setRowCount(0);
        for (Snippet s : list) {
            tableModel.addRow(new Object[]{s.getId(), s.getTitle(), s.getLanguage(), s.getTagsAsString(), s.getTimestamp()});
        }
    }


    private Snippet getSnippetFromUI() {
        Snippet s = new Snippet();
        s.setTitle(txtTitle.getText());
        s.setLanguage(txtLanguage.getText());
        s.setContent(txtContent.getText());
        s.setTagsFromString(txtTags.getText());
        s.setTimestamp(LocalDateTime.now());
        return s;
    }


    private void clearFields() {
        txtTitle.setText("");
        txtLanguage.setText("");
        txtTags.setText("");
        txtContent.setText("");
        currentSelectedTitle = null;
        table.clearSelection();
    }
}
