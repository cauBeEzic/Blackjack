# Blackjack (Spring Boot)

A simple Blackjack web app built with Spring Boot (Java 17) and Thymeleaf. The legacy Swing UI has been moved to `legacy/` and is no longer used.

## Rules
- Standard Blackjack: get as close to 21 as possible without going over.
- Card values: 2–10 are face value, J/Q/K are worth 10, Ace is 1 or 11.
- Player can Hit or Stand.
- Dealer hits until total >= 17 (stands on all 17).
- Busting over 21 loses the round.
- Optional house rule included: if the player holds 5 cards without busting, the player wins.
- Splitting is supported when the first two cards are the same rank (re-splitting allowed).

## Project layout
- `src/main/java/com/caubeezic/blackjack/domain` – game logic
- `src/main/java/com/caubeezic/blackjack/web` – web controller
- `src/main/resources/templates` – Thymeleaf UI
- `src/main/resources/static/imagesCard` – card images
- `legacy/` – old Swing UI (unused)

## Run locally

```bash
./mvnw spring-boot:run
```

To force a port (Render-style):

```bash
PORT=10000 ./mvnw spring-boot:run
```

Then open: `http://localhost:10000`

## Build a jar

```bash
./mvnw -DskipTests package
java -jar target/blackjack-0.0.1-SNAPSHOT.jar
```

## Docker

```bash
docker build -t blackjack .
docker run -p 10000:10000 -e PORT=10000 blackjack
```

Open: `http://localhost:10000`

Health check: `http://localhost:10000/health`

## Render deployment (Docker)
1. Create a new **Web Service** in Render.
2. Connect this repo.
3. Choose **Docker** as the runtime.
4. Set the port to `10000` (Render uses `$PORT` internally).
5. Deploy.

Live Demo: (add link here)
