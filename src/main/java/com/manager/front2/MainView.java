package com.manager.front2;

import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.OriginDto;
import com.manager.front2.domain.RentalDto;
import com.manager.front2.domain.UserDto;
import com.manager.front2.filter.*;
import com.manager.front2.form.*;
import com.manager.front2.service.BookService;
import com.manager.front2.service.OriginService;
import com.manager.front2.service.RentalService;
import com.manager.front2.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
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
    private Button getAllRatings = new Button("Download all ratings");
    private HorizontalLayout originButtons = new HorizontalLayout(originFilter, addNewOrigin, getAllRatings);
    private Details instructions = new Details("Details & instruction", new Text(""));
    private BookFilter bookTitleFilter = new BookFilter(booksGrid);
    private StatusFilter bookStatusFilter = new StatusFilter(booksGrid);
    private Button addNewBook = new Button("Add new Book");
    private HorizontalLayout bookButtons = new HorizontalLayout(bookTitleFilter, bookStatusFilter, addNewBook);
    private BookCreateForm bookCreateForm = new BookCreateForm(this);
    private BookEditForm bookEditForm = new BookEditForm(this);
    private UserForm userForm = new UserForm(this);
    private Button addNewUser = new Button("Add new User");
    private HorizontalLayout userButtons = new HorizontalLayout(addNewUser);
    private Button addNewRental = new Button("Add new Rental");
    private CreateRentalForm createRentalForm = new CreateRentalForm(this);
    private EditRentalForm editRentalForm = new EditRentalForm(this);
    private RentalLastnameFilter rentalLastnameFilter = new RentalLastnameFilter(rentalsGrid);
    private RentalStatusFilter rentalStatusFilter = new RentalStatusFilter(rentalsGrid);
    private HorizontalLayout rentalButtons = new HorizontalLayout(rentalLastnameFilter, rentalStatusFilter, addNewRental);


    public MainView() {
        originsGrid.setColumns("id", "title", "author", "publishedYear", "isbn", "rating");
        originsGrid.getColumnByKey("rating").setHeader("Goodreads rating");
        booksGrid.setColumns("id", "originId", "title", "bookStatus");
        usersGrid.setColumns("id", "firstName", "lastName", "email", "userCreationDate");
        rentalsGrid.setColumns("id", "active", "bookId", "bookTitle", "userId", "userFirstName", "userLastName", "rentalDate", "returnDate");
        alignGridColumns(originsGrid);
        alignGridColumns(booksGrid);
        alignGridColumns(usersGrid);
        alignGridColumns(rentalsGrid);

        Tab originsTab = new Tab("Origins");
        Div originPage = new Div();
        originForm.setOrigin(null);
        originPage.add(originsGrid);
        originPage.add(originButtons);
        originButtons.setPadding(true);
        addNewOrigin.addClickListener(event -> {
            originForm.setOrigin(new OriginDto());
        });
        getAllRatings.addClickListener(e -> {
            originService.updateAllGoodreadsRatings();
            refresh();
        });
        originsGrid.asSingleSelect().addValueChangeListener(event ->
                originForm.setOrigin((OriginDto) originsGrid.asSingleSelect().getValue()));
        originPage.add(originForm);
        instructions.setContent(new Text("Please note that the application will not allow for actions violating integrity constraints, such as deletion of objects if there are other objects depending on it. Example: it is not possible to delete an origin if there are rentals of books of that origin. First related rentals need to be deleted, then it is possible to delete the origin (which will also delete all the books of that origin). There are also field constraints and validators checking for example if provided email address is valid, whether given ISBN already exists in the database etc.\n" +
                "To edit or reveal/hide additional functionalities relating existing objects, simply click/unclick a record in the table. Ratings are sourced from goodreads.com"));
        originPage.add(instructions);


        Tab booksTab = new Tab("Books");
        Div booksPage = new Div();
        booksPage.add(booksGrid);
        booksPage.add(bookButtons);
        bookButtons.setPadding(true);
        addNewBook.addClickListener(e -> bookCreateForm.setVisible(true));
        booksPage.add(bookCreateForm);
        bookEditForm.setBook(null);
        booksGrid.asSingleSelect().addValueChangeListener(e ->
                bookEditForm.setBook((BookDto) booksGrid.asSingleSelect().getValue()));
        booksPage.add(bookEditForm);
        booksPage.setVisible(false);

        Tab usersTab = new Tab("Users");
        Div usersPage = new Div();
        usersPage.add(usersGrid);
        usersPage.add(userButtons);
        userButtons.setPadding(true);
        addNewUser.addClickListener(e -> userForm.setUser(new UserDto()));
        userForm.setUser(null);
        usersGrid.asSingleSelect().addValueChangeListener(e ->
                userForm.setUser((UserDto) usersGrid.asSingleSelect().getValue()));
        usersPage.add(userForm);
        usersPage.setVisible(false);

        Tab rentalsTab = new Tab("Rentals");
        Div rentalsPage = new Div();
        rentalsPage.add(rentalsGrid);
        rentalsPage.add(rentalButtons);
        rentalButtons.setPadding(true);
        addNewRental.addClickListener(e -> createRentalForm.setRental(new RentalDto()));
        editRentalForm.setRental(null);
        rentalsGrid.asSingleSelect().addValueChangeListener(e ->
                editRentalForm.setRental((RentalDto) rentalsGrid.asSingleSelect().getValue()));
        rentalsPage.add(createRentalForm);
        rentalsPage.add(editRentalForm);
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

    }

    public void refresh() {
        originsGrid.setItems(originService.fetchOrigins());
        booksGrid.setItems(bookService.fetchBooks());
        usersGrid.setItems(userService.fetchUsers());
        rentalsGrid.setItems(rentalService.fetchRentals());
    }

    public void alignGridColumns(Grid grid) {
        for (Object col : grid.getColumns()) {
            if (col instanceof Grid.Column) {
                ((Grid.Column) col).setAutoWidth(true);
            }
        }
    }

}
