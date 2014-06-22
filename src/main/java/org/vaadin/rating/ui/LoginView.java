package org.vaadin.rating.ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.*;
import org.vaadin.rating.service.RatingService;
import org.vaadin.rating.service.User;

import javax.inject.Inject;

@CDIView
public class LoginView extends VerticalLayout implements View {

    TextField email = new TextField("Your email");
    Button loginButton = new Button("Login", this::login);

    @Inject RatingService service;
    @Inject User user;

    public LoginView() {

        addComponents(email, loginButton);

        setSizeFull();
        setSpacing(true);
        setComponentAlignment(email, Alignment.BOTTOM_CENTER);
        setComponentAlignment(loginButton, Alignment.TOP_CENTER);

        email.addValidator(new EmailValidator("Real email address, please"));

        email.focus();
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
    }

    void login(Button.ClickEvent clickEvent) {
        if (!email.getValue().equals("") && email.isValid()) {
            service.sendLoginLink(email.getValue(), getUI().getPage().getLocation().toString());

            // Delete UI and ask to check email
            removeAllComponents();
            Label msg = new Label("Login link sent to " + email.getValue());
            addComponent(msg);
            msg.setSizeUndefined();
            setComponentAlignment(msg, Alignment.MIDDLE_CENTER);
        } else Notification.show("Email address is needed to log in");
    }

    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {

        // If we already have logged in
        if (user.getEmail() != null) {
            getUI().getNavigator().navigateTo(PresentationsView.URI);
        }

        // Lets try to log in if we have identity given as a parameter
        String param = viewChangeEvent.getParameters();
        if (param != null && param.length() > 0) {
            if (user.getEmail() == null) {
                String email = service.login(param);
                if (email != null) {
                    user.setEmail(email);
                    System.out.println("User logged in: " + email);
                    getUI().getNavigator().navigateTo(PresentationsView.URI);
                }
            }
        }
    }
}
