Raiden JPA
=========

A really fast JPA subset implementation to be use in development enviroments.

Hibernate vs Raiden JPA
=========

This video show two unit tests. The first is a test in a simple project and the second is a more complex (private) project: https://www.youtube.com/watch?v=cpJuVDhoV0Q

Is it ready to use?
=========

Currently, there are already two (private) projects using it. Although it is not complete, you can try it in your project without fear, because its intention is not go to production, just help you in development environment. You can see issues in GitHub.

Do you want help to put it on your project? Send me an email: andreitognolo@gmail.com

Getting Started
=========

Repository (it is not in maven central yet):

    <repositories>
        <repository>
            <id>raidenjpa</id>
            <url>http://storage.googleapis.com/raiden-jpa</url>
        </repository>
    </repositories>
    
Dependency:

    <dependency>
        <groupId>org.raidenjpa</groupId>
        <artifactId>raidenjpa-core</artifactId>
        <version>0.0.3</version>
    </dependency>
    
In your code:

    // As soon as possible, it will be able to use <provider> in persistence.xml
    EntityManagerFactory emf = new RaidenEntityManagerFactory();

Development
=========

Build Status (by Travis):

[![Build Status](https://travis-ci.org/andreitognolo/raidenjpa.png)](http://travis-ci.org/andreitognolo/raidenjpa)

## @Before

You need Java 7+ and maven 3+
