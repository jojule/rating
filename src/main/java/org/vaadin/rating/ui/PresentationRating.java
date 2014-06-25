package org.vaadin.rating.ui;

import com.vaadin.data.Property;
import com.vaadin.data.validator.IntegerRangeValidator;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import org.vaadin.rating.service.RatingService;
import org.vaadin.rating.service.User;
import org.vaadin.rating.service.data.Presentation;

import javax.inject.Inject;

public class PresentationRating extends HorizontalLayout {

    Presentation presentation;
    @Inject
    RatingService service;
    @Inject
    User user;

    TextField myRating = new TextField("My rating");
    TextField avgRating = new TextField("Average rating");

    PresentationRating() {
        addComponents(myRating, avgRating);

        avgRating.setReadOnly(true);

        myRating.addValueChangeListener(this::rate);
        myRating.setNullSettingAllowed(true);
        myRating.setNullRepresentation("-");
    }

    void rate(Property.ValueChangeEvent e) {
        try {
            if (myRating.getValue() != null) {
                int myRatingValue = Integer.valueOf(myRating.getValue());
                if (myRatingValue < 1 || myRatingValue > 5)
                    Notification.show("Rating must be 1-5");
                service.setRating(user.getEmail(), presentation, myRatingValue);
                updateAverage();
            }
        } catch (NumberFormatException ignored) {
        }
    }

    void updateAverage() {
        avgRating.setReadOnly(false);
        avgRating.setValue(String.valueOf(service.getRating(presentation)));
        avgRating.setReadOnly(true);
    }

    void setPresentation(Presentation presentation) {
        this.presentation = presentation;
        int myRatingValue = (int) service.getRating(presentation, user.getEmail());
        myRating.setValue(myRatingValue < 1 ? null : String.valueOf(myRatingValue));
        updateAverage();
    }
}
