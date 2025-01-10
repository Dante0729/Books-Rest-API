# Bookstore Management System

This is a comprehensive bookstore management system built using Spring Boot, demonstrating RESTful API development with security implemented via JWT authentication. It manages books, authors, users, shopping carts, wishlists, comments, and ratings.

## Features

- **Book Management**: Add, update, delete, and query books.
- **Author Management**: Register authors and associate them with books.
- **User Accounts**: User registration, authentication, and management.
- **Shopping Cart**: Add and remove books from a user's shopping cart.
- **Wishlist**: Users can create wishlists and add books to them.
- **Comments and Ratings**: Users can rate books and leave comments.
- **Security**: Secured endpoints with JWT authentication.

## Setup and Installation

1. **Prerequisites**:
    - Java 17
    - Maven
    - PostgreSQL

2. **Database Configuration**: Create a PostgreSQL database and update the `application.properties` with your database credentials:

    ```properties
    spring.datasource.url=jdbc:postgresql://localhost:5432/books
    spring.datasource.username=postgres
    spring.datasource.password=yourPassword
    ```

3. **Running the Application**:
    - Navigate to the project root directory.
    - Run `mvn spring-boot:run` to start the application.

## API Endpoints

The application defines several RESTful endpoints:

- `/api/v1/books` - For managing books.
- `/api/v1/authors` - For managing authors.
- `/api/v1/users` - For user registration and management.
- `/api/v1/shopping-cart` - For managing user shopping carts.
- `/api/v1/wishlists` - For managing user wishlists.
- `/api/v1/comments` - For adding and viewing comments on books.
- `/api/v1/ratings` - For adding and viewing ratings of books.

## Security

Endpoints are secured using JWT authentication. Use `/login` to authenticate and receive a token to be used with subsequent requests.

## Contribution

Feel free to fork the project and submit pull requests.

## License

[MIT](LICENSE.md)
