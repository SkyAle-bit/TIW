# Helpdesk TIW

Sistema di ticketing / helpdesk realizzato per l'esame di **Tecnologie Informatiche per il Web**.

## Stack tecnologico

- **Backend**: Spring Boot 3, Spring Security (JWT), Spring Data JPA, PostgreSQL
- **Frontend**: Angular 17 (standalone components), Angular Material

## Struttura del progetto

```
helpdesk/
├── backend/    # Spring Boot REST API
├── frontend/   # Angular SPA
├── SPEC.md     # Specifiche funzionali
└── SCHEMA.md   # Schema del database
```

---

## Livelli implementati

- [x] Livello 1 — Apertura e gestione ticket base
- [x] Livello 2 — Priorità, assegnazione automatica e filtri

---

## Funzionalità implementate

### Livello 1

**Utente (ruolo USER)**
- Registrazione e login
- Apertura di un ticket con titolo, descrizione e categoria
- Visualizzazione dei propri ticket
- Visualizzazione del dettaglio di un ticket
- Aggiunta di commenti a un proprio ticket
- Visualizzazione dello stato del ticket

**Operatore (ruolo OPERATOR)**
- Visualizzazione di tutti i ticket
- Risposta ai ticket tramite commenti
- Modifica dello stato del ticket: `OPEN`, `IN_PROGRESS`, `RESOLVED`, `CLOSED`

### Livello 2

- **Priorità**: ogni ticket ha una priorità (`LOW`, `MEDIUM`, `HIGH`, `URGENT`) modificabile dall'operatore
- **Assegnazione automatica**: alla creazione, il ticket viene assegnato all'operatore con meno ticket aperti
- **Storico stati**: ogni cambio di stato viene registrato con timestamp e operatore responsabile
- **Filtri**: la lista ticket è filtrabile per stato, priorità, categoria e operatore assegnato
- **Dashboard operatore**: conteggio ticket per stato (aperti, in lavorazione, risolti)
- **Valutazione**: l'utente può indicare se il problema è stato risolto quando il ticket è in stato `RESOLVED`

---

## Credenziali di test

| Ruolo    | Username | Password    |
|----------|----------|-------------|
| Operator | operator | operator123 |
| User     | user     | user123     |

---

## Prerequisiti

- Java 17+
- Node.js 18+
- PostgreSQL 15+

---

## Avvio del progetto

### 1. Configura il database

Crea un database PostgreSQL locale:

```sql
CREATE DATABASE helpdesk_db;
```

### 2. Configura il backend

Modifica il file `backend/src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/helpdesk_db
spring.datasource.username=TUO_USERNAME
spring.datasource.password=TUA_PASSWORD
```

### 3. Avvia il backend

```bash
cd backend
./mvnw spring-boot:run
```

Il backend sarà disponibile su `http://localhost:8080`.

### 4. Avvia il frontend

```bash
cd frontend
npm install
ng serve
```

Il frontend sarà disponibile su `http://localhost:4200`.

---

## Principali endpoint REST

| Metodo | Endpoint | Ruolo | Descrizione |
|--------|----------|-------|-------------|
| POST | `/api/auth/register` | — | Registrazione utente |
| POST | `/api/auth/login` | — | Login, restituisce JWT |
| GET | `/api/tickets` | USER | Ticket dell'utente autenticato |
| POST | `/api/tickets` | USER | Apertura nuovo ticket |
| GET | `/api/tickets/{id}` | USER / OPERATOR | Dettaglio ticket |
| POST | `/api/tickets/{id}/comments` | USER / OPERATOR | Aggiunta commento |
| GET | `/api/operator/tickets` | OPERATOR | Tutti i ticket con filtri |
| PATCH | `/api/operator/tickets/{id}/status` | OPERATOR | Cambio stato |
| PATCH | `/api/operator/tickets/{id}/priority` | OPERATOR | Cambio priorità |
| GET | `/api/operator/dashboard` | OPERATOR | Conteggi per stato |
| PATCH | `/api/tickets/{id}/rating` | USER | Valutazione risposta |