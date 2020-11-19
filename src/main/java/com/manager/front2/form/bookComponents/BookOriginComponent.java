package com.manager.front2.form.bookComponents;

import com.manager.front2.domain.BookStatus;
import com.manager.front2.service.OriginService;
import com.vaadin.flow.component.combobox.ComboBox;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookOriginComponent extends ComboBox<String> {

    private OriginService originService = OriginService.getInstance();


    public BookOriginComponent(){
        this.setLabel("Origin");
        this.setItems(getOriginsTitles());
    }


    public List<String> getOriginsTitles(){
        List<String> titlesList = new ArrayList<>();
        titlesList = originService.fetchOrigins().stream()
                .map(origin -> origin.getTitle())
                .collect(Collectors.toList());

        return titlesList;
    }

}
