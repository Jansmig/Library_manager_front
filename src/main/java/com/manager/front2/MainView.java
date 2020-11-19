package com.manager.front2;

import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.OriginDto;
import com.manager.front2.domain.RentalDto;
import com.manager.front2.domain.UserDto;
import com.manager.front2.filter.BookFilter;
import com.manager.front2.filter.OriginFilter;
import com.manager.front2.filter.StatusFilter;
import com.manager.front2.form.BookCreateForm;
import com.manager.front2.form.OriginForm;
import com.manager.front2.service.BookService;
import com.manager.front2.service.OriginService;
import com.manager.front2.service.RentalService;
import com.manager.front2.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {

    private OriginService originService = OriginService.getInstance();
    private BookService bookService = BookService.getInstance();
    private UserService userService = UserService.getInstance();
    private RentalService rentalService = RentalService.getInstance();
    private Grid originsGrid = new Grid<>(OriginDto.class);
    private Grid booksGrid = new Grid<>(BookDto.class);
    private Grid usersGrid = new Grid<>(UserDto.class);
    private Grid rentalsGrid = new Grid<>(RentalDto.class);
    private OriginFilter originFilter = new OriginFilter(originsGrid);
    private OriginForm originForm = new OriginForm(this);
    private Button addNewOrigin = new Button("Add new Origin");
    private BookFilter bookFilter = new BookFilter(booksGrid);
    private StatusFilter statusFilter = new StatusFilter(booksGrid);
    private Button addNewBook = new Button("Add new Book");
    private BookCreateForm bookCreateForm = new BookCreateForm(this);


    public MainView() {
        originsGrid.setColumns("id", "title", "author", "publishedYear", "isbn");
        booksGrid.setColumns("id", "originId", "title", "bookStatus");
        usersGrid.setColumns("id", "firstName", "lastName", "email", "userCreationDate");
        rentalsGrid.setColumns("id", "active", "bookId", "bookTitle", "userId", "userFirstName", "userLastName", "rentalDate", "returnDate");
        originForm.setOrigin(null);

        addNewOrigin.addClickListener(event -> {
            originForm.setOrigin(new OriginDto());
        });

        Tab originsTab = new Tab("Origins");
        Div originPage = new Div();
        originPage.add(originsGrid);
        originPage.add(originFilter);
        originPage.add(addNewOrigin);
        originPage.add(originForm);

        Tab booksTab = new Tab("Books");
        Div booksPage = new Div();
        booksPage.add(booksGrid);
        booksPage.add(bookFilter);
        booksPage.add(statusFilter);
        booksPage.add(addNewBook);
        addNewBook.addClickListener(e -> bookCreateForm.setVisible(true));
        booksPage.add(bookCreateForm);
        booksPage.setVisible(false);

        Tab usersTab = new Tab("Users");
        Div usersPage = new Div();
        usersPage.add(usersGrid);
        usersPage.setVisible(false);

        Tab rentalsTab = new Tab("Rentals");
        Div rentalsPage = new Div();
        rentalsPage.add(rentalsGrid);
        rentalsPage.setVisible(false);

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(originsTab, originPage);
        tabsToPages.put(booksTab, booksPage);
        tabsToPages.put(rentalsTab, rentalsPage);
        tabsToPages.put(usersTab, usersPage);
        Tabs tabs = new Tabs(originsTab, booksTab, usersTab, rentalsTab);
        Div pages = new Div(originPage, booksPage, usersPage, rentalsPage);

        tabs.addSelectedChangeListener(event -> {
            tabsToPages.values().forEach(page -> page.setVisible(false));
            Component selectedPage = tabsToPages.get(tabs.getSelectedTab());
            selectedPage.setVisible(true);
        });

        pages.setSizeFull();
        add(tabs, pages);

        refresh();

        originsGrid.asSingleSelect().addValueChangeListener(event -> originForm.setOrigin((OriginDto) originsGrid.asSingleSelect().getValue()));
    }

    public void refresh() {
        originsGrid.setItems(originService.fetchOrigins());
        booksGrid.setItems(bookService.fetchBooks());
        usersGrid.setItems(userService.fetchUsers());
        rentalsGrid.setItems(rentalService.fetchRentals());
    }

}
