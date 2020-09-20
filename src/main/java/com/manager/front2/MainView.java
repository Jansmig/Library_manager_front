package com.manager.front2;

import com.manager.front2.domain.OriginDto;
import com.manager.front2.domain.service.OriginService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

    private OriginService originService = OriginService.getInstance();
    private Grid grid = new Grid<>(OriginDto.class);

    public MainView() {
        grid.setColumns("id", "title", "author", "publishedYear", "isbn");
        add(grid);
        refresh();
    }

    public void refresh() {
        grid.setItems(originService.getOrigins());
    }

}
