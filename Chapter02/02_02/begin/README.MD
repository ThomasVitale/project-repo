# Greeting Service

Initialize a Spring project with your favourite method, for example from Spring Initialzr or your IDE.
During the initialization, you need to select the "Spring Web" dependency.

If you prefer, you can initialize the project with this command:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.arcticgreetings -d artifactId=greeting-service -d name=greeting-service -d packageName=com.arcticgreetings.greeting-service -d dependencies=web -d javaVersion=11 -d type=gradle-project -o greeting-service.zip
```

For using Maven instead of Gradle:

```bash
curl https://start.spring.io/starter.tgz -d groupId=com.arcticgreetings -d artifactId=greeting-service -d name=greeting-service -d packageName=com.arcticgreetings.greeting-service -d dependencies=web -d javaVersion=11 -d type=maven-project -o greeting-service.zip
```
