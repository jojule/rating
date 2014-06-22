package org.vaadin.rating.ui;

import com.vaadin.data.Property;
import com.vaadin.ui.HorizontalLayout;
import org.vaadin.rating.service.RatingService;
import org.vaadin.rating.service.User;
import org.vaadin.rating.service.data.Presentation;
import org.vaadin.teemu.ratingstars.RatingStars;

import javax.inject.Inject;

public class PresentationRating extends HorizontalLayout {

    Presentation presentation;
    @Inject
    RatingService service;
    @Inject
    User user;

    RatingStars myRating = new RatingStars();
    RatingStars avgRating = new RatingStars();

    PresentationRating() {
        addComponents(myRating, avgRating);

        avgRating.setCaption("Average rating");
        avgRating.setReadOnly(true);
        myRating.setCaption("My rating");

        myRating.addValueChangeListener(this::rate);
    }

    void rate(Property.ValueChangeEvent e) {
        service.setRating(user.getEmail(), presentation, myRating.getValue());
        updateAverage();
    }

    void updateAverage() {
        avgRating.setReadOnly(false);
        avgRating.setValue(service.getRating(presentation));
        avgRating.setReadOnly(true);
    }

    void setPresentation(Presentation presentation) {
        this.presentation = presentation;
        myRating.setValue(service.getRating(presentation, user.getEmail()));
        updateAverage();
    }
}
