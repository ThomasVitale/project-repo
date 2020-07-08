# Catalog Service

Initialize a Spring project with your favorite method, for example from Spring Initialzr (https://start.spring.io/) or your IDE.
During the initialization, you need to select the "Spring Web" dependency.

If you prefer, you can initialize the project with this command:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.polarshopia.polarbookshop -d artifactId=catalog-service -d name=catalog-service -d packageName=com.polarshopia.polarbookshop.catalog-service -d dependencies=web -d javaVersion=11 -d type=gradle-project -o catalog-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.polarshopia.polarbookshop -d artifactId=catalog-service -d name=catalog-service -d packageName=com.polarshopia.polarbookshop.catalog-service -d dependencies=web -d javaVersion=11 -o catalog-service.zip
```
