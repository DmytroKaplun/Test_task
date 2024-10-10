package org.example;

import java.time.Instant;
import java.util.Arrays;


public class Main {
    public static void main(String[] args) {
        DocumentManager documentManager = new DocumentManager();
        DocumentManager.SearchRequest request = DocumentManager.SearchRequest.builder()
                .titlePrefixes(Arrays.asList("Annual", "Report"))
                .containsContents(Arrays.asList("financial", "report"))
                .authorIds(Arrays.asList("author1", "author2"))
                .createdFrom(Instant.parse("2023-01-11T10:00:00Z"))
                .build();

        DocumentManager.Author author = DocumentManager.Author.builder()
                .id("author1")
                .name("Alice Smith")
                .build();

        DocumentManager.Document doc = DocumentManager.Document.builder()
                .id(null)
                .title("Annual Report 2023")
                .content("This document contains the annual financial report and summary.")
                .author(author)
                .created(Instant.parse("2023-01-15T10:00:00Z"))
                .build();

        System.out.println("save(doc) = " + documentManager.save(doc));
        System.out.println("findById(doc.getId()) = " + documentManager.findById(doc.getId()));
        System.out.println("search(request) = " + documentManager.search(request));
    }
}