# 🅂🄾🄾🄳🄰

> is a simple Spring Boot Rest Application created 
> for study purposes. It uses **H2** in memory database
> to reduce the complexity of initial launch and 
> testing. Java Persistence API **(Hibernate)** makes
> this application comfortable to easily access all
> data from a database. Authentication logic implemented
> by using **Spring Security** and **[JJWT](https://mvnrepository.com/artifact/io.jsonwebtoken/jjwt)**.


### Endpoints

    # To register a new user with USER privileges

    POST http://localhost:8080/register
    Content-Type: application/json

    {
        "username": "my_username",
        "password": "StrongPass123",
        "passwordRepeat": "StrongPass123"
    }
<br>

    # To get authentication and get JWT 

    POST http://localhost:8080/authenticate
    Content-Type: application/json
    
    {
        "username": "bekzhan",
        "password": "123456Xz"
    }
<br>

    # To get list of all users
    # Use Authorization header to access
    # Admin authority needed
    # use username:admin, password: admin

    GET http://localhost:8080/admin/users
    Content-Type: application/json
    Authorization: Bearer <JWT access token>
