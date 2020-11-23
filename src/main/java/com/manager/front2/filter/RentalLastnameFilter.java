package com.manager.front2.filter;

import com.manager.front2.domain.RentalDto;
import com.manager.front2.service.RentalService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.stream.Collectors;

public class RentalLastnameFilter extends TextField {

    private RentalService rentalService = RentalService.getInstance();
    private Grid grid;

    public RentalLastnameFilter(Grid grid) {
        this.setPlaceholder("Filter by last name");
        this.setClearButtonVisible(true);
        this.setValueChangeMode(ValueChangeMode.EAGER);
        this.grid = grid;
        this.addValueChangeListener(e -> filterRentalsByLastName());
    }

    private void filterRentalsByLastName() {
        String lastName = this.getValue().toLowerCase();
        List<RentalDto> rentals = rentalService.fetchRentals().stream()
                .filter(rental -> rental.getUserLastName().toLowerCase().contains(lastName))
                .collect(Collectors.toList());

        this.grid.setItems(rentals);
    }

}
