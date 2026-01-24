package model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


public class Snippet {
    private int id;
    private String title;
    private String content;
    private String language;
    private List<String> tags;
    private LocalDateTime timestamp;


    public Snippet() {
    }

    ;


    
    public Snippet(int id, String title, String content, String language, List<String> tags, LocalDateTime timestamp) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.language = language;
        this.tags = tags != null ? tags : new ArrayList<>();
        this.timestamp = timestamp != null ? timestamp : LocalDateTime.now();
    }



    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getTitle() {
        return title;
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public String getContent() {
        return content;
    }


    public void setContent(String content) {
        this.content = content;
    }


    public String getLanguage() {
        return language;
    }


    public void setLanguage(String language) {
        this.language = language;
    }


    public List<String> getTags() {
        return tags;
    }


    public void setTags(List<String> tags) {
        this.tags = tags;
    }


    public LocalDateTime getTimestamp() {
        return timestamp;
    }


    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }


    public boolean matches(String query) {
        if (query == null || query.isEmpty()) return true;
        String cleanedQuery = query.toLowerCase().trim();


        boolean titleMatch = (title != null) && title.toLowerCase().contains(cleanedQuery);
        boolean contentMatch = (content != null) && content.toLowerCase().contains(cleanedQuery);
        boolean languageMatch = (language != null) && language.toLowerCase().contains(cleanedQuery);


        boolean tagsMatch = false;
        if (tags != null) {
            for (String tag : tags) {
                if (tag.toLowerCase().contains(cleanedQuery)) {
                    tagsMatch = true;
                    break;
                }
            }
        }
        return titleMatch || contentMatch || languageMatch || tagsMatch;
    }


    @Override
    public String toString() {
        return title;
    }

    public String getTagsAsString() {
        if (tags == null || tags.isEmpty()) {
            return "";
        }
        String result = "";
        for (int i = 0; i < tags.size(); i++) {
            result += tags.get(i);
            if (i < tags.size() - 1) {
                result += ", ";
            }
        }
        return result;
    }


    public void setTagsFromString(String tagString) {
        this.tags = new ArrayList<>();
        if (tagString == null || tagString.isEmpty()) return;


        List<String> splitArray = new ArrayList<>();
        String[] parts = tagString.split(",");


        for (String p : parts) {
            String cleanedTag = p.trim();
            if (!cleanedTag.isEmpty()) {
                this.tags.add(cleanedTag);
            }
        }
    }

    public boolean isValid() {


        boolean isTitleValid = (title != null) && !title.trim().isEmpty();
        boolean isContentValid = (content != null) && !content.trim().isEmpty();


        return isTitleValid && isContentValid;
    }
}

