# Rating example app

## Pre-requirements

* Java 8
* Java EE server with Web Profile support
  * For example TomEE 1.7.0+, Glassfish 4.0.1+ or WildFly 8.1
* Maven
* Git
* Maven & Java 8 compatible IDE

## Application notes

This is a mockup app that "authenticates" with email. Instead of sending the email, it just adds URL to console. Please use that email to log in. Also note that any email that starts with "admin" gives access to admin functionality.

# Workshop

This example app was built for Worldwide Vaadin Hack day organized by nighthacking.com. For the full workshop notes, see workshop.pdf

## Valo theme

This branch uses early developer prerelease of upcoming Valo theme. It required not yet released Vaadin 7.3 for
theme compilation. Because 7.3 is not released yet, you must use sass-lang compiler and launch it in src/main/webapp/VAADIN/themes directory with command

sass --watch --scss .:.
