package org.vaadin.rating.ui;

import com.vaadin.data.Property;
import com.vaadin.ui.Button;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.rating.service.RatingService;
import org.vaadin.rating.service.User;
import org.vaadin.rating.service.data.Presentation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

class PresentationList extends VerticalLayout {

    Table presentations = new Table();

    @Inject
    RatingService service;
    @Inject
    User user;

    @PostConstruct
    void init() {
        setSizeFull();

        addComponent(presentations);
        setExpandRatio(presentations, 1f);
        presentations.setSizeFull();
        presentations.setSelectable(true);
        presentations.addValueChangeListener(this::presentationSelectedFromList);

        // Add some mockup data
        presentations.addContainerProperty("topic", String.class, "");
        presentations.addContainerProperty("speaker", String.class, "");
        presentations.addItem(new Object[] {"Introduction to Vaadin", "Joonas Lehtinen"}, 1);
        presentations.addItem(new Object[] {"GWT Roadmap", "Ray Cromwell"}, 2);
    }

    void presentationSelectedFromList(Property.ValueChangeEvent e) {
        Object id = presentations.getValue();
        if (id == null)
            getUI().getNavigator().navigateTo("");
        else {
            getUI().getNavigator().navigateTo("/" + presentations.getValue());
        }

    }
}
