# Description

This is an HTTP sever written from the ground up in java. It uses Spring Boot for DI, Config and Logging otherwise the
entire web support is written manually without Spring Boot Web. This project uses Maven as dependency manager.

# Usage

The spring starter module added can be built with maven, added as a dependency to any other spring project and run as
part of @SpringBootApplication. This way it can be extended through the of annotations to declare new routing, exception
handling and middleware.

The core module is framework-agnostic and can run with other runtimes if additional config is provided, yet this is not
tested and some frameworks might not support several runtime features of it.

Since the server handles everything from application loop to http request/response lifecycle, it should not be
interfered with and only extended in the supported ways.

# Extension Through Client

* Middleware: Add @Middleware to a @Component class extending either PreProcessMiddleware or PostProcessMiddleware and
  declare the containing package inside your config file to register a new middleware class.
* Routing: Add @Routing to specifically shaped methods inside a @Component class and declare the containing package
  inside your config file to register a new handler method.
* ExceptionHandling: Add @ExceptionHandling to specifically shaped methods inside a @Component class and declare the
  containing package inside your config file to register a new handler method.
* HttpHeader, HttpResponseStatus and HttpContentType can be extended by using of() methods. See examples in model
  package
  of sample client module.

# Config

* http.server.socket.port -> set server port number; Default is 8080.
* http.server.socket.timeout -> set socket timeout in milliseconds; Default is 20000.
* http.server.http.max-content-length -> set max content length to read from requests in bytes; default is 65536
* http.server.middleware-packages -> set package names to be scanned for @Middleware.
* http.server.routing-packages -> set package names to be scanned for @Routing.
* http.server.exception-handling-packages -> set package names to be scanned for @ExceptionHandling.
