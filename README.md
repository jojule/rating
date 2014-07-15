# Rating example app

## Pre-requirements

* Java 8
* Java EE server with Web Profile support. For example:
  * TomEE 1.7.0+
  * Glassfish 4.0.1+
  * WildFly 8.1
* Maven
* Git
* Maven & Java 8 compatible IDE. For example:
  * Eclipse Luna
  * IntelliJ IDEA 13
  * NetBeans 8

## Features

This is a simple application for rating conference presentations. While the application works, is usable by multiple users and persists its data, it is just an example application built for learning. Here are some of the features of the application from developer point of view:

* Full stack Vaadin application
* Utilizes Java 8 features
* Fully built with Vaadin CDI
* Uses JPA directly and through Vaadin JPAContainer
* Includes simple authentication and authorization
* Uses Valo theme
* Includes responsive layout
* Customizes Valo theme
* Uses an add-on widget
* Includes a clearly separated service layer

## Application notes

This is a mockup app that "authenticates" with email. Instead of actually sending the email, it just adds URL to console. Please use that email to log in or click "skip login" link in the app. 

Any email that starts with "admin" gives access to admin functionality. For example, try to login as `admin@vaadin.com`

# Lab

This example app was built for Worldwide Vaadin Hack day organized by nighthacking.com in June 2014. The example app has been updated to use Vaadin 7.3 since the lab. You can find a recording of the lab here:

* [Vaadin Worldwide Hack Day Part 1](http://nighthacking.com/vaadin-worldwide-hack-day-part-1/)
* [Vaadin Worldwide Hack Day Part 2](http://nighthacking.com/vaadin-worldwide-hack-day-part-2/)

The lab teaches how to build such an application in 8 steps. Feel free to experiment, modify and break things - working app is always just a git checkout away :)

## Step 1: Hello World

Lets start from the basics. This is practically simplest possible Vaadin application with only one java class describing the user interface (UI). Check it out, play with it and learn the structure of a Vaadin application.

[Branch: step-1-helloworld](https://github.com/jojule/rating/tree/step-1-helloworld)

## Step 2: Todo List

Start learning how to layout Vaadin components. Start from the previous step and try to build a simple todo list application by yourself. Try to avoid reading the source code for this step just yet, come up with your own todo list design instead. [Book of Vaadin](https://vaadin.com/book) is your best friend here.

[Branch: step-2-todolist](https://github.com/jojule/rating/tree/step-2-todolist)

## Step 3: CDI

Lets take a deep breath and jump into Java EE world. Here we'll refactor the application to use Vaadin CDI add-on and build a simple master-detail navigation using Vaadin Navigator.

[Branch: step-3-cdi](https://github.com/jojule/rating/tree/step-3-cdi)

## Step 4: JPA

This far we have only had a UI with no data. We'll add a simple JPA based data model and build UIs for viewing and modifying this data.

[Branch: step-4-jpa](https://github.com/jojule/rating/tree/step-4-jpa)

## Step 5: Login

Most applications need to know who you are and limit access to certain functions to only some users. In this case we'll only give access to administrator for editing and adding new conference presentations while everyone can edit them.

Vaadin does not limit how you may implement login and security. It can work with most Java security frameworks. In this case we implement a simple email based login and store the identity in a session scoped bean.

[Branch: step-5-login](https://github.com/jojule/rating/tree/step-5-login)

## Step 6: Forms

Business apps are all about data, grids and forms. Here we show how to build a customized form and bind it to data. The form also includes declarative JSR-303 based bean validation.

[Branch: step-6-forms](https://github.com/jojule/rating/tree/step-6-forms)

## Step 7: Add-ons

While Vaadin Framework has a good set of commonly used UI components, one can never have enough. Fortunately the awesome Vaadin community has developed several hundreds of components to choose from. Just take a peek into [Vaadin Directory](https://vaadin.com/directory) to see what is out there.

In this step we'll integrate a third party add-on from Vaadin directory and compile our widgetset for the first time. For the previous steps we were using a built-in precompiled widgets that included just the component from the framework.

[Branch: step-7-addons](https://github.com/jojule/rating/tree/step-7-addons)

## Step 8: Theme

For the steps 1-7 we have been using a built in Valo theme. While it is beautiful, it would be boring if all the apps would look the same. Lets customize the team and experiment with responsive layouts. 

Making changes to theme is as simple as editing `src/main/webapp/VAADIN/themes/rating/rating.scss`. Built-in SASS compiler will update the CSS on the fly - just reload your web page to see your latest edits. 

The rating.css file there are a handful of Valo parameter examples included. Try commenting them out - one set of parameters at time. Start tuning these parameters further by yourself.

[Branch: step-8-theme](https://github.com/jojule/rating/tree/step-8-theme)



