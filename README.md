# Football matches ticket shop
**Implementation of an online Taxi service for drivers written in Java.**

## Structure

###### The project has an N-tier structure and consists of the project layers:

* Controllers.
* Service layer(contains the business logic of thr project);
* DAO layer (JDBC API);
* Database layer;


###### Store clients can perform the following actions on this web-service:

Store **registered Users** can perform the following actions:

* login;
* see all information
* delete his cars
* delete himself or other drivers from the service;


## Technologies

* Java 11
* Maven 3.1.1
* Maven Checkstyle Plugin
* Hibernate
* MySQL
* Servlet API
* Apache Tomcat
* JDBC
* JSP
* JSTL


## To start the project you need:

1. Download and install the JDK
2. Download and install web-server (for example Apache Tomcat)
3. Download and install MySQL. Setup connection properties in **package core.util.ConnectionUtil** class
* user: "your username"
* password: "your password"
* db.url=jdbc:mysql://localhost_your_port_ /*your_db_name*?serverTimezone=EET
4. Run a project
