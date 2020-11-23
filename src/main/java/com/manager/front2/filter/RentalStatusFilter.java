package com.manager.front2.filter;

import com.manager.front2.domain.RentalDto;
import com.manager.front2.service.RentalService;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.data.value.ValueChangeMode;

import java.util.List;
import java.util.stream.Collectors;

public class RentalStatusFilter extends ComboBox<RentalStatus> {

    private RentalService rentalService = RentalService.getInstance();
    private Grid grid;

    public RentalStatusFilter(Grid grid){
        this.setPlaceholder("Active / Inactive");
        this.setClearButtonVisible(true);
        this.setItems(RentalStatus.values());
        this.grid = grid;
        this.addValueChangeListener(e -> filterRentalsByActivityStatus());
    }

    private void filterRentalsByActivityStatus() {
        if (this.getValue() == null){
            this.grid.setItems(rentalService.fetchRentals());

        } else if(this.getValue() == RentalStatus.ACTIVE) {
            List<RentalDto> activeRentals = rentalService.fetchRentals().stream()
                    .filter(rental -> rental.isActive())
                    .collect(Collectors.toList());
            this.grid.setItems(activeRentals);

        } else if(this.getValue() == RentalStatus.CLOSED) {
            List<RentalDto> inactiveRentals = rentalService.fetchRentals().stream()
                    .filter(rental -> !rental.isActive())
                    .collect(Collectors.toList());
            this.grid.setItems(inactiveRentals);
        }
    }

}
