package org.vaadin.rating.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@Theme("valo")
@SuppressWarnings("serial")
public class RatingUI extends UI {

    @WebServlet(value = "/*")
    public static class Servlet extends VaadinServlet {
    }

    VerticalLayout todoList = new VerticalLayout();
    Button addButton = new Button(FontAwesome.PLUS);
    VerticalLayout layout = new VerticalLayout(todoList, addButton);


    @Override
    protected void init(VaadinRequest request) {

        setContent(layout);

        layout.setSizeFull();
        todoList.setSizeUndefined();
        layout.setComponentAlignment(todoList, Alignment.BOTTOM_CENTER);
        layout.setComponentAlignment(addButton, Alignment.TOP_CENTER);
        layout.setSpacing(true);
        todoList.setSpacing(true);

        addButton.addClickListener(e -> todoList.addComponent(new TodoItem()));
    }
}
