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

**Create Order Deployment**

```bash
kubectl create -f k8s/order-deployment.yml
```

**Create Order Service**

```bash
kubectl create -f k8s/order-service.yml
```

**Delete Order Deployment**

```bash
kubectl delete -f k8s/order-deployment.yml
```

**Delete Order Service**

```bash
kubectl delete -f k8s/order-service.yml
```

## Database tasks (Kubernetes)

**Create PostgreSQL Deployment**

```bash
kubectl create -f k8s/polardb-order-deployment.yml
```

**Create PostgreSQL Service**

```bash
kubectl create -f k8s/polardb-order-service.yml
```

**Delte PostgreSQL Deployment**

```bash
kubectl delete -f k8s/polardb-order-deployment.yml
```

**Delete PostgreSQL Service**

```bash
kubectl delete -f k8s/polardb-order-service.yml
```
