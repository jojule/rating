package org.vaadin.rating.ui;

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.util.BeanItem;
import com.vaadin.server.Responsive;
import com.vaadin.shared.ui.datefield.Resolution;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.Reindeer;
import org.vaadin.rating.service.RatingService;
import org.vaadin.rating.service.User;
import org.vaadin.rating.service.data.Presentation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

public class PresentationForm extends CssLayout {

    Presentation presentation;
    @Inject
    RatingService service;
    @Inject
    User user;

    FieldGroup fieldGroup;
    TextField topic = new TextField("Topic");
    TextField speaker = new TextField("Speaker");
    DateField startTime = new DateField("Start time");
    TextArea overview = new TextArea("Overview");

    Button deleteButton = new Button("Delete", this::delete);
    Button resetButton = new Button("Reset", e -> fieldGroup.discard());
    Button saveButton = new Button("Save", this::save);

    @PostConstruct
    void init() {
        addComponents(topic, speaker, startTime, overview);
        if (user.isAdmin())
            addComponents(deleteButton, resetButton, saveButton);

        startTime.setResolution(Resolution.MINUTE);

        Responsive.makeResponsive(this);
        setStyleName("presentation-form");
        speaker.setStyleName("speaker");
        topic.setStyleName("topic");
        saveButton.setStyleName(Reindeer.BUTTON_DEFAULT);
    }

    void save(Button.ClickEvent e) {
        try {
            fieldGroup.commit();
            service.updatePresentation(presentation);
            updatePresentationList();
        } catch (FieldGroup.CommitException e1) {
            // This should not happen as the bean itself is a DTO
        }
    }

    void delete(Button.ClickEvent e) {
        service.deletePresentation(presentation);
        updatePresentationList();
        ((PresentationsView) getParent().getParent()).list.presentations.setValue(null);
    }

    void setPresentation(Presentation presentation) {
        this.presentation = presentation;

        fieldGroup = new FieldGroup(new BeanItem<>(presentation));
        fieldGroup.bindMemberFields(this);
        fieldGroup.setReadOnly(!user.isAdmin());
    }

    void updatePresentationList() {
        // TODO use CDI events to refresh and decouple
        ((PresentationsView)getParent().getParent()).list.datasource.refresh();
    }

}
