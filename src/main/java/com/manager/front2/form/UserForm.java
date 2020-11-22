package com.manager.front2.form;

import com.manager.front2.MainView;
import com.manager.front2.domain.OriginDto;
import com.manager.front2.domain.UserDto;
import com.manager.front2.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;


public class UserForm extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private Button save = new Button("Save");
    private Button delete = new Button("Delete");
    private UserService userService = UserService.getInstance();
    private Binder<UserDto> binder = new Binder<>(UserDto.class);
    private MainView mainView;

    public UserForm(MainView mainView) {
        HorizontalLayout buttons = new HorizontalLayout(save, delete);
        buttons.setVerticalComponentAlignment(FlexComponent.Alignment.BASELINE, save, delete);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, email, buttons);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> save());
        delete.addClickListener(event -> delete());
        this.mainView = mainView;

    }

    private void save() {
        UserDto userDto = binder.getBean();
        userService.saveUser(userDto);
        mainView.refresh();
        setUser(null);
    }

    private void delete() {
        UserDto userDto = binder.getBean();
        userService.deleteUser(userDto);
        mainView.refresh();
        setUser(null);
    }

    public void setUser(UserDto userDto) {
        binder.setBean(userDto);
        if (userDto == null) {
            setVisible(false);
        } else {
            setVisible(true);
        }
    }

}
