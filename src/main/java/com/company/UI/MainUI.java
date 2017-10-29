package com.company.UI;

import com.company.model.Todo;
import com.company.repository.TodoRepository;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ComponentRenderer;
import com.vaadin.ui.themes.ValoTheme;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by bernard-w on 29/10/17.
 */
@SpringUI
public class MainUI  extends UI{


    private VerticalLayout verticalLayout=new VerticalLayout();
    private TextField textField=new TextField();
    private Button button=new Button("ADD");
    private Grid<Todo> grid=new Grid<>();
    @Autowired
    TodoRepository todoRepository;

    private  Todo selectedTodo=new Todo();


    @Override
    protected void init(VaadinRequest vaadinRequest) {

        HorizontalLayout horizontalLayout=new HorizontalLayout();
        horizontalLayout.setMargin(true);
        horizontalLayout.setWidth(50,Unit.PERCENTAGE);
        horizontalLayout.addComponents(textField,button);
        verticalLayout.addComponent(horizontalLayout);
        setContent(verticalLayout);
        addEventHandler();
        setUpGrid();
        loadGrid();
    }

    public void setUpGrid(){
        grid.addColumn(Todo::getTask).setCaption("Task");
        grid.addColumn(todo -> {
            Button delete = new Button();
            delete.setStyleName(ValoTheme.BUTTON_DANGER);
            delete.setIcon(VaadinIcons.FILE_REMOVE);

            delete.addClickListener(clickEvent -> {

                try {
                    todoRepository.delete(todo);
                    Notification.show("Successfully deleted TODO");
                    loadGrid();
                }catch (Exception e){
                    Notification.show("Error in deleting TODO");
                }
                });


            return delete;


        },new ComponentRenderer());

        verticalLayout.addComponent(grid);

        grid.addItemClickListener(itemClick -> {
            selectedTodo=itemClick.getItem();
            textField.setValue(selectedTodo.getTask());
            button.setCaption("Save");
        });
    }

    public void loadGrid(){
        List<Todo> todoList=(List<Todo>)todoRepository.findAll();
        grid.setItems(todoList);
    }




    public void addEventHandler(){
        button.addClickListener(clickEvent -> {



            if(textField.isEmpty()){
                Notification.show("Please enter a task!");
                return;
            }

            if(button.getCaption().equals("Add"))
                selectedTodo=new Todo();
            else
                button.setCaption("Add");

            selectedTodo.setTask(textField.getValue());
            // TOOD all a layer which a service and to a try catch to handle error if it happened
            try {
                todoRepository.save(selectedTodo); // Both  new and save
                Notification.show("Successully added Task");
                loadGrid();
            }
            catch (Exception e){
                // In real app you might need to log this to a file
                Notification.show("Error");
            }


        });
    }
}
