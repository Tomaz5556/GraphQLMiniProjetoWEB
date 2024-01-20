package com.web.graphql;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Game {

    private String id;
    private String name;
    private String developerId;

    private static List<Game> games = new ArrayList<>(Arrays.asList(
            new Game("game-1", "GTA", "developer-1"),
            new Game("game-2", "Far Cry", "developer-2"),
            new Game("game-3", "The Witcher", "developer-3")));

    public Game(String id, String name, String developerId) {
        this.id = id;
        this.name = name;
        this.developerId = developerId;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDeveloperId() {
        return developerId;
    }

    public static Game getById(String id) {
        return games.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static Game getByName(String name) {
        return games.stream()
                .filter(game -> game.getName().equals(name))
                .findFirst()
                .orElse(null);
    }

    public static List<Game> getAllGames() {
        return games;
    }

    public static Game addGame(String id, String name, String developerId) {
        Game newGame = new Game(id, name, developerId);
        games.add(newGame);
        return newGame;
    }

    public static Game updateGame(String id, String name, String developerId) {
        Game game = getById(id);
        if (game != null) {
            game.name = name;
            game.developerId = developerId;
        }
        return game;
    }

    public static Game deleteGame(String id) {
        Game game = getById(id);
        if (game != null) {
            games.remove(game);
        }
        return game;
    }
}