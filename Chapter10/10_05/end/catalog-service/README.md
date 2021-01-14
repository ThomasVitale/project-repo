# Catalog Service

## Application tasks (Gradle)

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

**Build the application image from Cloud Native Buildpacks**

```bash
./gradlew bootBuildImage
```

## Application tasks (Kubernetes)

**Create Catalog Deployment**

```bash
kubectl create -f k8s/catalog-deployment.yml
```

**Create Catalog Service**

```bash
kubectl create -f k8s/catalog-service.yml
```

**Delte Catalog Deployment**

```bash
kubectl delete -f k8s/catalog-deployment.yml
```

**Delete Catalog Service**

```bash
kubectl delete -f k8s/catalog-service.yml
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
