package org.vaadin.rating.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

@SuppressWarnings("serial")
public class RatingUI extends UI {

    @WebServlet(value = "/*")
    public static class Servlet extends VaadinServlet {
    }

    TextField name = new TextField("Who are you?");
    Button greetButton = new Button("Greet");
    VerticalLayout layout = new VerticalLayout(name, greetButton);


    @Override
    protected void init(VaadinRequest request) {

        setContent(layout);

        layout.setSizeFull();
        layout.setComponentAlignment(name, Alignment.BOTTOM_CENTER);
        layout.setComponentAlignment(greetButton, Alignment.TOP_CENTER);

        greetButton.addClickListener(e -> Notification.show("Hi " + name.getValue()));
    }
}
