package daoMethods;


import DataBaseHandling.DatabaseConnection;
import model.Snippet;


import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class SnippetDAO {
    public void addSnippet(Snippet snippet){
        String sql="INSERT INTO snippets(title,snippet_code,programming_language,tags,timestamp) VALUES (?,?,?,?,?)";


        try(Connection conn= DatabaseConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){


            pstmt.setString(1,snippet.getTitle());
            pstmt.setString(2,snippet.getContent());
            pstmt.setString(3,snippet.getLanguage());
            pstmt.setString(4, String.join(",", snippet.getTags()));
            pstmt.setString(5,snippet.getTimestamp().toString());


            pstmt.executeUpdate();


            try(ResultSet rs= pstmt.getGeneratedKeys()){
                if(rs.next()){
                    snippet.setId(rs.getInt(1));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public boolean deleteById(int id){
        String sql="DELETE FROM snippets WHERE id=?";


        try(Connection conn=DatabaseConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){


            pstmt.setInt(1,id);
            int affectedRows=pstmt.executeUpdate();


            return affectedRows>0;


        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }


    public Snippet findByTitle(String title){
        String sql="SELECT * FROM snippets WHERE title=?";
        Snippet snippet=null;


        try(Connection conn=DatabaseConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql)){


            pstmt.setString(1,title);


            try(ResultSet rs=pstmt.executeQuery()){
                if(rs.next()){
                    snippet=mapRowToSnippet(rs);
                }
            }


        }catch(SQLException e) {
            throw new RuntimeException(e);
        }
        return snippet;
    }


    public List<Snippet> findAll(){
        List<Snippet> snippets=new ArrayList<>();
        String sql="SELECT * FROM snippets";


        try(Connection conn=DatabaseConnection.getConnection();
            PreparedStatement pstmt=conn.prepareStatement(sql);
            ResultSet rs=pstmt.executeQuery()){
            while(rs.next()){
                snippets.add(mapRowToSnippet(rs));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return snippets;
    }


    public boolean updateSnippetByTitle(Snippet snippet, String oldTitle) {


        String sql = "UPDATE snippets SET title = ?, snippet_code = ?, programming_language = ?, tags = ?, timestamp = ? WHERE title = ?";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            pstmt.setString(1, snippet.getTitle());
            pstmt.setString(2, snippet.getContent());
            pstmt.setString(3, snippet.getLanguage());
            pstmt.setString(4, String.join(",", snippet.getTags()));
            pstmt.setString(5, LocalDateTime.now().toString()); // νέο timestamp
            pstmt.setString(6, oldTitle);


            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;


        } catch (SQLException e) {
            throw new RuntimeException("Error updating snippet with title: " + oldTitle, e);
        }
    }


    public boolean deleteAll() {


        String sql = "DELETE FROM snippets";


        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {


            int affectedRows = pstmt.executeUpdate();


            return affectedRows > 0;


        } catch (SQLException e) {
            throw new RuntimeException("Error deleting all snippets", e);
        }
    }








    private Snippet mapRowToSnippet(ResultSet rs) throws SQLException {
        Snippet snippet = new Snippet();


        snippet.setId(rs.getInt("id"));
        snippet.setTitle(rs.getString("title"));
        snippet.setContent(rs.getString("snippet_code"));
        snippet.setLanguage(rs.getString("programming_language"));


        String tagsFromDb = rs.getString("tags");


        if (tagsFromDb != null && !tagsFromDb.isEmpty()) {
            List<String> tagsList = Arrays.asList(tagsFromDb.split("\\s*,\\s*"));
            snippet.setTags(tagsList);
        } else {
            snippet.setTags(new ArrayList<>());
        }


        snippet.setTimestamp(rs.getObject("timestamp", LocalDateTime.class));


        return snippet;
    }
}
