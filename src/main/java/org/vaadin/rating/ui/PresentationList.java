package org.vaadin.rating.ui;

import com.vaadin.addon.jpacontainer.JPAContainer;
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
    Button addButton = new Button("Add presentation", this::addPresentation);

    @Inject RatingService service;
    @Inject User user;
    JPAContainer<Presentation> datasource;

    PresentationList() {
        addComponents(presentations);
        setExpandRatio(presentations, 1f);
        presentations.setSizeFull();
        setSizeFull();
        addButton.setWidth("100%");

        presentations.setSelectable(true);
        presentations.addValueChangeListener(this::presentationSelectedFromList);
    }

    @PostConstruct void init() {
        datasource = service.getPresentationsContainer();
        presentations.setContainerDataSource(datasource);
        presentations.setVisibleColumns("topic", "speaker");

        if (user.isAdmin()) addComponent(addButton);
    }

    void addPresentation(Button.ClickEvent e) {
        Presentation p = service.addPresentation();
        datasource.refresh();
        presentations.select(p.getId());
    }

    void presentationSelectedFromList(Property.ValueChangeEvent e) {
        String param = (presentations.getValue() != null) ? "/" + presentations.getValue().toString() : "";
        getUI().getNavigator().navigateTo(PresentationsView.URI + param);

    }

    Presentation getPresentation(String presentationId) {
        try {
            return datasource.getItem(Long.valueOf(presentationId)).getEntity();
        } catch (NumberFormatException e) {
            // Not a proper presentation id
            return null;
        } catch (NullPointerException e) {
            // Presentation was not on the list
            return null;
        }

    }
}
