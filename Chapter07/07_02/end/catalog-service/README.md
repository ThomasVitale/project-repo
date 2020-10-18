# Catalog Service

## Application tasks

**Build the application**

```bash
./gradlew build
```

**Run the application (Boot)**

```bash
./gradlew bootRun
```

**Run tests**

```bash
./gradlew test
```

## Docker tasks

**Build the application image from Cloud Native Buildpacks**

```bash
./gradlew bootBuildImage
```

**Run application as Docker container**

```bash
docker run -d --name catalog-service -p 9001:9001 --net catalog-network -e SPRING_DATASOURCE_URL=jdbc:postgresql://polardb-catalog:5432/polardb_catalog <your_docker_username>/catalog-service:0.0.1-SNAPSHOT
```

**Remove the application container**

```bash
docker rm -f catalog-service
```

## Docker Compose tasks

**Start containers**

```bash
docker-compose up -d
```

**Remove containers**

```bash
docker-compose down
```

## Database tasks (Kubernetes)

**Create PostgreSQL Deployment**

```bash
kubectl create -f k8s/polardb-catalog-deployment.yml
```

**Create PostgreSQL Service**

```bash
kubectl create -f k8s/polardb-catalog-service.yml
```

**Delte PostgreSQL Deployment**

```bash
kubectl delete -f k8s/polardb-catalog-deployment.yml
```

**Delete PostgreSQL Service**

```bash
kubectl delete -f k8s/polardb-catalog-service.yml
```
