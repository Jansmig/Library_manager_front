package com.manager.front2.pages;

import com.manager.front2.MainView;
import com.manager.front2.domain.OriginDto;
import com.manager.front2.filter.OriginFilter;
import com.manager.front2.form.OriginFormNew;
import com.manager.front2.form.OriginFormUpdate;
import com.manager.front2.service.OriginService;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class OriginsPage extends Div {

    private Grid originsGrid = new Grid<>(OriginDto.class);
    private OriginFormNew originFormNew;
    private OriginFormUpdate originFormUpdate;
    private OriginFilter originFilter = new OriginFilter(originsGrid);
    private Button addNewOrigin = new Button("Add new Origin");
    private Button getAllRatings = new Button("Download all ratings");
    private HorizontalLayout originButtons = new HorizontalLayout(originFilter, addNewOrigin, getAllRatings);
    private OriginService originService = OriginService.getInstance();
    private Details instructions = new Details("Details & instruction", new Text(""));


    public OriginsPage(MainView mainView) {
        this.originFormUpdate = new OriginFormUpdate(mainView);
        this.originFormNew = new OriginFormNew(mainView);
        originsGrid.setColumns("id", "title", "author", "publishedYear", "isbn", "rating");
        originsGrid.getColumnByKey("rating").setHeader("Goodreads rating");
        for (Object col : originsGrid.getColumns()) {
            if (col instanceof Grid.Column) {
                ((Grid.Column) col).setAutoWidth(true);
            }
        }
        originFormNew.setOrigin(null);
        originFormUpdate.setOrigin(null);
        add(originsGrid);
        add(originButtons);
        originButtons.setPadding(true);
        addNewOrigin.addClickListener(event -> {
            originFormNew.setOrigin(new OriginDto());
        });
        getAllRatings.addClickListener(e -> {
            originService.updateAllGoodreadsRatings();
            refresh();
        });
        originsGrid.asSingleSelect().addValueChangeListener(event ->
                originFormUpdate.setOrigin((OriginDto) originsGrid.asSingleSelect().getValue()));
        add(originFormNew);
        add(originFormUpdate);
        instructions.setContent(new Text("Please note that the application will not allow for actions violating integrity constraints, such as deletion of an object if some other objects depend on it. For example: it is not possible to delete an origin if there are rentals of books of that origin. First rentals need to be deleted, then it is possible to delete the origin (which will also delete all books of that origin). There are also field constraints and validators (e.g. for email address, whether given ISBN already exists in the database etc.\n" +
                "To edit or reveal/hide additional functionalities relating existing objects, simply click/unclick a record in the table. Ratings are sourced from www.goodreads.com"));
        add(instructions);
    }

    public void refresh() {
        originsGrid.setItems(originService.fetchOrigins());
    }
}

