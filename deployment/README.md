# 📊 Microservicio de Estadísticas - Muebles SAS
Este microservicio recibe estadísticas de interacción con usuarios, valida un hash MD5, guarda los datos en **DynamoDB** y 
publica eventos en **RabbitMQ**, usando Clean Arquitecture Scaffol de bancolombia y desarrollo reactivo con **Spring WebFlux**.
---
## ⚙️ Tecnologías
- Java 17
- Gradle 8.9
- Spring Boot + WebFlux
- Clean Architecture (Bancolombia Scaffold v3.23.1)
- Lombok
- DynamoDB (local)
- RabbitMQ
- Docker Compose
- JUnit 5 + Mockito + Jacoco
---
## 🚀 Instrucciones para ejecución
### 🔧 Requisitos previos
- Docker y Docker Compose
- Java 17+
- Gradle 8.9+
- AWS CLI (opcional para testeo de DynamoDB local)
---
### 🐳 Levantar entorno con Docker
```bash
docker-compose up -d
```
* Esto levantará:
DynamoDB Local en localhost:8000
RabbitMQ en localhost:5672 (UI en localhost:15672)
## 📦 Crear tabla DynamoDB (una vez)
```bash
aws dynamodb create-table \
--table-name stats_table \
--attribute-definitions AttributeName=timestamp,AttributeType=S \
--key-schema AttributeName=timestamp,KeyType=HASH \
--provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1 \
--endpoint-url http://localhost:8000
```
## ▶️ Ejecutar el servicio
```bash
./gradlew bootRun
```
El servicio quedará disponible en: http://localhost:8080
## 🧪 Probar el endpoint
### ➕ POST /stats
POST /stats
Content-Type: application/json
#### 🧾 Cuerpo del request:
```json
{
"totalContactoClientes": 250,
"motivoReclamo": 25,
"motivoGarantia": 10,
"motivoDuda": 100,
"motivoCompra": 100,
"motivoFelicitaciones": 7,
"motivoCambio": 8,
"hash": "02946f262f2eb0d8d5c8e76c50433ed8"
}
```
### ✅ Respuesta esperada:
200 OK si el hash es válido (se guarda y se publica).
400 Bad Request si el hash no coincide con los campos.
## 🧪 Ejecutar pruebas
```bash
./gradlew test
```
## 📈 Ver cobertura de pruebas
```bash
./gradlew jacocoTestReport
```
## 📂 Estructura del proyecto
```scss
.
├── domain
│   ├── model (entidades)
│   └── usecase (interfaces de lógica, implementaciones)
├── infrastructure
│   ├── driven-adapters
│   │   ├── dynamodb
│   │   └── rabbitmq
│   └── entry-points
│       └── reactive-web
├── docker-compose.yml
└── README.md
```
## 📬 Contacto
* Autor: [jgquinta-Jose Guillermo Quintanilla Paredes]
* Correo: [jgquinta@bancolombia.com.co]
* GitHub: [https://github.com/guillermoqp]