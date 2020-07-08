# Greeting Service

## Application tasks

### Run the application

```bash
./gradlew bootRun
```

### Run the tests

```bash
./gradlew test
```

### Package the application

```bash
./gradlew bootJar
```

## Docker tasks

### Build Docker image

```bash
./gradlew bootBuildImage
```

### Run application as Docker container

```bash
docker run --name greeting-service -p 8080:8080 arcticgreetings.com/greeting-service:1.0.0
```

### Stop application Docker container

```bash
docker stop greeting-service
```

### Remove application Docker container

```bash
docker rm greeting-service
```

### Remove application Docker image

```bash
docker rmi arcticgreetings.com/greeting-service:1.0.0
```