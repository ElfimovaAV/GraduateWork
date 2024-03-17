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
        - Java: 20
    - Dependencies:
        - Spring Data JPA - _сохранение данных в SQL-хранилищах с помощью Java Persistence API с использованием Spring Data и Hibernate_
        - MySQL Driver - _драйвер JDBC для MySQL_
        - Lombok - _создание get,set-методов, конструкторов и многое другое_
        - Spring Web - _создание веб-приложения, включая RESTful, с использованием Spring MVC. Использует Apache Tomcat в качестве встроенного контейнера по умолчанию_

- Содержимое [application.properties](./src/main/resources/application.properties)

Поднята база данных в докер-контейнере:
- Docker: `docker run --name games_for_every_day -p 8000:3306 -e MYSQL_ROOT_PASSWORD=12345678 -d mysql`

- Стартовый SQL-скрипт [тут](./sql/query.sql)

TO_DO:
- Через liquibase заполнить таблицы games, game_rules добавить в таблицу games, связать расписание на неделю
- Включить бизнес логику в контроллеры
- Добавить thymeleaf (Seminar6 ScrumBoard)
- Добавить эндпойнт /api и создать коллекцию Postman

