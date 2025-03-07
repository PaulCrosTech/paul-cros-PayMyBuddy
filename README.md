# Pay My Buddy

## Description

This application aims to allow clients to transfer money to manage their finances or pay their friends.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

### Prerequisites

What things you need to install the software and how to install them :

- Java 21
- Maven 3.9.9
- MySQL 8.x
- Docker (only for running integration tests)

### Installing

1.Install Java:  
https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html

2.Install Maven:  
https://maven.apache.org/install.html

3.Install MySQL:  
https://dev.mysql.com/downloads/

4.Install Docker: (only for integration tests)  
https://www.docker.com/get-started/

### Configuration

Copy and rename the "/src/main/ressources/script/.env.template" file into the projet directory as "/.env".  
In the ".env" file you have to :

- configure your MySQL connection
- set the default balance for new users (default is 100)
- set the default port for the application (default is 8080)

### Database Scripts

Hibernate will automatically create the database schema if it does not exist.  
The schema is also available in the SQL file "/src/main/resources/script/db_schema.sql".

For testing purposes, you can import data using the file "/src/main/resources/script/db_data_for_test.sql".  
The default password for all users is: "Password@1".

### Run the application

To run the application, you can :

- use the command : `mvn spring-boot:run`
- run the main class : `PayMyBuddyApplication.java`

Then go to the URL http://localhost:8080/

## Database MPD

![MDP Schema](/src/main/resources/documentation/MPD.png)

## Testing

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

For unit tests and reports : `mvn clean test site`

For unit, integration tests and reports :  `mvn clean verify site`  
Integration tests used TestContainer, so don't forget to start your docker first (https://testcontainers.com/).

Surefire, JaCoCo, JavaDoc reporting are available in the project directory : "/target/site/index.html"

