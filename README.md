# Modified Blackjack

## 1) What it is
This is a Spring Boot + Thymeleaf Blackjack web app with game rules handled on the server.  
The active implementation is under `src/main`, and the older Swing version is kept in `legacy/`.

## 2) Demo
Working on deploying.  
There is no public live URL yet; next step is deploying the Dockerized app to a hosted service.  
For now, run it locally and open `http://localhost:10000`.

## 3) Features
- Server-side Blackjack engine with a shuffled 52-card deck.
- Hand scoring with Ace as 1 or 11.
- Player actions: `Hit`, `Stand`, `Split`, and `New Game/Play Again`.
- Split support for matching ranks or any two 10-value cards.
- Multi-hand flow after split with active-hand tracking.
- Dealer hole card is hidden until player turns are done.
- Dealer draws until hand value is at least 17.
- Round outcomes tracked per hand: `WIN`, `LOSE`, `BUST`, `PUSH`, `BLACKJACK`.
- Session scoreboard tracked using HttpSession and a tabId to separate concurrent games.
- House rule: player wins automatically with 5 cards without busting (Five-card Charlie).
- Health endpoint at `/health`.

## 4) Why I built this
I built this to move a classic Blackjack project into a web app and strengthen full-stack Java skills.  
The focus was clean separation between domain logic and controller/UI layers.  
It also gave me practice managing per-tab session state and running the app via a multi-stage Dockerfile.

## 5) What I learned
- How to model game rules in plain Java domain classes (`GameState`, `Hand`, `Deck`) and keep controllers thin.
- How to manage independent game sessions per browser tab using `HttpSession` + `tabId`.
- How to sequence split-hand gameplay and settle each hand result correctly.
- How to render dynamic game state with Thymeleaf without a frontend framework.
- How to package and run the app with Maven Wrapper and a multi-stage Dockerfile.
- Current gap: there are no automated tests in `src/test`; next step is adding JUnit tests for game logic and controller endpoints.

## 6) Tech stack
- Java 17: core language for game logic and backend code.
- Spring Boot 3 (Web): HTTP routing, session handling, and app bootstrapping.
- Thymeleaf: server-rendered UI bound to backend model state.
- Maven Wrapper (`mvnw`): consistent build/run workflow across environments.
- CSS + static card image assets: custom responsive game interface.
- Docker: reproducible build and runtime environment.

## 7) How to run locally
```bash
cd Modified_Blackjack
./mvnw spring-boot:run
```

Open `http://localhost:10000`

Windows (Command Prompt):
```bat
cd Modified_Blackjack
mvnw.cmd spring-boot:run
```

## 8) Project structure
```text
Modified_Blackjack/
├── src/
│   └── main/
│       ├── java/com/caubeezic/blackjack/
│       │   ├── BlackjackApplication.java
│       │   ├── domain/
│       │   │   ├── Card.java
│       │   │   ├── Deck.java
│       │   │   ├── GameState.java
│       │   │   ├── GameOutcome.java
│       │   │   ├── Hand.java
│       │   │   └── Suit.java
│       │   └── web/
│       │       ├── BlackjackController.java
│       │       └── SessionStats.java
│       └── resources/
│           ├── application.properties
│           ├── templates/index.html
│           └── static/
│               ├── styles.css
│               └── imagesCard/
├── legacy/blackjack/         # older Swing implementation
├── Dockerfile
├── pom.xml
└── mvnw
```
