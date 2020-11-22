package com.manager.front2.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manager.front2.MainView;
import com.manager.front2.domain.OriginDto;
import com.manager.front2.domain.RentalDto;
import com.manager.front2.service.RentalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.converter.StringToLongConverter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateRentalForm extends FormLayout {

    private TextField bookId = new TextField("Book ID");
    private TextField userId = new TextField("User ID");
    private Button createRentalButton = new Button("Create");
    private Binder<RentalDto> binder = new Binder<>(RentalDto.class);
    private RentalService rentalService = RentalService.getInstance();
    private MainView mainView;


    public CreateRentalForm(MainView mainView){
        createRentalButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(createRentalButton);
        buttons.setVerticalComponentAlignment(FlexComponent.Alignment.BASELINE, createRentalButton);
        add(bookId, userId, buttons);
        binder.forField(bookId)
                .withConverter(new StringToLongConverter("Provide Book ID as an Integer"))
                .bind(RentalDto::getBookId, RentalDto::setBookId);
        binder.forField(userId)
                .withConverter(new StringToLongConverter("Provide User ID as an Integer"))
                .bind(RentalDto::getUserId, RentalDto::setUserId);
        binder.bindInstanceFields(this);
        createRentalButton.addClickListener(e -> createRental());
        this.mainView = mainView;
        this.setVisible(false);
    }

    public void createRental(){
        RentalDto rentalDto = binder.getBean();
        rentalService.createRental(rentalDto);
        mainView.refresh();
        setRental(null);
    }


    public void setRental(RentalDto rentalDto) {
        binder.setBean(rentalDto);
        if (rentalDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

}
