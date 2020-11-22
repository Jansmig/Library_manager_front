package com.manager.front2.form;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.manager.front2.MainView;
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
public class EditRentalForm extends FormLayout {

    private TextField rentalId = new TextField("Rental ID");
    private Button closeRentalButton = new Button("Close");
    private Button deleteRentalButton = new Button("Delete");
    private Binder<RentalDto> binder = new Binder<>(RentalDto.class);
    private RentalService rentalService = RentalService.getInstance();
    private MainView mainView;


    public EditRentalForm(MainView mainView){
        closeRentalButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        HorizontalLayout buttons = new HorizontalLayout(closeRentalButton, deleteRentalButton);
        buttons.setVerticalComponentAlignment(FlexComponent.Alignment.BASELINE, closeRentalButton, deleteRentalButton);
        add(rentalId, buttons);
        binder.forField(rentalId)
                .withConverter(new StringToLongConverter(""))
                .bind(RentalDto::getId, RentalDto::setId);
        binder.bindInstanceFields(this);
        closeRentalButton.addClickListener(e -> closeRental());
        deleteRentalButton.addClickListener(e -> deleteRental());
        this.mainView = mainView;
        this.setVisible(false);
    }

    public void closeRental(){
        RentalDto rentalDto = binder.getBean();
        rentalService.closeRental(rentalDto);
        mainView.refresh();
        setRental(null);
    }

    public void deleteRental(){
        RentalDto rentalDto = binder.getBean();
        rentalService.deleteRental(rentalDto);
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
