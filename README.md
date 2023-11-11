# Project 1
Simple Servlet API for product and order management.

### Usage
In order to launch the application it is necessary to have JDK21 and Maven installed.

#### Run with Maven
`mvn jetty:run`

#### Run with Docker
```bash
mvn clean package
docker build -t <image-name> .
docker run -d -p 8080:8080 <image-name>
```

After that the application should be running on the `localhost:8080`.

To explore functionality of the app, prepared html forms (`product-form.html` and `order-form.html`) can be used to display and create resources..