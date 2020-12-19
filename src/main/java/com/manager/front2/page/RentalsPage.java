package com.manager.front2.page;

import com.manager.front2.MainView;
import com.manager.front2.domain.RentalDto;
import com.manager.front2.filter.RentalLastnameFilter;
import com.manager.front2.filter.RentalStatusFilter;
import com.manager.front2.form.CreateRentalForm;
import com.manager.front2.form.EditRentalForm;
import com.manager.front2.service.RentalService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class RentalsPage extends Div {

    private RentalService rentalService = RentalService.getInstance();
    private Grid rentalsGrid = new Grid<>(RentalDto.class);
    private Button addNewRental = new Button("Add new Rental");
    private CreateRentalForm createRentalForm;
    private EditRentalForm editRentalForm;
    private RentalLastnameFilter rentalLastnameFilter = new RentalLastnameFilter(rentalsGrid);
    private RentalStatusFilter rentalStatusFilter = new RentalStatusFilter(rentalsGrid);
    private HorizontalLayout rentalButtons = new HorizontalLayout(rentalLastnameFilter, rentalStatusFilter, addNewRental);

    public RentalsPage(MainView mainView){
        this.createRentalForm = new CreateRentalForm(mainView);
        this.editRentalForm = new EditRentalForm(mainView);
        rentalsGrid.setColumns("id", "active", "bookId", "bookTitle", "userId", "userFirstName", "userLastName", "rentalDate", "returnDate");
        for (Object col : rentalsGrid.getColumns()) {
            if (col instanceof Grid.Column) {
                ((Grid.Column) col).setAutoWidth(true);
            }
        }
        add(rentalsGrid);
        add(rentalButtons);
        rentalButtons.setPadding(true);
        addNewRental.addClickListener(e -> createRentalForm.setRental(new RentalDto()));
        editRentalForm.setRental(null);
        rentalsGrid.asSingleSelect().addValueChangeListener(e ->
                editRentalForm.setRental((RentalDto) rentalsGrid.asSingleSelect().getValue()));
        add(createRentalForm);
        add(editRentalForm);
        this.setVisible(false);
    }

    public void refresh(){
        rentalsGrid.setItems(rentalService.fetchRentals());
    }

}
