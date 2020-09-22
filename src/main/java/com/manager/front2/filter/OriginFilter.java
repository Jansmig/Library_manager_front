package com.manager.front2.filter;

import com.manager.front2.service.OriginService;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class OriginFilter extends TextField {

    private OriginService originService = OriginService.getInstance();
    private Grid grid;

    public OriginFilter(Grid grid) {
        this.setPlaceholder("Filter by title");
        this.setClearButtonVisible(true);
        this.setValueChangeMode(ValueChangeMode.EAGER);
        this.grid = grid;
        this.addValueChangeListener(e -> updateOrigins());
    }

    public void updateOrigins(){
        this.grid.setItems(originService.fetchOriginsByTitle(this.getValue()));
    }

}
