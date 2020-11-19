package com.manager.front2.service;

import com.manager.front2.domain.BookDto;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
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

    public List<BookDto> fetchBooks() {
        String url = "http://localhost:8080/v1/books";
        BookDto[] bookDtos = restTemplate.getForObject(url, BookDto[].class);
        if (bookDtos != null) {
            return Arrays.asList(bookDtos);
        } else {
            return new ArrayList<BookDto>();
        }
    }

    public void createBook(long originId){
        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/books/createBook")
                .queryParam("originId", originId)
                .build()
                .encode()
                .toUri();

        restTemplate.postForObject(url, null, BookDto.class);

    }

}
