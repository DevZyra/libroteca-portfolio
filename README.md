# libroteca-portfolio
[![Build Status](https://circleci.com/gh/DevZyra/libroteca-portfolio.svg?style=svg&circle-token=<YOUR_STATUS_API_TOKEN>)](<LINK>)

# About
REST/mvc E-commerce like application
Spring JPA & MySql
Jwt authentication
(JMS)ActiveMQartemis


- User creation, login, authentication
- Book search: by title/ author
- Placing order MVC/REST
- Showing Users, Books, Orders, Addresses
- HATEOAS
- After placing an order, application sends it (jms:ActiveMq-artemis) to a consumer service.

Swagger rest documentation : ~:8080/swagger-ui/ 

# Docker images
```
Mysql 
$ docker run -e MYSQL_ROOT_PASSWORD=root -d mysql

$ docker exec -it [cont.name] bash

$ mysql -u root
$ set password for root = 'root';
```

ActiveMq-artemis
```
$ docker run -it --rm \
  -p 8161:8161 \
  -p 61616:61616 \
  vromero/activemq-artemis
```
# Install & run: 
Navigate to project dir:

```
$ cd libroteca 
$ mvn install
```

Navigate to web-module:

```
$ cd libroteca-web
$ mvn spring-boot:run
```

# Login 

pre-defined users : 
```
-u admin / user
-p admin / user
```

@Post   "/rest/login"  
{
    "username":"admin",
    "password":"admin"
}

