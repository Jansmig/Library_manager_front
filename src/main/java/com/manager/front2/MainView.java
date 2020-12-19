package com.manager.front2;

import com.manager.front2.domain.RentalDto;
import com.manager.front2.domain.UserDto;
import com.manager.front2.filter.RentalLastnameFilter;
import com.manager.front2.filter.RentalStatusFilter;
import com.manager.front2.form.*;
import com.manager.front2.page.BooksPage;
import com.manager.front2.page.OriginsPage;
import com.manager.front2.page.UsersPage;
import com.manager.front2.service.RentalService;
import com.manager.front2.service.UserService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
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

    private RentalService rentalService = RentalService.getInstance();
    private Grid rentalsGrid = new Grid<>(RentalDto.class);
    private Button addNewRental = new Button("Add new Rental");
    private CreateRentalForm createRentalForm = new CreateRentalForm(this);
    private EditRentalForm editRentalForm = new EditRentalForm(this);
    private RentalLastnameFilter rentalLastnameFilter = new RentalLastnameFilter(rentalsGrid);
    private RentalStatusFilter rentalStatusFilter = new RentalStatusFilter(rentalsGrid);
    private HorizontalLayout rentalButtons = new HorizontalLayout(rentalLastnameFilter, rentalStatusFilter, addNewRental);
    private OriginsPage originsPage = new OriginsPage(this);
    private BooksPage booksPage = new BooksPage(this);
    private UsersPage usersPage = new UsersPage(this);


    public MainView() {
        rentalsGrid.setColumns("id", "active", "bookId", "bookTitle", "userId", "userFirstName", "userLastName", "rentalDate", "returnDate");
        alignGridColumns(rentalsGrid);


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

        Tab originsTab = new Tab("Origins");
        Tab booksTab = new Tab("Books");
        Tab usersTab = new Tab("Users");

        Map<Tab, Component> tabsToPages = new HashMap<>();
        tabsToPages.put(originsTab, originsPage);
        tabsToPages.put(booksTab, booksPage);
        tabsToPages.put(rentalsTab, rentalsPage);
        tabsToPages.put(usersTab, usersPage);
        Tabs tabs = new Tabs(originsTab, booksTab, usersTab, rentalsTab);
        Div pages = new Div(originsPage, booksPage, usersPage, rentalsPage);

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
        originsPage.refresh();
        booksPage.refresh();
        usersPage.refresh();
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

