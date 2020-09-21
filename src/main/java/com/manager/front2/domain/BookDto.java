package com.manager.front2.domain;

public class BookDto {

    private long id;
    private long originId;
    private String title;
    private BookStatus bookStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOriginId() {
        return originId;
    }

    public void setOriginId(long originId) {
        this.originId = originId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public BookDto(long id, long originId, String title, BookStatus bookStatus) {
        this.id = id;
        this.originId = originId;
        this.title = title;
        this.bookStatus = bookStatus;
    }

    public BookDto() {
    }
}
