package org.vaadin.rating.service;

import org.vaadin.rating.service.data.Comment;
import org.vaadin.rating.service.data.Presentation;
import org.vaadin.rating.service.data.Rating;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
@Startup
@Singleton
public class RatingService {

    @PersistenceContext
    private EntityManager em;

    public void setRating(String email, Presentation presentation, double rating) {
        Query q = em.createQuery("select r from Rating r where r.presentation = :p and r.email = :e");
        q.setParameter("p", presentation);
        q.setParameter("e", email);
        List results = q.getResultList();
        if (results.isEmpty()) {
            Rating r = new Rating();
            r.setEmail(email);
            r.setPresentation(presentation);
            r.setRating(rating);
            em.persist(r);
        } else {
            Rating r = (Rating) results.get(0);
            r.setRating(rating);
            em.merge(r);
        }
    }

    public double getRating(Presentation presentation) {
        Query q = em.createQuery("select avg(r.rating) from Rating r where r.presentation = :p");
        q.setParameter("p", presentation);
        Double average = (Double) q.getSingleResult();
        return average == null ? 0 : average;
    }

    public double getRating(Presentation presentation, String email) {
        Query q = em.createQuery("select r.rating from Rating r where r.presentation = :p and r.email = :e");
        q.setParameter("p", presentation);
        q.setParameter("e", email);
        try {
            return (Double) q.getSingleResult();
        } catch (NoResultException e) {
            return 0.0;
        }
    }

    public void addComment(String identity, Presentation presentation, String comment) {
        Comment c = new Comment();
        c.setEmail(identity);
        c.setPresentation(presentation);
        c.setComment(comment);
        em.persist(c);
    }

    public Presentation addPresentation() {
        Presentation p = new Presentation();
        em.persist(p);
        return p;
    }

    public void updatePresentation(Presentation presentation) {
        em.merge(presentation);
    }

    public void deletePresentation(Presentation presentation) {
        Query q = em.createQuery("delete from Presentation p where p.id=" + presentation.getId());
        q.executeUpdate();
    }

}
