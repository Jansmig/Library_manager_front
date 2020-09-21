package com.manager.front2.domain.service;

import com.manager.front2.domain.BookDto;
import org.springframework.web.client.RestTemplate;

import java.awt.print.Book;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookService {

    private static BookService bookService;
    private RestTemplate restTemplate = new RestTemplate();
    private List<BookDto> books;

    public static BookService getInstance() {
        if (bookService == null) {
            bookService = new BookService();
        }
        return bookService;
    }

    public BookService() {
        this.books = fetchBooks();
    }

    private List<BookDto> fetchBooks() {
        String url = "http://localhost:8080/v1/books";
        BookDto[] bookDtos = restTemplate.getForObject(url, BookDto[].class);
        if (bookDtos != null) {
            return Arrays.asList(bookDtos);
        } else {
            return new ArrayList<BookDto>();
        }
    }

    public List<BookDto> getBooks() {
        return new ArrayList<>(books);
    }
}
