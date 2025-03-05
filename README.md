# Pay My Buddy

## Description

This application aims to allow clients to transfer money to manage their finances or pay their friends.

## Getting Started

These instructions will get you a copy of the project up and running on your local machine for development and testing
purposes.

### Prerequisites

What things you need to install the software and how to install them

- Java 21
- Maven 3.9.9
- MySQL 8.x
- Docker (only for running integration tests)

### Installing

A step by step series of examples that tell you how to get a development env running:

1.Install Java:

https://docs.oracle.com/en/java/javase/21/install/overview-jdk-installation.html

2.Install Maven:

https://maven.apache.org/install.html

3.Install MySQL:

https://dev.mysql.com/downloads/

4.Installer Docker: (only for integration tests)
https://www.docker.com/get-started/

### Database creation

Hibernate will automatically create the database schema if it does not exist.
The schema is also available in the SQL file "/src/main/resources/script/db_schema.sql".

For testing purposes, you can import data using the file "/src/main/resources/script/db_data_for_test.sql".
The default password for all users is: "Password@1".

### Database MPD

![MDP Schema](/src/main/resources/documentation/MPD.png)

### Database configuration

Copy and rename the "/src/main/ressources/script/.env.template" file into the projet directory as "/.env",
then update the file with your MySQL configuration.

### Testing

The app has unit tests and integration tests written.
For integration tests, the app uses Testcontainers to run the tests in a docker container.
https://testcontainers.com/

To run the tests from maven, go to the folder that contains the pom.xml file and execute the below command.

For unit tests : `mvn test`  
For unit and integration tests :  `mvn verify`      
For generating reporting : `mvn clean site`  
Surefire, JaCoCo, JavaDoc reporting are available in the project directory : "/target/site/index.html"