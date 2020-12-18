package com.manager.front2.pages;

import com.manager.front2.MainView;
import com.manager.front2.domain.BookDto;
import com.manager.front2.filter.BookFilter;
import com.manager.front2.filter.StatusFilter;
import com.manager.front2.form.BookCreateForm;
import com.manager.front2.form.BookEditForm;
import com.manager.front2.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class BooksPage extends Div {

    private BookService bookService = BookService.getInstance();
    private Grid booksGrid = new Grid<>(BookDto.class);
    private BookFilter bookTitleFilter = new BookFilter(booksGrid);
    private StatusFilter bookStatusFilter = new StatusFilter(booksGrid);
    private Button addNewBook = new Button("Add new Book");
    private HorizontalLayout bookButtons = new HorizontalLayout(bookTitleFilter, bookStatusFilter, addNewBook);
    private BookCreateForm bookCreateForm;
    private BookEditForm bookEditForm;


    public BooksPage(MainView mainView){
       this.bookCreateForm = new BookCreateForm(mainView);
       this.bookEditForm = new BookEditForm(mainView);
       booksGrid.setColumns("id", "originId", "title", "bookStatus");
       for (Object col : booksGrid.getColumns()) {
            if (col instanceof Grid.Column) {
                ((Grid.Column) col).setAutoWidth(true);
            }
        }
       add(booksGrid);
       add(bookButtons);
       add(bookCreateForm);
       add(bookEditForm);
       bookButtons.setPadding(true);
       addNewBook.addClickListener(e -> {
           bookCreateForm.refreshAvailableOrigins();
           bookCreateForm.setVisible(true);
       });
       bookEditForm.setBook(null);
       booksGrid.asSingleSelect().addValueChangeListener(e ->
                bookEditForm.setBook((BookDto) booksGrid.asSingleSelect().getValue()));
       setVisible(false);
    }

    public void refresh(){
        booksGrid.setItems(bookService.fetchBooks());
    }


}
