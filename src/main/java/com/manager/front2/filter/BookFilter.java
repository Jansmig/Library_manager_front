package com.manager.front2.filter;


import com.manager.front2.domain.BookDto;
import com.manager.front2.service.BookService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.stream.Collectors;

public class BookFilter extends TextField {

    private BookService bookService = BookService.getInstance();
    private Grid grid;

    public BookFilter(Grid grid) {
        this.setPlaceholder("Filter by title");
        this.setClearButtonVisible(true);
        this.setValueChangeMode(ValueChangeMode.EAGER);
        this.grid = grid;
        this.addValueChangeListener(e -> updateFilteredBooks());
    }

    private void updateFilteredBooks() {
        List<BookDto> bookDtoList = bookService.fetchBooks();
        bookDtoList = bookDtoList.stream()
                .filter(bookDto -> bookDto.getTitle().toLowerCase().contains(this.getValue().toLowerCase()))
                .collect(Collectors.toList());

        this.grid.setItems(bookDtoList);
    }

}
