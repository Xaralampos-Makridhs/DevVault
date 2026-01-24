package daoMethods;


import model.Snippet;
import org.junit.jupiter.api.*;


import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SnippetDAOTest {


    private static SnippetDAO dao;
    private static final String testTitle = "JUnit Test Snippet";


    @BeforeAll
    static void setup() {
        dao = new SnippetDAO();
    }


    @Test
    @Order(1)
    @DisplayName("Should insert a snippet and generate an ID")
    void testAddSnippet() {
        Snippet snippet = new Snippet();
        snippet.setTitle(testTitle);
        snippet.setContent("System.out.println('Hello JUnit');");
        snippet.setLanguage("Java");
        snippet.setTags(Arrays.asList("test", "junit", "java"));
        snippet.setTimestamp(LocalDateTime.now());


        dao.addSnippet(snippet);


        assertNotEquals(0, snippet.getId(), "ID should not be 0 after insertion");
    }


    @Test
    @Order(2)
    @DisplayName("Should update an existing snippet by its title")
    void testUpdateSnippetByTitle() {


        Snippet snippet = dao.findByTitle(testTitle);
        assertNotNull(snippet, "Snippet should exist to be updated");


        snippet.setLanguage("JavaScript");
        snippet.setTags(Arrays.asList("updated", "web", "js"));
        snippet.setContent("console.log('Updated!');");


        String currentTitle = snippet.getTitle();


        boolean isUpdated = dao.updateSnippetByTitle(snippet, currentTitle);
        assertTrue(isUpdated, "Update operation should return true");


        Snippet updatedSnippet = dao.findByTitle(currentTitle);
        assertNotNull(updatedSnippet);


        assertEquals("JavaScript", updatedSnippet.getLanguage());
        assertEquals(3, updatedSnippet.getTags().size());
        assertTrue(updatedSnippet.getTags().contains("web"));
        assertEquals("console.log('Updated!');", updatedSnippet.getContent());
    }


    @Test
    @Order(3)
    @DisplayName("Should retrieve all snippets from the database")
    void testFindAll() {
        List<Snippet> all = dao.findAll();
        assertFalse(all.isEmpty(), "The list of snippets should not be empty");
    }


    @Test
    @Order(4)
    @DisplayName("Should delete the snippet by its ID")
    void testDeleteById() {


        Snippet toDelete = dao.findByTitle(testTitle);
        assertNotNull(toDelete);


        boolean isDeleted = dao.deleteById(toDelete.getId());
        assertTrue(isDeleted, "Delete operation should return true");


        Snippet afterDelete = dao.findByTitle(testTitle);
        assertNull(afterDelete, "Snippet should be null after deletion");
    }
}
