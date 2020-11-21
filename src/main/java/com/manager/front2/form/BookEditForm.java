package com.manager.front2.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manager.front2.MainView;
import com.manager.front2.domain.BookDto;
import com.manager.front2.domain.BookStatus;
import com.manager.front2.service.BookService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;
import com.vaadin.flow.data.converter.StringToLongConverter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookEditForm extends FormLayout {

    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private BookService bookService = BookService.getInstance();
    private ComboBox<BookStatus> bookStatus = new ComboBox<>("Book status");
    private Binder<BookDto> binder = new Binder<>(BookDto.class);
    private MainView mainView;


    public BookEditForm(MainView mainView){
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setVerticalComponentAlignment(FlexComponent.Alignment.BASELINE, save, delete);
        bookStatus.setItems(BookStatus.values());
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(bookStatus, buttons);
        binder.bindInstanceFields(this);
        save.addClickListener(e -> updateBook());
        delete.addClickListener(e -> deleteBook());
        this.mainView = mainView;
    }

    public void updateBook(){
        BookDto bookDto = binder.getBean();
        bookService.updateBook(bookDto);
        mainView.refresh();
        setBook(null);
    }

    public void deleteBook(){
        BookDto bookDto = binder.getBean();
        bookService.deleteBook(bookDto);
        mainView.refresh();
        setBook(null);
    }

    public void setBook(BookDto bookDto){
        binder.setBean(bookDto);
        if (bookDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
    //        bookStatus.focus();
        }
    }




}
