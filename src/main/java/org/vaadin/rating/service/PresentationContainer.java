package org.vaadin.rating.service;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.addon.jpacontainer.provider.CachingLocalEntityProvider;
import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.vaadin.rating.service.data.Presentation;

/**
 *
 */
@Dependent
public class PresentationContainer extends JPAContainer<Presentation> {

    @PersistenceContext
    EntityManager em;

    public PresentationContainer() {
        super(Presentation.class);
    }

    @PostConstruct
    void init() {
        setEntityProvider(new CachingLocalEntityProvider<>(Presentation.class, em));
    }

}
