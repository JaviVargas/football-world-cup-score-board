# :rocket: Football World Cup Score Board library 

## :star: Usage:

Get an instance:
```java
 ScoreBoard scoreBoard = ScoreBoard.getInstance();
```
----------
## :trident: Available methods:

Create a new game with the given teams names
```java
public Game startGame(String homeTeam, String awayTeam)
```
Updates the score for a game matching the team names
```java
public void updateScore(String homeTeam, String awayTeam, int homeScore, int awayScore)
```
Remove the game matching the given home and away teams
```java
public void finishGame(String homeTeam, String awayTeam)
```
Get a summary of games by total score. Those games with the same total score will be returned ordered by the most recently added to our system.
```java
public List<Game> getSummary()
```
----------

## :muscle: Powered by
![Java](https://raw.githubusercontent.com/JaviVargas/resources/main/javalogo.png?raw=true)


![Maven](https://raw.githubusercontent.com/JaviVargas/resources/main/maven.png?raw=true)


![Mockito](https://raw.githubusercontent.com/JaviVargas/resources/main/mockito.jpg?raw=true)


![jUnit 5](https://raw.githubusercontent.com/JaviVargas/resources/main/java_junit_junit.png?raw=true)

- Java 11
- Maven
- Mockito
- jUnit 5