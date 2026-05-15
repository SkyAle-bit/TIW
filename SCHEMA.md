# Schema del database

## Tabella: users
| Campo      | Tipo         | Note                        |
|------------|--------------|-----------------------------|
| id         | BIGINT PK    | auto increment              |
| username   | VARCHAR(50)  | unique, not null            |
| email      | VARCHAR(100) | unique, not null            |
| password   | VARCHAR(255) | bcrypt, not null            |
| role       | VARCHAR(20)  | USER o OPERATOR, not null   |
| created_at | TIMESTAMP    | default now()               |

## Tabella: tickets
| Campo       | Tipo        | Note                              |
|-------------|-------------|-----------------------------------|
| id          | BIGINT PK   | auto increment                    |
| title       | VARCHAR(100)| not null                          |
| description | TEXT        | not null                          |
| category    | VARCHAR(50) | TECHNICAL, BILLING, GENERAL, OTHER|
| priority    | VARCHAR(20) | LOW, MEDIUM, HIGH, URGENT         |
| status      | VARCHAR(20) | OPEN, IN_PROGRESS, RESOLVED, CLOSED|
| rating      | VARCHAR(20) | SATISFIED, NOT_SATISFIED, null    |
| created_by  | FK → users  | not null                          |
| assigned_to | FK → users  | operatore assegnato, nullable     |
| created_at  | TIMESTAMP   | default now()                     |
| updated_at  | TIMESTAMP   | aggiornato ad ogni modifica       |

## Tabella: comments
| Campo      | Tipo      | Note                      |
|------------|-----------|---------------------------|
| id         | BIGINT PK | auto increment            |
| ticket_id  | FK → tickets | not null               |
| author_id  | FK → users   | not null               |
| text       | TEXT      | not null                  |
| created_at | TIMESTAMP | default now()             |

## Tabella: status_history
| Campo      | Tipo      | Note                          |
|------------|-----------|-------------------------------|
| id         | BIGINT PK | auto increment                |
| ticket_id  | FK → tickets | not null                   |
| old_status | VARCHAR(20)  | nullable (primo stato)     |
| new_status | VARCHAR(20)  | not null                   |
| changed_by | FK → users   | operatore, not null        |
| changed_at | TIMESTAMP    | default now()              |