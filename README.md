# 🎬 Cinema Management System

Complete cinema management system developed in Java with Spring Boot, including ticket booking features, session management, and administration.

## 📋 About The Project

This project implements a complete cinema infrastructure, allowing management of theaters, seats, movies, and sessions. Users can view the schedule and make ticket reservations in a secure and efficient manner.

## 🚀 Technologies Used

- **Java** - Programming language
- **Spring Boot** - Application development framework
- **PostgreSQL** - Relational database
- **Flyway** - Database version control and migration

## 📁 Project Structure

The system includes the following main entities:

- **Theaters** - Cinema theater management
- **Seats** - Seat control per theater
- **Movies** - Available movie catalog
- **Sessions** - Screening schedule
- **Bookings** - Ticket reservation system
- **Users** - Customer registration and authentication
- **Managers** - Administrative profile

## 🗺️ Execution Plan

### Phase 1: Structure and Initial Data
- Database structure creation
- Flyway configuration
- Populate database with initial data

### Phase 2: Read Functionalities
- **2.1** - List available movies
- **2.2** - List sessions
- **2.3** - View seats per session

### Phase 3: Basic Booking System
- **3.1** - Create ticket booking
- **3.2** - Implement security to prevent duplicate bookings
  - Validation for already booked seats
  - Theater capacity control
- **3.3** - Error and exception handling

### Phase 4: Booking System Refinement
- **4.1** - Implement booking status (reserved/confirmed)
- **4.2** - Booking cancellation functionality
- **4.3** - Timeout system for pending bookings

### Phase 5: User Management
- **5.1** - Create user registration
- **5.2** - Link bookings to user
- **5.3** - Display user booking history
- **5.4** - List active (non-expired) tickets for user

### Phase 6: Security and Authentication
- Implement Spring Security
- User authentication and authorization system

### Phase 7: Administrative Panel (Manager)
- **7.1** - Session management
  - **7.1.1** - Create new session
  - **7.1.2** - Edit existing session
- **7.2** - Add movies to catalog
- **7.3** - Remove movies from catalog

## 🎯 Main Features

### For Users
- View movies in theaters
- Check available sessions
- Verify seat availability
- Make ticket reservations
- Manage their bookings (view, cancel)
- Access purchase history

### For Managers
- Manage movie catalog
- Create and edit sessions
- Administer theaters and seats
- View statistics and reports

## 🔒 Security

The system implements:
- User authentication
- Role-based access control (user/manager)
- Booking validation to prevent duplicates
- Overbooking protection

## 📦 How to Run

### Prerequisites
- Java 17 or higher
- PostgreSQL installed and configured
- Maven

### Execution Steps

1. Clone the repository
```bash
git clone [repository-url]
```

2. Configure the database in `application.properties`
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/cinema
spring.datasource.username=your_username
spring.datasource.password=your_password
```

3. Run Flyway migrations
```bash
mvn flyway:migrate
```

4. Run the application
```bash
mvn spring-boot:run
```

## 📝 Data Model

The system uses the following main tables:
- `theaters` - Cinema theater information
- `seats` - Available seats per theater
- `movies` - Movie catalog
- `sessions` - Screening schedule
- `bookings` - Booking records
- `users` - System user registration


## 👥 Authors

ADILSON MAGALHAES JAGER

---

⭐ Developed with Java and Spring Boot
