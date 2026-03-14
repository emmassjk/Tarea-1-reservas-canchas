# 🏟️ Sistema de Reservas de Canchas

Sistema distribuido compuesto por dos microservicios independientes desarrollados con **Spring Boot (Java)**, que permiten gestionar canchas deportivas y sus reservas.

---

## 📁 Estructura del Proyecto

```
├── AgregarCanchas/      → Microservicio para gestionar canchas (puerto 8081)
└── ReservarCanchas/     → Microservicio para gestionar reservas (puerto 8082)
```

---

## ⚙️ Requisitos Previos

- Java 17+
- Maven
- Postman (para pruebas)

---

## 🚀 Cómo Ejecutar

### 1. Microservicio AgregarCanchas
```bash
cd AgregarCanchas
./mvnw spring-boot:run
```
> Corre en: `http://localhost:8081`

### 2. Microservicio ReservarCanchas
```bash
cd ReservarCanchas
./mvnw spring-boot:run
```
> Corre en: `http://localhost:8082`

---

## 🔵 Microservicio 1 — AgregarCanchas (Puerto 8081)

Gestiona el registro y consulta de canchas disponibles. Los datos se almacenan en memoria y los IDs se generan automáticamente con un contador atómico.

### Endpoints

| Método | Endpoint         | Descripción                        |
|--------|------------------|------------------------------------|
| GET    | `/canchas`       | Retorna todas las canchas          |
| GET    | `/canchas/{id}`  | Retorna una cancha por su ID       |
| POST   | `/canchas`       | Agrega una nueva cancha            |

---

### `GET /canchas` — Listar todas las canchas

**Ejemplo en Postman:**
- Método: `GET`
- URL: `http://localhost:8081/canchas`

**Respuesta (200 OK):** *(lista vacía si aún no se han agregado canchas)*
```json
[
  {
    "id": 1,
    "nombre": "Cancha Central",
    "tipo": "Fútbol",
    "disponible": true
  },
  {
    "id": 2,
    "nombre": "Cancha Norte",
    "tipo": "Tenis",
    "disponible": false
  }
]
```

---

### `GET /canchas/{id}` — Obtener una cancha por ID

**Ejemplo en Postman:**
- Método: `GET`
- URL: `http://localhost:8081/canchas/1`

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Cancha Central",
  "tipo": "Fútbol",
  "disponible": true
}
```

> ⚠️ Si el ID no existe, el endpoint retorna un body vacío (el stream no encuentra coincidencia y devuelve `Optional.empty()`).

---

### `POST /canchas` — Agregar una nueva cancha

> El campo `id` **no debe enviarse** en el body — se asigna automáticamente.

**Ejemplo en Postman:**
- Método: `POST`
- URL: `http://localhost:8081/canchas`
- Headers:
  ```
  Content-Type: application/json
  ```
- Body (raw → JSON):
```json
{
  "nombre": "Cancha Sur",
  "tipo": "Básquetbol",
  "disponible": true
}
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "nombre": "Cancha Sur",
  "tipo": "Básquetbol",
  "disponible": true
}
```

---

## 🟢 Microservicio 2 — ReservarCanchas (Puerto 8082)

Gestiona la creación y consulta de reservas. Permite filtrar reservas por cancha.

### Endpoints

| Método | Endpoint                      | Descripción                                   |
|--------|-------------------------------|-----------------------------------------------|
| GET    | `/reservas`                   | Retorna todas las reservas                    |
| POST   | `/reservas`                   | Crea una nueva reserva                        |
| GET    | `/reservas/cancha/{canchaId}` | Retorna las reservas de una cancha específica |

---

### `GET /reservas` — Listar todas las reservas

**Ejemplo en Postman:**
- Método: `GET`
- URL: `http://localhost:8082/reservas`

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "canchaId": 1,
    "usuario": "Juan Pérez",
    "fecha": "2026-03-20",
    "hora": "10:00"
  },
  {
    "id": 2,
    "canchaId": 2,
    "usuario": "María López",
    "fecha": "2026-03-21",
    "hora": "15:30"
  }
]
```

---

### `POST /reservas` — Crear una nueva reserva

> Si el body incluye un `id`, se usa ese valor. Si no se envía, se asigna automáticamente según el tamaño de la lista.

**Ejemplo en Postman:**
- Método: `POST`
- URL: `http://localhost:8082/reservas`
- Headers:
  ```
  Content-Type: application/json
  ```
- Body (raw → JSON):
```json
{
  "canchaId": 1,
  "usuario": "Carlos Gómez",
  "fecha": "2026-03-25",
  "hora": "09:00"
}
```

**Respuesta (200 OK):**
```json
{
  "id": 1,
  "canchaId": 1,
  "usuario": "Carlos Gómez",
  "fecha": "2026-03-25",
  "hora": "09:00"
}
```

---

### `GET /reservas/cancha/{canchaId}` — Reservas por cancha

**Descripción:** Filtra y retorna todas las reservas asociadas a una cancha específica mediante su ID.

**Ejemplo en Postman:**
- Método: `GET`
- URL: `http://localhost:8082/reservas/cancha/1`

**Respuesta (200 OK):**
```json
[
  {
    "id": 1,
    "canchaId": 1,
    "usuario": "Carlos Gómez",
    "fecha": "2026-03-25",
    "hora": "09:00"
  }
]
```

> Si la cancha no tiene reservas, retorna una lista vacía `[]`.

---

## 🧪 Flujo de prueba recomendado en Postman

Sigue este orden para probar el sistema completo:

1. **Agregar canchas** → `POST http://localhost:8081/canchas` (crea 2 o 3 canchas)
2. **Verificar canchas** → `GET http://localhost:8081/canchas`
3. **Crear reservas** → `POST http://localhost:8082/reservas` (usando los IDs de las canchas creadas)
4. **Listar todas las reservas** → `GET http://localhost:8082/reservas`
5. **Filtrar por cancha** → `GET http://localhost:8082/reservas/cancha/1`

---

## 📝 Notas

- Los datos se almacenan **en memoria**: se pierden al reiniciar el servidor.
- El microservicio `AgregarCanchas` usa `AtomicLong` para generar IDs de forma segura.
- El microservicio `ReservarCanchas` asigna el ID automáticamente solo si no viene en el body del request.
- Ambos servicios deben estar corriendo al mismo tiempo para el flujo completo.
