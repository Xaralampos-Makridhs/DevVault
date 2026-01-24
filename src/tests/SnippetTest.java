package tests;


import model.Snippet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;


import static org.junit.jupiter.api.Assertions.*;


class SnippetTest {
    private Snippet snippet;


    @BeforeEach
    void setUp() {
        snippet = new Snippet();
        snippet.setTitle("Java Connection");
        snippet.setContent("DriverManager.getConnection(url);");
        snippet.setTags(new ArrayList<>(Arrays.asList("java", "database", "jdbc")));
    }


    @Test
    @DisplayName("Test search matching logic")
    void testMatches() {
        assertTrue(snippet.matches("Java"), "Should match title 'Java'");
        assertTrue(snippet.matches("jdbc"), "Should match tag 'jdbc'");
        assertFalse(snippet.matches("python"), "Should not match 'python'");
    }


    @Test
    @DisplayName("Test tags conversion to String")
    void testGetTagsAsString() {
        String tagsStr = snippet.getTagsAsString();
        assertEquals("java, database, jdbc", tagsStr);
    }


    @Test
    @DisplayName("Test validation logic")
    void testIsValid() {
        assertTrue(snippet.isValid());
        snippet.setTitle("");
        assertFalse(snippet.isValid(), "Snippet with empty title should be invalid");
    }
}

