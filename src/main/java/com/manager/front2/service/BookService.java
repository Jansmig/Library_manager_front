package com.manager.front2.service;

import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.BookStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BookService {

    private static BookService bookService;
    @Autowired
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

    public void updateBook(BookDto bookDto){
        long bookId = bookDto.getId();
        String bookStatus = bookDto.getBookStatus().toString();
        String url = "http://localhost:8080/v1/books/setBookStatus/" + bookId + "/" + bookStatus;
        restTemplate.put(url, bookDto, BookDto.class);
    }

    public void updateBookPut(BookDto bookDto){
        long bookId = bookDto.getId();
        String status = bookDto.getBookStatus().toString();
        String url = "http://localhost:8080/v1/books/setStatusPut/updateBook/" + bookId;
        restTemplate.put(url, bookDto);
    }

    public void updateBookPost(BookDto bookDto){
        long bookId = bookDto.getId();
//        URI url = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/v1/books/updateBook/")
//                .queryParam("bookId", bookId)
//                .build()
//                .encode()
//                .toUri();
        String url = "http://localhost:8080/v1/books/updateBook/" + bookId;
        restTemplate.put(url, bookDto, BookDto.class);
    }

    public void deleteBook(BookDto bookDto){
        long bookId = bookDto.getId();
        String url = "http://localhost:8080/v1/books/" + bookId;
        restTemplate.delete(url);
    }

}
