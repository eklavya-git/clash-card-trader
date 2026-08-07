# Clash Card Trader

A lightweight companion application for the **Clash of Clans Card Trading Event** that helps clan members discover who can trade the cards they need.

Instead of repeatedly asking in clan chat, players can publish the list of cards they currently have available for trade and instantly discover other players who can help.

---

## Motivation

The in-game trading experience requires players to manually coordinate with each other.

Typical conversations look like:

> "Does anyone have a Wizard?"

> "I need a Super Dragon."

> "Who needs my Electro Dragon?"

As the number of available cards increases, manually finding the right trade partner becomes increasingly difficult.

**Clash Card Trader** aims to solve this problem by acting as a lightweight marketplace for card availability.

Players publish the cards they have available for trade, and the application helps identify suitable trading partners.

---

## Phase 1 Scope

The initial version focuses on providing a searchable marketplace.

Players can:

- Register themselves
- Verify or update their current clan
- Publish their current tradeable cards
- Search for players who own cards they need
- Discover potential trade partners

Trading itself continues inside the Clash of Clans application.

---

## Future Roadmap

Planned enhancements include:

- Smart trade recommendations
- Automatic trade matching
- Cross-clan recommendations
- Recently active players
- Inventory freshness indicators
- Player authentication
- Frontend UI
- Deployment to cloud

---

# Technology Stack

| Component | Technology |
|----------|------------|
| Language | Java 25 (LTS) |
| Framework | Spring Boot 4.1 |
| Build Tool | Gradle |
| Database | PostgreSQL 17 |
| Database Migration | Flyway |
| ORM | Spring Data JPA / Hibernate |
| Security | Spring Security |
| Containerization | Docker |

---

# Project Structure

```
src
├── main
│   ├── java
│   │   └── com.altius.clashcardtrader
│   │       ├── config
│   │       ├── controller
│   │       ├── domain
│   │       ├── dto
│   │       ├── entity
│   │       ├── repository
│   │       └── service
│   │
│   └── resources
│       └── db
│           └── migration
```

---

# Database

The application currently contains four primary tables:

- players
- clans
- cards
- player_tradeable_cards

Reference data for all supported cards is seeded automatically using Flyway.

---

# API

## Get all supported cards

```
GET /v1/api/cards
```

Example response

```json
[
  {
    "id": "9dba93...",
    "name": "Barbarian"
  },
  {
    "id": "52f5d4...",
    "name": "Archer"
  }
]
```

---

# Running Locally

## Start PostgreSQL

```bash
docker compose up -d
```

---

## Run the application

```bash
./gradlew bootRun
```

---

Application will start on

```
http://localhost:8080
```

Cards endpoint

```
GET http://localhost:8080/v1/api/cards
```

---

# Development Workflow

Feature development follows a lightweight Git workflow.

```
master
    │
    ├── feature/addInitialDBSchema
    ├── feature/cards-api
    ├── feature/player-login
    └── ...
```

Guidelines:

- Small feature branches
- Small atomic commits
- Flyway migrations are immutable once merged
- Every business rule should be covered by unit tests

---

# Design Principles

This project intentionally follows a domain-driven design approach.

Some notable decisions include:

- `ClashTag` implemented as a Value Object
- Immutable reference data for cards
- Published marketplace snapshot instead of inventory history
- UUID identifiers across all entities
- Flyway-managed database schema
- REST-first backend

---

# Current Status

- ✅ Initial project setup
- ✅ Dockerized PostgreSQL
- ✅ Flyway migrations
- ✅ Domain model
- ✅ Database schema
- ✅ Master card data
- ✅ Cards API

---

## Useful PostgreSQL Commands

docker compose up -d

docker compose down

docker exec -it clash-card-postgres psql -U postgres -d clash_card_trader

\dt

SELECT * FROM players;

SELECT * FROM cards;

## Clash Tags

All API endpoints that accept a Clash tag as a path parameter expect the tag **without the leading `#`**.

Example:

GET /v1/api/clans/ABC123

Request bodies may contain tags with or without the leading `#`. The server normalizes all tags internally.

Responses always return the canonical representation including the leading `#`.

# License

This project is intended for learning and experimentation and is not affiliated with or endorsed by Supercell.