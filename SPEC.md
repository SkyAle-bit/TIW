# Helpdesk — Specifiche di progetto

## Stack
- Backend: Spring Boot 3, Spring Security (JWT), Spring Data JPA, PostgreSQL
- Frontend: Angular 17 standalone, Angular Material, HttpClient

## Ruoli
- USER: utente normale che apre ticket
- OPERATOR: operatore che gestisce i ticket

---

## Livello 1

### Autenticazione
- POST /api/auth/register → registra un nuovo utente (ruolo USER di default)
- POST /api/auth/login → restituisce un JWT

### Funzionalità utente (ruolo USER)
- Aprire un ticket con: titolo, descrizione, categoria
- Vedere la lista dei propri ticket
- Vedere il dettaglio di un ticket
- Aggiungere un commento a un proprio ticket
- Vedere lo stato del ticket

### Funzionalità operatore (ruolo OPERATOR)
- Vedere tutti i ticket
- Rispondere a un ticket (aggiungere commento)
- Cambiare lo stato di un ticket: OPEN, IN_PROGRESS, RESOLVED, CLOSED

---

## Livello 2

### Priorità
- Il ticket ha una priorità: LOW, MEDIUM, HIGH, URGENT
- L'operatore può modificare la priorità

### Assegnazione automatica
- Quando un ticket viene aperto, viene assegnato automaticamente
  all'operatore con meno ticket aperti (status = OPEN o IN_PROGRESS)

### Storico stati
- Ogni cambio di stato viene registrato con: stato precedente,
  stato nuovo, operatore che ha effettuato il cambio, timestamp

### Filtri
- Lista ticket filtrabile per: stato, priorità, categoria, operatore assegnato

### Dashboard operatore
- Conteggio ticket per stato: aperti, in lavorazione, risolti

### Valutazione
- Quando un ticket è RESOLVED, l'utente può indicare
  se il problema è stato risolto (SATISFIED / NOT_SATISFIED)

---

## Categorie ticket (esempi)
- TECHNICAL, BILLING, GENERAL, OTHER

## Stati ticket
- OPEN, IN_PROGRESS, RESOLVED, CLOSED

## Priorità ticket
- LOW, MEDIUM, HIGH, URGENT