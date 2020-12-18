package com.manager.front2.form;

import com.manager.front2.MainView;
import com.manager.front2.form.bookComponents.BookOriginComponent;
import com.manager.front2.service.BookService;
import com.manager.front2.service.OriginService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class BookCreateForm extends FormLayout {

    private OriginService originService = OriginService.getInstance();
    private Button create = new Button("Create");
    private BookService bookService = BookService.getInstance();
    private BookOriginComponent bookOriginBox = new BookOriginComponent();
    private MainView mainView;

    public BookCreateForm(MainView mainView){
        HorizontalLayout buttons = new HorizontalLayout(create);
        buttons.setVerticalComponentAlignment(FlexComponent.Alignment.BASELINE, create);
        add(bookOriginBox, buttons);
        create.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        create.addClickListener(e -> createBook());
        this.mainView = mainView;
        this.setVisible(false);
    }

    public void createBook(){
        String title = bookOriginBox.getValue();
        long originId = originService.fetchOrigins().stream()
                .filter(origin -> origin.getTitle().equals(title))
                .map(origin -> origin.getId())
                .findAny()
                .orElse(0L);

        bookService.createBook(originId);
        mainView.refresh();
        this.setVisible(false);
    }




}
