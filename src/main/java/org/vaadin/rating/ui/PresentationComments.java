package org.vaadin.rating.ui;

import com.vaadin.ui.*;
import org.vaadin.rating.service.CommentContainer;
import org.vaadin.rating.service.RatingService;
import org.vaadin.rating.service.User;
import org.vaadin.rating.service.data.Presentation;

import javax.inject.Inject;

public class PresentationComments extends VerticalLayout {

    Presentation presentation;
    @Inject
    RatingService service;
    @Inject
    User user;

    Table commentList = new Table();
    
    @Inject
    CommentContainer datasource;
    
    TextField commentField = new TextField();
    Button addButton = new Button("Comment", this::comment);

    PresentationComments() {
        HorizontalLayout commentLine = new HorizontalLayout(commentField, addButton);
        addComponents(commentList, commentLine);

        commentList.setPageLength(0);
        commentList.setColumnHeaderMode(Table.ColumnHeaderMode.HIDDEN);
        commentList.setWidth("100%");
        commentList.setVisible(false);

        commentLine.setExpandRatio(commentField, 1f);
        commentLine.setWidth("100%");
        commentField.setWidth("100%");
        setSpacing(true);
    }

    void comment(Button.ClickEvent e) {
        if (commentField.getValue().length() > 0) {
            service.addComment(user.getEmail(), presentation, commentField.getValue());
            datasource.refresh();
            commentField.setValue("");
            commentList.setVisible(true);
        }
    }

    void setPresentation(Presentation presentation) {
        this.presentation = presentation;
        datasource.filterByPresentation(presentation);
        commentList.setContainerDataSource(datasource);
        commentList.setVisibleColumns("timeGiven", "email", "comment");
        commentList.setColumnExpandRatio("comment", 1f);
        commentList.setVisible(datasource.size() > 0);
    }

}
