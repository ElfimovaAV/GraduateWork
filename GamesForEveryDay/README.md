Создан Java-проект:
- через [spring.io](https://start.spring.io)
    - Project: Maven
    - Spring Boot  3.2.3
    - Project Metadata:
        - Group: ru.gb
        - Artifact: GamesForEveryDay
        - Name: GamesForEveryDay
        - Description: Graduate project for Spring Boot
        - Package name: ru.gb.backend
        - Packaging: Jar
        - Java: 17
    - Dependencies:
        - Spring Data JPA - _сохранение данных в SQL-хранилищах с помощью Java Persistence API с использованием Spring Data и Hibernate_
        - MySQL Driver - _драйвер JDBC для MySQL_
        - Lombok - _создание get,set-методов, конструкторов и многое другое_
        - Spring Web - _создание веб-приложения, включая RESTful, с использованием Spring MVC. Использует Apache Tomcat в качестве встроенного контейнера по умолчанию_
        - Spring Security - _Легко настраиваемая платформа аутентификации и контроля доступа для приложений Spring._
        - Liquibase Migration - _Миграция базы данных Liquibase и библиотека управления версиями._
    - Libraries:
        - JSON Web Tokens - _создание данных с необязательной подписью и/или необязательным шифрованием._
     

- Содержимое [application.properties](./src/main/resources/application.properties)

Поднята база данных в докер-контейнере:
- Docker: `docker run --name games_for_every_day -p 8000:3306 -e MYSQL_ROOT_PASSWORD=12345678 -d mysql`


Коллекция Postman [Games For Every Day.postman_collection.json](./.postman/Games%20For%20Every%20Day.postman_collection.json)

