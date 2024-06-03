# Spring Boot without Web - Only with CommandLineRunner

This is a simple Spring Boot application that does not have a web server. It only has a CommandLineRunner that runs when the application starts.

## What's on this project?

This project has a single class, `SpringBootWithoutWebApplication`, that has a `CommandLineRunner` that runs when the application starts.

The `CommandLineRunner` is a functional interface that has a single method, `run`, that receives an array of strings as an argument. This method is called when the application starts.

## How to run

1. Clone the repository
2. Run the following command in the root directory of the project:

```shell
mvn spring-boot:run
```
