# 🎬 Cinema Management System

A full-stack cinema management platform built with **Spring Boot** + **React**, featuring JWT authentication, Stripe payment integration, real-time seat reservation, and an admin panel for managing movies, rooms, and showtimes.

> 🚀 **Live Demo:** [your-url-here]

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Database Schema](#database-schema)
- [API Endpoints](#api-endpoints)
- [Design Decisions](#design-decisions)
- [Getting Started](#getting-started)

---

## ✨ Features

- 🔐 JWT-based authentication with role separation (users vs internal admins)
- 🎟️ Real-time seat reservation with automatic pending cleanup
- 💳 Stripe payment integration via abstraction layer
- 🎞️ Movie catalog with poster, synopsis, genre, and age classification
- 🏟️ Room and seat management with typed pricing (seat types)
- 📅 Showtime scheduling with live availability status
- 🧹 Automatic cleanup of abandoned reservations (scheduled task)

---

## 🛠️ Tech Stack

**Backend**
- Java 17 + Spring Boot 3
- Spring Security + JWT
- Spring Data JPA / Hibernate
- PostgreSQL
- Stripe API
- JUnit 5 + Mockito

**Frontend**
- React

---

## 🏛️ Architecture

```
┌─────────────────────────────────────────┐
│               React Frontend            │
└──────────────────┬──────────────────────┘
                   │ HTTP / REST
┌──────────────────▼──────────────────────┐
│            Spring Boot API              │
│                                         │
│  Controllers → Contracts (interfaces)   │
│       ↓                                 │
│  Services → Repositories (JPA)          │
│       ↓                                 │
│  ReservationStateManager                │
│  StripePaymentAdapter (PaymentGateway)  │
│  ReservationCleanupTask (Scheduler)     │
└──────────────────┬──────────────────────┘
                   │
┌──────────────────▼──────────────────────┐
│              PostgreSQL                  │
└─────────────────────────────────────────┘
```

---

## 🗄️ Database Schema

The schema evolved through 7 migrations:

```
┌──────────────┐       ┌──────────────┐       ┌─────────────────┐
│    movie     │       │     room     │       │    seatType     │
│──────────────│       │──────────────│       │─────────────────│
│ id (PK)      │       │ id (PK)      │       │ id (PK)         │
│ title        │       │ name         │       │ name            │
│ synopsis     │       │ capacity     │       │ price           │
│ genre        │       └──────┬───────┘       └────────┬────────┘
│ classification│              │                        │
│ poster_url   │              │                        │
│ time         │       ┌──────▼───────┐                │
└──────┬───────┘       │     seat     │◄───────────────┘
       │               │──────────────│
       │               │ id (PK)      │
       │               │ room_id (FK) │
       │               │ seat_row     │
       │               │ seat_column  │
       │               │ type_id (FK) │
       │               └──────┬───────┘
       │                      │
┌──────▼───────────────────┐  │
│        showtime          │  │
│──────────────────────────│  │
│ id (PK)                  │  │
│ movie_id (FK)            │  │
│ room_id (FK)             │  │
│ date_time                │  │
└──────┬───────────────────┘  │
       │                      │
┌──────▼───────────────────┐  │
│       reservation        │  │
│──────────────────────────│  │
│ id (PK)                  │  │
│ users_id (FK)            │  │
│ showtime_id (FK)         │  │
│ status                   │  │
│ created_at               │  │
└──────┬───────────────────┘  │
       │                      │
┌──────▼───────────────────┐  │
│     reservation_item     │  │
│──────────────────────────│  │
│ id (PK)                  │  │
│ reservation_id (FK)      │  │
│ seat_id (FK) ────────────┼──┘
│ showtime_id (FK)         │
│ UNIQUE(seat_id,showtime) │
└──────────────────────────┘

┌──────────────┐     ┌──────────────┐
│    users     │     │   internal   │
│──────────────│     │──────────────│
│ id (PK)      │     │ id (PK)      │
│ name         │     │ login        │
│ email        │     │ password     │
└──────────────┘     │ role         │
                     └──────────────┘
```

**Key migration highlights:**
- **V4** — Introduced `reservation_item` to support multi-seat reservations in a single booking
- **V5** — Added `seatType` for pricing differentiation (standard, VIP, etc.)
- **V6** — Enriched movie entity with `synopsis`, `genre`, `classification`, and `poster_url`
- **V7** — Separated admin authentication (`internal`) from regular `users`

---

## 📡 API Endpoints

### Auth
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/auth/login` | User login, returns JWT |
| POST | `/auth/register` | User registration |
| POST | `/auth/internal/login` | Admin login |

### Movies
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/movies` | List all movies |
| GET | `/movies/{id}` | Get movie details |
| POST | `/movies` | Create movie (admin) |
| PUT | `/movies/{id}` | Update movie (admin) |
| DELETE | `/movies/{id}` | Delete movie (admin) |

### Showtimes
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/showtimes` | List showtimes with status |
| GET | `/showtimes/{id}/seats` | Get seat availability |
| POST | `/showtimes` | Create showtime (admin) |

### Reservations
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/reservations` | Create reservation (pending) |
| POST | `/reservations/{id}/pay` | Process payment via Stripe |
| GET | `/reservations/my` | List user's reservations |
| DELETE | `/reservations/{id}` | Cancel reservation |

### Rooms & Seats
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/rooms` | List all rooms |
| POST | `/rooms` | Create room (admin) |
| POST | `/rooms/{id}/seats` | Add seats to room (admin) |

---

## 🧠 Design Decisions

### 1. Contract Interfaces (ISP / Dependency Inversion)
Controllers depend on interfaces (`MovieContract`, `ReservationContract`) rather than concrete service implementations. This decouples the web layer from business logic and makes testing straightforward — you can mock any contract without wiring the full service.

### 2. ReservationStateManager (Single Responsibility)
Payment state transitions (PENDING → APPROVED / FAILED) are handled by a dedicated `ReservationStateManager` instead of polluting `ReservationService`. The payment flow is: `PaymentService` processes the charge → calls `StateManager` to update reservation status. `ReservationService` has zero knowledge of payment outcomes.

### 3. PaymentGateway Abstraction (Open/Closed Principle)
Stripe is injected via a `PaymentGateway` interface implemented by `StripePaymentAdapter`. Swapping to another payment provider (e.g., PayPal, Mercado Pago) requires only a new adapter — zero changes to business logic.

### 4. ReservationCleanupTask (Scheduled Job)
A `@Scheduled` task runs every minute to expire PENDING reservations that were never completed. An index on `(created_at) WHERE status = 'PENDING'` (V3) ensures this query is fast even with large reservation tables. The cleanup window is intentionally generous to avoid race conditions between reservation creation and the scheduler cycle.

### 5. reservation_item (V4 redesign)
Originally, each reservation held a single seat. V4 introduced `reservation_item` to allow one reservation to span multiple seats — more realistic for groups buying tickets together. The `UNIQUE(seat_id, showtime_id)` constraint on `reservation_item` enforces that no seat can be double-booked for the same showtime at the database level.

---

## 🚀 Getting Started

### Prerequisites
- Java 17+
- PostgreSQL
- Node.js 18+ (frontend)
- Stripe account (test keys)

### Backend

```bash
# Clone the repository
git clone https://github.com/your-username/cinema-management-system

# Configure environment variables
cp src/main/resources/application.properties.example src/main/resources/application.properties
# Fill in: DB_URL, DB_USER, DB_PASSWORD, JWT_SECRET, STRIPE_SECRET_KEY

# Run
./mvnw spring-boot:run
```

### Frontend

```bash
cd frontend
npm install
npm run dev
```

### Environment Variables

| Variable | Description |
|----------|-------------|
| `DB_URL` | PostgreSQL connection URL |
| `DB_USER` | Database user |
| `DB_PASSWORD` | Database password |
| `JWT_SECRET` | Secret key for JWT signing |
| `STRIPE_SECRET_KEY` | Stripe secret key (starts with `sk_`) |

---

## 👤 Author

Made by **[your name]** — [LinkedIn](https://linkedin.com/in/your-profile) · [GitHub](https://github.com/your-username)
