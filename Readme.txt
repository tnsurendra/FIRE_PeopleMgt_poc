## Synechron Reconcillation Engine


## Steps to Setup
**1. Run the app using maven**

```bash
cd fire-person-rest
mvn spring-boot:run
```


That's it! The application can be accessed at `http://localhost:8082`.

**Acccessing the REST End point:

http://localhost:8082/

You may also package the application in the form of a jar and then run the jar file like so -

```bash
mvn clean package
java -jar target/file-demo-0.0.1-SNAPSHOT.jar
```

**Containerizing the application using Docker

docker build -t synechron/recon-engine-docker .


