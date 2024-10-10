package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DocumentManagerTest {
    DocumentManager documentManager;
    List<DocumentManager.Document> testDocuments;
    
    @BeforeEach
    void setUp() {
        documentManager = new DocumentManager();
        testDocuments = generateDocs();

        testDocuments.forEach(doc -> {
            documentManager.save(doc);
        });
    }

    @Test
    void testSaveAndFindById() {
        DocumentManager.Document testDocument = DocumentManager.Document.builder()
                .id("test")
                .content("content")
                .build();
        documentManager.save(testDocument);
        assertEquals(testDocument, documentManager.findById("test").orElseThrow());
    }

    @Test
    void testSearchByTitlePrefix() {
        DocumentManager.SearchRequest request = DocumentManager.SearchRequest.builder()
                .titlePrefixes(Arrays.asList("Annual"))
                .containsContents(null)
                .authorIds(null)
                .createdFrom(null)
                .createdTo(null)
                .build();

        List<DocumentManager.Document> list = documentManager.search(request);
        assertEquals(2, list.size());
        assertEquals("doc1", list.get(0).getId());
        assertEquals("doc6", list.get(1).getId());
    }

    @Test
    public void testSearchByMultipleCriteria() {
        DocumentManager.SearchRequest request = DocumentManager.SearchRequest.builder()
                .titlePrefixes(Arrays.asList("Project"))
                .containsContents(Arrays.asList("launched"))
                .authorIds(null)
                .createdFrom(Instant.parse("2023-01-01T00:00:00Z"))
                .createdTo(Instant.parse("2023-12-31T23:59:59Z"))
                .build();

        List<DocumentManager.Document> results = documentManager.search(request);

        assertEquals(1, results.size());
        assertEquals("Project Plan for New Initiative", results.get(0).getTitle());
    }

    private List<DocumentManager.Document> generateDocs() {
        DocumentManager.Author author1 = DocumentManager.Author.builder()
                .id("author1")
                .name("Alice Smith")
                .build();

        DocumentManager.Author author2 = DocumentManager.Author.builder()
                .id("author2")
                .name("Bob Johnson")
                .build();

        DocumentManager.Author author3 = DocumentManager.Author.builder()
                .id("author3")
                .name("Charlie Brown")
                .build();

        return List.of(DocumentManager.Document.builder()
                        .id("doc1")
                        .title("Annual Report 2023")
                        .content("This document contains the annual financial report and summary.")
                        .author(author1)
                        .created(Instant.parse("2023-01-15T10:00:00Z"))
                        .build(),
                DocumentManager.Document.builder()
                        .id("doc2")
                        .title("Project Plan for New Initiative")
                        .content("Detailed project plan for the new initiative launched this year.")
                        .author(author2)
                        .created(Instant.parse("2023-02-20T11:00:00Z"))
                        .build(),
                DocumentManager.Document.builder()
                        .id("doc3")
                        .title("Research Paper on AI Trends")
                        .content("This paper explores recent trends in artificial intelligence.")
                        .author(author3)
                        .created(Instant.parse("2023-03-10T14:30:00Z"))
                        .build(),
                DocumentManager.Document.builder()
                        .id("doc4")
                        .title("Meeting Minutes - Q1 Review")
                        .content("Minutes from the Q1 review meeting discussing key performance indicators.")
                        .author(author1)
                        .created(Instant.parse("2023-04-05T09:00:00Z"))
                        .build(),
                DocumentManager.Document.builder()
                        .id("doc5")
                        .title("User Feedback Analysis")
                        .content("Analysis of user feedback and recommendations for improvements.")
                        .author(author2)
                        .created(Instant.parse("2023-05-25T15:45:00Z"))
                        .build(),
                DocumentManager.Document.builder()
                        .id("doc6")
                        .title("Annual Report 2022")
                        .content("This document contains the annual financial report for the previous year.")
                        .author(author3)
                        .created(Instant.parse("2022-01-15T10:00:00Z"))
                        .build(),
                DocumentManager.Document.builder()
                        .id("doc7")
                        .title("AI and Machine Learning")
                        .content("Exploring the implications of AI and machine learning in modern society.")
                        .author(author1)
                        .created(Instant.parse("2023-06-10T12:00:00Z"))
                        .build(),
                DocumentManager.Document.builder()
                        .id("doc8")
                        .title("Survey Results on Product Satisfaction")
                        .content("Results of the survey conducted on customer satisfaction with our products.")
                        .author(author2)
                        .created(Instant.parse("2023-07-20T08:30:00Z"))
                        .build());
    }
}