package com.manager.front2.filter;

import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.BookStatus;
import com.manager.front2.service.BookService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.stream.Collectors;

public class StatusFilter extends ComboBox<BookStatus> {

    private BookService bookService = BookService.getInstance();
    private Grid grid;

    public StatusFilter(Grid grid){
        this.setPlaceholder("Filter by status");
        this.setClearButtonVisible(true);
        this.setItems(BookStatus.values());
        this.grid = grid;
        this.addValueChangeListener(e -> updateFilteredBooksByStatus());
    }

    private void updateFilteredBooksByStatus(){
        List<BookDto> bookDtoList = bookService.fetchBooks();
        bookDtoList = bookDtoList.stream()
                .filter(book -> book.getBookStatus().equals(this.getValue()))
                .collect(Collectors.toList());

        if (this.getValue() == null) {
            this.grid.setItems(bookService.fetchBooks());
        } else {
            this.grid.setItems(bookDtoList);
        }
    }

}
