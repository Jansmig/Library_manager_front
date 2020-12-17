package com.manager.front2.form;

import com.manager.front2.MainView;
import com.manager.front2.domain.UserDto;
import com.manager.front2.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.EmailValidator;


public class UserFormNew extends FormLayout {

    private TextField firstName = new TextField("First name");
    private TextField lastName = new TextField("Last name");
    private TextField email = new TextField("Email");
    private Button save = new Button("Save");
    private UserService userService = UserService.getInstance();
    private Binder<UserDto> binder = new Binder<>(UserDto.class);
    private MainView mainView;

    public UserFormNew(MainView mainView) {
        HorizontalLayout buttons = new HorizontalLayout(save);
        buttons.setVerticalComponentAlignment(FlexComponent.Alignment.BASELINE, save);
        save.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        add(firstName, lastName, email, buttons);
        binder.forField(firstName)
                .withValidator(n -> n.length() > 2, "Name requires at least 3 characters")
                .bind(UserDto::getFirstName, UserDto::setFirstName);
        binder.forField(lastName)
                .withValidator(n -> n.length() > 2, "Surname requires at least 3 characters")
                .bind(UserDto::getLastName, UserDto::setLastName);
        binder.forField(email)
                .withValidator(new EmailValidator("Invalid e-mail address"))
                .bind(UserDto::getEmail, UserDto::setEmail);
        binder.bindInstanceFields(this);
        save.addClickListener(event -> save());
        this.mainView = mainView;

    }

    private void save() {
        UserDto userDto = binder.getBean();
        userService.saveUser(userDto);
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
