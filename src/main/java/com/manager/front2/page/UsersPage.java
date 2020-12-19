package com.manager.front2.page;

import com.manager.front2.MainView;
import com.manager.front2.domain.UserDto;
import com.manager.front2.form.UserFormNew;
import com.manager.front2.form.UserFormUpdate;
import com.manager.front2.service.UserService;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

public class UsersPage extends Div {

    private Grid usersGrid = new Grid<>(UserDto.class);
    private UserService userService = UserService.getInstance();
    private UserFormNew userFormNew;
    private UserFormUpdate userFormUpdate;
    private Button addNewUser = new Button("Add new User");
    private HorizontalLayout userButtons = new HorizontalLayout(addNewUser);


    public UsersPage(MainView mainView){
        usersGrid.setColumns("id", "firstName", "lastName", "email", "userCreationDate");
        for (Object col : usersGrid.getColumns()) {
            if (col instanceof Grid.Column) {
                ((Grid.Column) col).setAutoWidth(true);
            }
        }
        this.userFormNew = new UserFormNew(mainView);
        this.userFormUpdate = new UserFormUpdate(mainView);
        add(usersGrid);
        add(userButtons);
        userButtons.setPadding(true);
        addNewUser.addClickListener(e -> userFormNew.setUser(new UserDto()));
        userFormNew.setUser(null);
        userFormUpdate.setUser(null);
        usersGrid.asSingleSelect().addValueChangeListener(e ->
                userFormUpdate.setUser((UserDto) usersGrid.asSingleSelect().getValue()));
        add(userFormNew);
        add(userFormUpdate);
        this.setVisible(false);
    }

    public void refresh(){
        usersGrid.setItems(userService.fetchUsers());
    }

}
