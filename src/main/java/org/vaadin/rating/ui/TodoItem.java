package org.vaadin.rating.ui;

import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.TextField;

public class TodoItem extends HorizontalLayout {
    TextField task = new TextField();
    Button remove = new Button(FontAwesome.TIMES);

    public TodoItem() {
        addComponents(task, remove);
        remove.addClickListener(e -> ((ComponentContainer) getParent()).removeComponent(this));
    }

}
