package org.vaadin.rating.ui;

import com.vaadin.ui.Alignment;
import com.vaadin.ui.VerticalLayout;
import org.vaadin.rating.service.data.Presentation;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

class PresentationDetails extends VerticalLayout {

    @Inject
    PresentationForm form;
    @Inject
    PresentationRating rating;
    @Inject
    PresentationComments comments;

    @PostConstruct
    void init() {
        addComponents(form, rating, comments);

        setMargin(true);
        setSpacing(true);
        setVisible(false);

        setComponentAlignment(rating, Alignment.MIDDLE_CENTER);
    }

    void setPresentation(Presentation presentation) {
        setVisible(presentation != null);
        if (presentation != null) {
            form.setPresentation(presentation);
            rating.setPresentation(presentation);
            comments.setPresentation(presentation);
        }
    }
}
