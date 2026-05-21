# Auction System REST API

## 📝 O projekcie

System backendowy typu REST API do obsługi internetowego systemu aukcyjnego. Projekt umożliwia:

* rejestrację użytkowników,
* wystawianie aukcji,
* składanie ofert (bidów),
* przeglądanie i filtrowanie aukcji,
* zarządzanie użytkownikami i aukcjami.

Projekt został wykonany w ramach przedmiotu **„Tworzenie usług sieciowych REST”**.

---

## 🚀 Technologie

* Java 21
* Spring Boot 4.0.6
* Spring Web
* Spring Data JPA
* Hibernate
* H2 Database
* MapStruct
* Lombok
* Swagger / OpenAPI
* testy jednostkowe Mockito,
* Maven

---

## ✨ Najważniejsze funkcjonalności

* architektura warstwowa (`Controller → Service → Repository`)
* DTO request/response
* MapStruct
* paginacja i sortowanie (`Pageable`)
* filtrowanie danych
* walidacja danych wejściowych
* globalna obsługa wyjątków
* scheduler automatycznie zamykający aukcje
* Swagger/OpenAPI

---

## 🛠️ Przykładowe endpointy

### Użytkownicy

```http
POST /api/v0.1/users/register
GET /api/v0.1/users/{username}
PUT /api/v0.1/users/{username}/email
DELETE /api/v0.1/users/{username}
```

### Aukcje

```http
POST /api/v0.1/auctions/user/{username}
GET /api/v0.1/auctions
GET /api/v0.1/auctions/{referenceNumber}
PUT /api/v0.1/auctions/{referenceNumber}/title
```

### Bidy

```http
POST /api/v0.1/bids/auction/{referenceNumber}?username=Kinga77
GET /api/v0.1/bids/{bidIdNumber}
GET /api/v0.1/bids?bidderUsername=Kinga77
```

---

## 📁 Struktura projektu

```text
controller/
dto/
exception/
mapper/
model/
repository/
scheduler/
service/
```

---

## 📚 Swagger

```text
http://localhost:8080/swagger-ui/index.html
```

---

## 🗄️ H2 Database

```text
http://localhost:8080/h2-console
```

JDBC URL:

```text
jdbc:h2:mem:auctionSystem
```

---

## ▶️ Uruchomienie projektu

```bash
git clone https://github.com/kacperciepienka/auction-system.git
```

```bash
cd auction-system
```

```bash
mvn spring-boot:run
```

Aplikacja uruchamia się na:

```text
http://localhost:8080
```

---

## 🔧 Status projektu

Aktualnie projekt posiada:

* REST API,
* Swagger/OpenAPI,
* H2 Database,
* DTO,
* paginację,
* filtrowanie,
* sortowanie,
* obsługę wyjątków,
* logikę biznesową systemu aukcyjnego.
* testy jednostkowe Mockito,

Planowane rozszerzenia:
* Spring Security + JWT,
* Docker.
