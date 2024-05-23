# ♕ BYU CS 240 Chess

This project demonstrates mastery of proper software design, client/server architecture, networking using HTTP and WebSocket, database persistence, unit testing, serialization, and security.

## 10k Architecture Overview

The application implements a multiplayer chess server and a command line chess client.

[![Sequence Diagram](10k-architecture.png)](https://sequencediagram.org/index.html#initialData=C4S2BsFMAIGEAtIGckCh0AcCGAnUBjEbAO2DnBElIEZVs8RCSzYKrgAmO3AorU6AGVIOAG4jUAEyzAsAIyxIYAERnzFkdKgrFIuaKlaUa0ALQA+ISPE4AXNABWAexDFoAcywBbTcLEizS1VZBSVbbVc9HGgnADNYiN19QzZSDkCrfztHFzdPH1Q-Gwzg9TDEqJj4iuSjdmoMopF7LywAaxgvJ3FC6wCLaFLQyHCdSriEseSm6NMBurT7AFcMaWAYOSdcSRTjTka+7NaO6C6emZK1YdHI-Qma6N6ss3nU4Gpl1ZkNrZwdhfeByy9hwyBA7mIT2KAyGGhuSWi9wuc0sAI49nyMG6ElQQA)

## Modules

The application has three modules.

- **Client**: The command line program used to play a game of chess over the network.
- **Server**: The command line program that listens for network requests from the client and manages users and games.
- **Shared**: Code that is used by both the client and the server. This includes the rules of chess and tracking the state of a game.

## Starter Code

As you create your chess application you will move through specific phases of development. This starts with implementing the moves of chess and finishes with sending game moves over the network between your client and server. You will start each phase by copying course provided [starter-code](starter-code/) for that phase into the source code of the project. Do not copy a phases' starter code before you are ready to begin work on that phase.

## IntelliJ Support

Open the project directory in IntelliJ in order to develop, run, and debug your code using an IDE.

## Maven Support

You can use the following commands to build, test, package, and run your code.

| Command                    | Description                                     |
| -------------------------- | ----------------------------------------------- |
| `mvn compile`              | Builds the code                                 |
| `mvn package`              | Run the tests and build an Uber jar file        |
| `mvn package -DskipTests`  | Build an Uber jar file                          |
| `mvn install`              | Installs the packages into the local repository |
| `mvn test`                 | Run all the tests                               |
| `mvn -pl shared test`      | Run all the shared tests                        |
| `mvn -pl client exec:java` | Build and run the client `Main`                 |
| `mvn -pl server exec:java` | Build and run the server `Main`                 |

These commands are configured by the `pom.xml` (Project Object Model) files. There is a POM file in the root of the project, and one in each of the modules. The root POM defines any global dependencies and references the module POM files.

## Running the program using Java

Once you have compiled your project into an uber jar, you can execute it with the following command.

```sh
java -jar client/target/client-jar-with-dependencies.jar

♕ 240 Chess Client: chess.ChessPiece@7852e922
```
## Sequence Diagram URL PHASE 2:
Presentation Mode:
https://sequencediagram.org/index.html?presentationMode=readOnly#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgztPL1GzjOUAJIAOW54UFwtG1v0ryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJcYNdLfnJuVVk8R03W2gj1N9hPKFvsuZygAmJxObr7vOjK8nqZnseXmBjxvdAOFMLxfH8AJoHYckYB5CBoiSAI0gyLJkHMNkjl3ao6iaVoDHUBI0HfAMUCDBYlgOLCoAKDdLg-Gsv0A68vhtJZvxvOiaPyQdSgQBCkDQWF4MQ1F0VibFBwKJMWVKckqRpUjCyZZMCndbk+QFas7nFSVqwnUQAB5pxk0k1Q1AyVGk3tk1KNMMwbXNGILHsi1U-J3W9X1-QPQNOwbc82yjGMR0slBjNyUyUz-C9MC4iLeJEwSVzXW8oAirjSgYu4OK+GL0Fyg4HwwYzqNKF8316AZfLGZifz6fKAKAvoDhAsDvD8QIvBQNsRN8ZhkPSTJMGK5glR3ecKmkXkuXqLlmhaAjVCI7pGrS2jzmBLKGoC-9CrizbKAKXj+PsfrhIQ-qxIxSTlRQay3Nk+TM2zQKhMa5SXVUNSTEHCKorCh6VJZVzgfUH7Sg0-lBUa3SIClUK-pMmzZPMzV4unRLLqzFKECwaiMsOso0ZG9CSsx4pLgq99Ws4dqIMCSEODg6EYAAcXzVlBtQ0mcjGgdKcmtnZoW+x81W3bYuojagXonbXr2urHimZBYhqMUOdGVQWoO2XuJO6F1c1tRYUa2YxdGHloWuiTAfyKK5IpF7RwvU3JfQT7iwhqGtNhiV4f87skcilGzPVTUpPt0OU2ejVjdUWFPfc9SZq0i2UCt2I4aldONGDh308z9ciYSu7SiLo3Odx-HBcJvXttz8ZKn6dOPWkJuAEYnwAZgAFieFDMjNZzcqeHQEFAZth5ypWvnTr183+GBGiKsmwFKwXytfd8+kb8oKhb-M287nv+6mQfTRq0epnHyfp8PWennnxfVj2ZfadAzwOsg7AfCgbBuDwGNJkdm+YUhDTQnzTCm9Ki1AaKLcWwR3bEV6M-UYVFBYyznNtRq19d75gXkxZqAIibHTLvZTIxs3YKwvLMChKBjY2yxHbAuTtkHUJdh7UGX1vapxhsg7OgdUhhX+tHUoJNI6sLAF6FAyQqFoFkcbJOLIIZeUrIKBRciwHBRgLnERyNHpmUUfmXWc5S7aiHHAYBKAZFaNGNXNKddsGH1GMfcoXc+6rz5uYkoW9KouJQG4mAHje4f3pp1AIlgUAaggMkGAAApCAglQGigCLfEAzZeYYXGr42BlI8ItHThLGh6B3wAOAFEqAcAID8SgObI+0gMETSwVtfouDH5zAnpU6ptTvwAHUWAejmi0AAQjyBQcAADSc8GlXhCSQvWZCLGlAAFZJLQFQs25Tuk1OgPU1x0hboWKBl9R2YBnZvQ4W9ZR4MPIch9vwkpSR-YIwbPokOhiUwSLuic4sZyqGt2kNaLplAenQBud9O5kM+G6IaYIvRUVeGaUFNs0FuzYAvJgJkEUYKoDvIdmswSxtTHAh8eCQlGz8wOIJtOTKJNRo+KptvXoYSv4MwCF4Cp8BuB4E7NgABhB4iJHATzUa0CJr72mnyOaC1jDrXyJlKaM0ZUtDlVxJZk4VQcGiRSFACgNSMkTiwsRscEDDwThCpF0NtK1hgGxd4YZfiCJuNwr2UKpWzS5Davy9q7QwAdAgZ1MpJEmqdnHTmRrEVQoebCy2rNMUIujhDD1c1Y0Z3jXpPRIbPlnP1QgC5-4LWuuTvcmFfs9KhSje6FNXry0B0RndURObpA6syIyEllAyUqhbdwNtJpqW11pUTcR4csnk1yGVamLKzB0yAA

Default:
https://sequencediagram.org/index.html#initialData=IYYwLg9gTgBAwgGwJYFMB2YBQAHYUxIhK4YwDKKUAbpTngUSWDABLBoAmCtu+hx7ZhWqEUdPo0EwAIsDDAAgiBAoAzqswc5wAEbBVKGBx2ZM6MFACeq3ETQBzGAAYAdAE5M9qBACu2GADEaMBUljAASij2SKoWckgQaIEA7gAWSGBiiKikALQAfOSUNFAAXDAA2gAKAPJkACoAujAA9D4GUAA6aADeAETtlMEAtih9pX0wfQA0U7jqydAc45MzUyjDwEgIK1MAvpjCJTAFrOxclOX9g1AjYxNTs33zqotQyw9rfRtbO58HbE43FgpyOonKUCiMUyUAAFJForFKJEAI4+NRgACUh2KohOhVk8iUKnU5XsKDAAFUOrCbndsYTFMo1Kp8UYdOUyABRAAyXLg9RgdOAoxgADNvMMhR1MIziSyTqDcSpymgfAgEDiRCo2XLmaSYCBIXIUNTKLSOndZi83hxZj9tgztPL1GzjOUAJIAOW54UFwtG1v0ryW9s22xg3vqNWltDBOtOepJqnKRpQJoUPjAqQtQxFKCdRP1rNO7sjPq5ftjt3zs2AWdS9QgAGt0OXozB69nZc7i4rCvGUOUu42W+gtVQ8blToCLmUIlCkVBIqp1VhZ8D+0VqJcYNdLfnJuVVk8R03W2gj1N9hPKFvsuZygAmJxObr7vOjK8nqZnseXmBjxvdAOFMLxfH8AJoHYckYB5CBoiSAI0gyLJkHMNkjl3ao6iaVoDHUBI0HfAMUCDBYlgOLCoAKDdLg-Gsv0A68vhtJZvxvOiaPyQdSgQBCkDQWF4MQ1F0VibFBwKJMWVKckqRpUjCyZZMCndbk+QFas7nFSVqwnUQAB5pxk0k1Q1AyVGk3tk1KNMMwbXNGILHsi1U-J3W9X1-QPQNOwbc82yjGMR0slBjNyUyUz-C9MC4iLeJEwSVzXW8oAirjSgYu4OK+GL0Fyg4HwwYzqNKF8316AZfLGZifz6fKAKAvoDhAsDvD8QIvBQNsRN8ZhkPSTJMGK5glR3ecKmkXkuXqLlmhaAjVCI7pGrS2jzmBLKGoC-9CrizbKAKXj+PsfrhIQ-qxIxSTlRQay3Nk+TM2zQKhMa5SXVUNSTEHCKorCh6VJZVzgfUH7Sg0-lBUa3SIClUK-pMmzZPMzV4unRLLqzFKECwaiMsOso0ZG9CSsx4pLgq99Ws4dqIMCSEODg6EYAAcXzVlBtQ0mcjGgdKcmtnZoW+x81W3bYuojagXonbXr2urHimZBYhqMUOdGVQWoO2XuJO6F1c1tRYUa2YxdGHloWuiTAfyKK5IpF7RwvU3JfQT7iwhqGtNhiV4f87skcilGzPVTUpPt0OU2ejVjdUWFPfc9SZq0i2UCt2I4aldONGDh308z9ciYSu7SiLo3Odx-HBcJvXttz8ZKn6dOPWkJuAEYnwAZgAFieFDMjNZzcqeHQEFAZth5ypWvnTr183+GBGiKsmwFKwXytfd8+kb8oKhb-M287nv+6mQfTRq0epnHyfp8PWennnxfVj2ZfadAzwOsg7AfCgbBuDwGNJkdm+YUhDTQnzTCm9Ki1AaKLcWwR3bEV6M-UYVFBYyznNtRq19d75gXkxZqAIibHTLvZTIxs3YKwvLMChKBjY2yxHbAuTtkHUJdh7UGX1vapxhsg7OgdUhhX+tHUoJNI6sLAF6FAyQqFoFkcbJOLIIZeUrIKBRciwHBRgLnERyNHpmUUfmXWc5S7aiHHAYBKAZFaNGNXNKddsGH1GMfcoXc+6rz5uYkoW9KouJQG4mAHje4f3pp1AIlgUAaggMkGAAApCAglQGigCLfEAzZeYYXGr42BlI8ItHThLGh6B3wAOAFEqAcAID8SgObI+0gMETSwVtfouDH5zAnpU6ptTvwAHUWAejmi0AAQjyBQcAADSc8GlXhCSQvWZCLGlAAFZJLQFQs25Tuk1OgPU1x0hboWKBl9R2YBnZvQ4W9ZR4MPIch9vwkpSR-YIwbPokOhiUwSLuic4sZyqGt2kNaLplAenQBud9O5kM+G6IaYIvRUVeGaUFNs0FuzYAvJgJkEUYKoDvIdmswSxtTHAh8eCQlGz8wOIJtOTKJNRo+KptvXoYSv4MwCF4Cp8BuB4E7NgABhB4iJHATzUa0CJr72mnyOaC1jDrXyJlKaM0ZUtDlVxJZk4VQcGiRSFACgNSMkTiwsRscEDDwThCpF0NtK1hgGxd4YZfiCJuNwr2UKpWzS5Davy9q7QwAdAgZ1MpJEmqdnHTmRrEVQoebCy2rNMUIujhDD1c1Y0Z3jXpPRIbPlnP1QgC5-4LWuuTvcmFfs9KhSje6FNXry0B0RndURObpA6syIyEllAyUqhbdwNtJpqW11pUTcR4csnk1yGVamLKzB0yAA