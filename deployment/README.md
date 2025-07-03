# 📊 Microservicio de Estadísticas - Muebles SAS
Este microservicio permite recibir estadísticas de interacción de usuarios, validar su integridad mediante hash MD5, 
almacenarlas en DynamoDB y publicar eventos en RabbitMQ, utilizando una arquitectura basada en el scaffold Clean Architecture de Bancolombia y tecnología reactiva con Spring WebFlux.

## 📁 Estructura del Proyecto
* Model: Entidades del dominio y gateways.
* Use Case: Lógica de negocio.
* Entry Points: Handler y rutas HTTP (WebFlux).
* Driven Adapters: Integración con DynamoDB y RabbitMQ.
* Docker Compose: Servicios para pruebas locales.

## 🚀 Tecnologías
* Java 17
* Spring WebFlux
* RabbitMQ
* DynamoDB Local
* Clean Architecture Scaffold de Bancolombia
* Lombok
* Reactor
* Docker / Docker Compose
* JUnit 5, Mockito
* Jacoco

## 🧪 Endpoint
POST /stats
```json
Request Body:
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

### Reglas de validación del hash:
Debe ser el MD5 de la cadena concatenada: "250,25,10,100,100,7,8"

## ⚙️ Cómo ejecutar el servicio
1. Clona el repositorio
   git clone https://github.com/tu-usuario/microservicio-stats.git
   cd microservicio-stats

2. Levanta el entorno local con Docker Compose
   docker-compose up -d

* Esto inicia:
DynamoDB Local (http://localhost:8000)
RabbitMQ (http://localhost:15672 / guest:guest)

3. Ejecuta la aplicación
   Desde la raíz del proyecto:
./gradle clean install
./gradle spring-boot:run

* O bien ejecuta directamente desde tu IDE (módulo app-service).

## 🧪 Cómo probar el endpoint
✅ Con curl:
```bash
curl -X POST http://localhost:8080/stats \
-H "Content-Type: application/json" \
-d '{
"totalContactoClientes": 250,
"motivoReclamo": 25,
"motivoGarantia": 10,
"motivoDuda": 100,
"motivoCompra": 100,
"motivoFelicitaciones": 7,
"motivoCambio": 8,
"hash": "02946f262f2eb0d8d5c8e76c50433ed8"
}'
```
* ✅ Con Postman o Insomnia:
Método: POST
URL: http://localhost:8080/stats
Body: JSON (como arriba)

## 🧪 Ejecutar pruebas
```bash
./gradle test
```
Esto ejecuta las pruebas unitarias y de integración.

Ver reporte de cobertura:
```bash
./gradle verify
```
El reporte estará en:
```bash
target/site/jacoco/index.html
```

## 📚 Arquitectura
Este proyecto sigue el patrón Clean Architecture:
```bash
domain/
└── model/
└── usecase/
infrastructure/
├── entry-points/       --> WebFlux
└── driven-adapters/    --> DynamoDB, RabbitMQ
```
## 📌 Notas
Asegúrate de que los puertos 8000, 5672, 15672 y 8080 estén libres.
La cola event.stats.validated debe existir en RabbitMQ o se creará automáticamente.

## 📬 Contacto
* Autor: [jgquinta-Jose Guillermo Quintanilla Paredes]
* Correo: [jgquinta@bancolombia.com.co]
* GitHub: [https://github.com/guillermoqp]