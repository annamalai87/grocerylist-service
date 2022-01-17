# Grocery List

The grocery list application is very light-weight and built using kotlin coroutines from the ground up using ktor
framework. The application creates and maintains a list of items that are to be purchased.

## Tech Stack

- Ktor
- Netty server
- Kotlin Coroutines
- MongoDB reactive driver

## Local set up

- Gradle clean build to create the uber jar
   ```
    ./gradlew clean build
   ```
- Spin up docker compose using docker-compose.yml file in the root directory
   ```
    docker-compose up -d
   ```
- Run the application using the following command
   ```
    java -jar build/libs/grocerylist.jar
   ```

## Swagger specification
- Refer openapi/grocery-list.yaml for the swagger specification

## Insomnia script (prefer Insomnia to Postman)
- Insomnia yaml is available in the root directory of the project
