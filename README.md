# Description

This is an HTTP sever written from the ground up in java. It uses Spring Boot for DI, Config and Logging otherwise the
entire
web support is written manually without Spring Boot Web. This project uses Maven as dependency manager.

# Usage

This sever can run standalone through the main class; Although this is not recommended as it requires changing the
internals to add additional routing, exception handling and middleware.

Instead, this project can be built with maven, added as a dependency to any other project and run as part of
@SpringBootApplication.
This way it can be extended through the of annotations to declare new routing, exception handling and middleware.

Since the server handles everything from application loop to http request/response lifecycle, it should not be
interfered with
and only extended in the supported ways.

Currently, no @Bean extension or replacement support is featured. Change at your own risk.

# Extension Through Client

* Middleware: Add @Middleware to any class extending either PreProcessMiddleware or PostProcessMiddleware and declare
  the containing package inside your config file to register a new middleware class.
* Routing: Add @Routing to specifically shaped methods inside a @Component class and declare the containing package
  inside your config file to register a new handler method.
* ExceptionHandling: Add @ExceptionHandling to specifically shaped methods inside a @Component class and declare the
  containing package inside your config file to register a new handler method.

# Config

* app.http.server.socket.port -> set server port number; Default is 8080.
* app.http.server.socket.timeout -> set socket timeout in milliseconds; Default is 20000.
* app.http.server.http.max-content-length -> set max content length to read from requests in bytes; default is 65536
* app.http.server.middleware-packages -> set package names to be scanned for @Middleware.
* app.http.server.routing-packages -> set package names to be scanned for @Routing.
* app.http.server.exception-handling-packages -> set package names to be scanned for @ExceptionHandling.
