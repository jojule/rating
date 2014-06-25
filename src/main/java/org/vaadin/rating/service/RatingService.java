package org.vaadin.rating.service;

import org.vaadin.rating.service.data.Comment;
import org.vaadin.rating.service.data.Presentation;
import org.vaadin.rating.service.data.Rating;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@ApplicationScoped
@Singleton
public class RatingService {

    private HashMap<Long, String> idToEmailMap = new HashMap<>();
    private HashMap<String, Long> emailToIdMap = new HashMap<>();

    @PersistenceContext
    private EntityManager em;

    public void sendLoginLink(String email, String appLocation) {

        Long id;
        if (emailToIdMap.containsKey(email)) id = emailToIdMap.get(email);
        else {
            id = Long.valueOf((long) (Math.random() * Long.MAX_VALUE));
            emailToIdMap.put(email, id);
            idToEmailMap.put(id, email);
        }
        int trail = appLocation.indexOf("#!");
        if (trail > 0) appLocation = appLocation.substring(0, trail);
        String url = appLocation + "#!/" + id;
        sendEmail(email, email + " can log in at " + url);

        // TODO add map cleanup to conserve memory
    }

    private void sendEmail(String email, String messageTxt) {
        System.out.println("[EMAIL] " + messageTxt);

        // TODO send email with javax.mail
        //        Session mailSession = Session.getDefaultInstance(new Properties());
        //        MimeMessage msg = new MimeMessage(mailSession);
        //        try {
        //            msg.setFrom(new InternetAddress("admin@rating.vaadin.org"));
        //            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email));
        //            msg.setSubject("Rating login");
        //            msg.setText(messageTxt);
        //            Transport.send(msg);
        //        } catch (MessagingException e) {
        //            e.printStackTrace();
        //        }
    }

    public String login(String identity) {
        try {
            return idToEmailMap.get(Long.valueOf(identity));
        } catch (Exception any) {
            return null;
        }

    }

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
        // Fire CDI event about the update
    }

    public double getRating(Presentation presentation) {
        Query q = em.createQuery("select avg(r.rating) from Rating r where r.presentation = :p");
        q.setParameter("p", presentation);
        Double average = (Double) q.getSingleResult();
        return average == null ? 0 : average.doubleValue();
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
        // Fire CDI event about the update
        return p;
    }

    public void updatePresentation(Presentation presentation) {
        em.merge(presentation);
        // Fire CDI event about the update
    }

    public void deletePresentation(Presentation presentation) {
        Query q = em.createQuery("delete from Presentation p where p.id=" + presentation.getId());
        q.executeUpdate();
        // Fire CDI event about the update
    }

    @PostConstruct
    void mockupDataInitializer() {
        for (int i = 0; i < 50; i++) {
            Presentation p = new Presentation();

            p.setSpeaker(mockupSpeakers[(int) (mockupSpeakers.length * Math.random())]);
            p.setTopic(mockupTopics[(int) (mockupTopics.length * Math.random())]);
            p.setStartTime(new Date(System.currentTimeMillis() + (int) (10 * 24 * 60 * 60 * 1000 * Math.random())));
            StringBuilder sb = new StringBuilder();
            while (Math.random() < 0.8)
                sb.append(mockupOverviewPart[(int) (mockupOverviewPart.length * Math.random())] + " '");
            p.setOverview(sb.toString());
            em.persist(p);
            while (Math.random() < 0.9) {
                Rating r = new Rating();
                r.setEmail("anonymous " + Math.round(Math.random() * 10000));
                r.setPresentation(p);
                r.setRating(Math.round(Math.random() * 4 + 1));
                em.persist(r);
            }
            while (Math.random() < 0.5)
                addComment("anonymous " + Math.round(Math.random() * 10000), p, mockupOverviewPart[(int) (mockupOverviewPart.length * Math.random())]);

        }

    }

    static final String[] mockupSpeakers = {"Joonas Lehtinen", "Ray Cromwell", "Brian Slesinsky", "Colin Alworth", "Daniel Kurka", "Murat Yener", "Roberto Lublinerman", "Christian Sadilek", "Julien Dramaix", "Manuel Carrasco Moñino", "Goktug Gokdogan", "Artur Signell", "Jonathan Fuerth", "Sami Ekblad", "Michael Vogt", "Leif Åstrand", "James Nelson", "Jouni Koivuviita", "Kim Leppänen", "Philip Rogers", "Erik Jan de Wit", "Matthew Dempsky", "John Stalcup"};
    static final String[] mockupTopics = {"Increasing productivity with GWT code generator", "The GWT open source project\n" +
            "CSS3 meets GWT with GSS", "Comparing GWT Transport Mechanisms", "Introduction to GXT", "Rich HTML5 Web Apps: Typesafe Edition", "Client-server Hybrid Applications with Vaadin", "Easing Offline web applications development with GWT", "CSS Is Awesome", "Accessible web applications", "Building mobile applications with Vaadin TouchKit", "Workshop: How to become a GWT committer", "Why JavaScript sucks, but we still love it!", "RIA security based on OWASP Top 10", "GQuery, the perfect companion for your GWT project", "Theming GWT Applications with the Appearance Pattern", "New Tools for Debugging GWT Apps", "Compiler Deep Dive", "A glimpse into the future of GWT – Nextgen Js Interop and Widgets 2.0", "Increasing productivity with GWT code generators", "GWT & Phonegap, the next step", "GWT Widgets 101", "Introduction to Vaadin"};
    static final String[] mockupOverviewPart = {"GWT code Generators allow the GWT coder to generate Java code at compile time and have it then be compiled along with the rest of the project into JavaScript.",
            "You will learn more about how to use efficiently GWT code Generators.",
            "Being one of the pieces that is the most difficult to maintain, we will go through a series of patterns and tools that you can use to make the code more readable and more maintainable.",
            "Frameworks like GWT and Vaadin take care of some aspects of information security on the developers’ behalf whereas other aspects are still up to the developer to get right.",
            "This presentation walks through the OWASP Top 10 list of typical security flaws and investigates how they relate to applications developed using GWT or Vaadin.",
            "The goals of the presentation is to show how a mature framework can improve security and to make developers aware of aspects that they still need to handle themselves.",
            "GQuery is quite literally a rewrite of the popular jQuery library for the GWT platform.",
            "But it's more than that.",
            "It comes with a lot of features and plugins that can make your life as a GWT developer easier.",
            "In this talk, we will show you how GQuery works and, using concrete examples, show you how GQuery can help you in the development of your GWT project.",
            "Vaadin is a popular web framework that makes it possible to write rich user interfaces in server-side Java. Writing an application that lazily loads large amounts of data from the server, includes drag-and-drop, keyboard navigation and compelling visualizations would not require writing any HTML, JavaScript or resigning a REST API.",
            "While the server centric development model provides the best productivity, Vaadin also supports client-side development though the GWT based Java to JavaScript compiler as well as JavaScript.",
            "The default looks of the application can be customized with CSS and Sass.",
            "We'll also discuss on what are the differences and the relationship between Vaadin and GWT.",
            "The session should give you everything you need to get started building your own apps with the free Apache-licensed Vaadin."};

}
