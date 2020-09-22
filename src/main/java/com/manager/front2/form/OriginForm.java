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

@JsonIgnoreProperties(ignoreUnknown = true)
public class OriginForm extends FormLayout {

    private TextField title = new TextField("Title");
    private TextField author = new TextField("Author");
    private TextField publishedYear = new TextField("Published year");
    private TextField isbn = new TextField("ISBN");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private Binder<OriginDto> binder = new Binder<>(OriginDto.class);
    private OriginService originService = OriginService.getInstance();
    private MainView mainView;

    public OriginForm(MainView mainView){
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(title, author, publishedYear, isbn, buttons);

        binder.forField(publishedYear)
                .withConverter(new StringToIntegerConverter("Requires integer"))
                .bind(OriginDto::getPublishedYear, OriginDto::setPublishedYear);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        this.mainView = mainView;
    }

    private void save() {
        OriginDto originDto = binder.getBean();
        originService.saveOrigin(originDto);
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





}
