package org.vaadin.rating.ui;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import com.vaadin.data.util.filter.Compare;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.vaadin.rating.service.data.Comment;
import org.vaadin.rating.service.data.Presentation;

/**
 *
 */
@Dependent
public class CommentContainer extends JPAContainer<Comment> {

    @PersistenceContext
    EntityManager em;

    public CommentContainer() {
        super(Comment.class);
    }

    public void filterByPresentation(Presentation presentation) {
        removeAllContainerFilters();
        addContainerFilter(new Compare.Equal("presentation", presentation));
    }

    @PostConstruct
    void init() {
        setEntityProvider(new CachingLocalEntityProvider<>(Comment.class, em));
    }

}
