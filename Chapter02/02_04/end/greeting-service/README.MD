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
docker run --name greeting-service -p 8080:8080 arcticgreetings/greeting-service:0.0.1-SNAPSHOT
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
docker rmi arcticgreetings/greeting-service:0.0.1-SNAPSHOT
```

## Kubernetes tasks

### Create Deployment for application container

Before running the command, make sure you have built the Docker image as explained above.

```bash
kubectl create deployment greeting-service-deployment --image=arcticgreetings/greeting-service:0.0.1-SNAPSHOT
```

### Create Service for application Deployment

```bash
kubectl expose deployment greeting-service-deployment --type=ClusterIP --name=my-service --port=8080
```

### Port forwarding from localhost to Kubernetes cluster

```bash
kubectl port-forward service/my-service 8000:8080
```

### Delete Deployment for application container

```bash
kubectl delete deployment greeting-service-deployment
```

### Delete Service for application container

```bash
kubectl delete svc my-service
```
