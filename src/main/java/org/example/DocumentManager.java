package org.example;

import lombok.Builder;
import lombok.Data;
import java.time.Instant;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


public class DocumentManager {
    private final Map<String, Document> documents = new HashMap<>();

    public Document save(Document document) {
        if (document.id == null) {
            document.id = UUID.randomUUID().toString();
        }
        documents.put(document.getId(), document);
        return document;
    }

    public List<Document> search(SearchRequest request) {
        return documents.values().stream()
                .filter(document ->
                        (request.titlePrefixes == null || request.titlePrefixes.stream()
                                .anyMatch(prefix -> document.title != null &&
                                        document.title.startsWith(prefix))) &&
                        (request.containsContents == null || request.containsContents.stream()
                                .allMatch(content -> document.content != null &&
                                        document.content.contains(content))) &&
                        (request.authorIds == null || request.authorIds.stream()
                                .anyMatch(id -> id.equals(document.author.id))) &&
                        (request.createdTo == null || document.created.isBefore(request.createdTo)) &&
                        (request.createdFrom == null || document.created.isAfter(request.createdFrom))
                )
                .sorted(Comparator.comparing(Document::getId))
                .toList();
    }

    public Optional<Document> findById(String id) {
       return Optional.ofNullable(documents.get(id));
    }

    @Data
    @Builder
    public static class SearchRequest {
        private List<String> titlePrefixes;
        private List<String> containsContents;
        private List<String> authorIds;
        private Instant createdFrom;
        private Instant createdTo;
    }

    @Data
    @Builder
    public static class Document {
        private String id;
        private String title;
        private String content;
        private Author author;
        private Instant created;
    }

    @Data
    @Builder
    public static class Author {
        private String id;
        private String name;
    }
}
