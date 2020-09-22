package com.manager.front2;

import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.OriginDto;
import com.manager.front2.domain.filter.OriginFilter;
import com.manager.front2.domain.form.OriginForm;
import com.manager.front2.domain.service.BookService;
import com.manager.front2.domain.service.OriginService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;

import java.util.HashMap;
import java.util.Map;

@Route
public class MainView extends VerticalLayout {

    private OriginService originService = OriginService.getInstance();
    private BookService bookService = BookService.getInstance();
    private Grid originsGrid = new Grid<>(OriginDto.class);
    private Grid booksGrid = new Grid<>(BookDto.class);
    private OriginFilter originFilter = new OriginFilter(originsGrid);
    private OriginForm originForm = new OriginForm(this);
    private Button addNewOrigin = new Button("Add new Origin");


    public MainView() {
        originsGrid.setColumns("id", "title", "author", "publishedYear", "isbn");
        booksGrid.setColumns("id", "originId", "title", "bookStatus");
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
        booksPage.setVisible(false);

        Tab usersTab = new Tab("Users");
        Div usersPage = new Div();
        usersPage.setVisible(false);

        Tab rentalsTab = new Tab("Rentals");
        Div rentalsPage = new Div();
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
        booksGrid.setItems(bookService.getBooks());
    }

}
