package com.manager.front2.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manager.front2.MainView;
import com.manager.front2.domain.OriginDto;
import com.manager.front2.service.OriginService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToIntegerConverter;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OriginFormUpdate extends FormLayout {

    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private TextField publishedYear = new TextField("Published year");
    private TextField isbn = new TextField("ISBN");
    private Button update = new Button("Update");
    private Button delete = new Button("Delete");
    private Button checkRating = new Button("Check rating");
    private Binder<OriginDto> binder = new Binder<>(OriginDto.class);
    private OriginService originService = OriginService.getInstance();
    private MainView mainView;

    public OriginFormUpdate(MainView mainView){
        HorizontalLayout buttons = new HorizontalLayout(update, delete, checkRating);
        update.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(title, author, publishedYear, isbn, buttons);
        binder.forField(publishedYear)
                .withConverter(new StringToIntegerConverter("Requires integer"))
                .withValidator(y -> y <= LocalDateTime.now().getYear(), "Looks like a book from the future.")
                .withValidator(y -> y > -4000, "Kinda old, isn't it?")
                .bind(OriginDto::getPublishedYear, OriginDto::setPublishedYear);
        binder.forField(isbn)
                .withValidator(y -> y.matches("\\d{10}|\\d{13}"), "ISBN has to be a 10 or 13 characters long Integer")
                .bind(OriginDto::getIsbn, OriginDto::setIsbn);
        binder.bindInstanceFields(this);
        update.addClickListener(event -> update());
        delete.addClickListener(event -> delete());
        checkRating.addClickListener(event -> checkGoodreadsRating());
        this.mainView = mainView;
    }

    private void update() {
        OriginDto originDto = binder.getBean();
        originService.updateOrigin(originDto);
        mainView.refresh();
        setOrigin(null);
    }

    private void delete() {
        OriginDto originDto = binder.getBean();
        originService.deleteOrigin(originDto);
        mainView.refresh();
        setOrigin(null);
    }

    public void setOrigin(OriginDto originDto) {
        binder.setBean(originDto);
        if (originDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
            title.focus();
        }
    }

    private void checkGoodreadsRating() {
        OriginDto originDto = binder.getBean();
        originService.updateGoodreadsRating(originDto);
        mainView.refresh();
        setOrigin(null);
    }

}
