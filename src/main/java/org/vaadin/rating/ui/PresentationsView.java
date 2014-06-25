package org.vaadin.rating.ui;

import com.vaadin.cdi.CDIView;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalSplitPanel;
import org.vaadin.rating.service.User;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

@CDIView(value = PresentationsView.URI)
class PresentationsView extends HorizontalSplitPanel implements View {
    static final String URI = "presentations";

    @Inject
    PresentationDetails details;
    @Inject
    PresentationList list;

    @Inject User user;

    @PostConstruct
    void buildLayout() {
        addComponents(list, details);
    }

    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        if (user.getEmail() == null) getUI().getNavigator().navigateTo("");
        details.setPresentation(list.getPresentation(viewChangeEvent.getParameters()));
    }
}
