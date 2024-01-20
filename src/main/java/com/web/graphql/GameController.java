package com.web.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GameController {
    @QueryMapping
    public Game gameById(@Argument String id) {
        Game game = Game.getById(id);
        return game;
    }

    @QueryMapping
    public Game gameByName(@Argument String name) {
        return Game.getByName(name);
    }

    @QueryMapping
    public List<Game> allGames() {
        return Game.getAllGames();
    }

    @MutationMapping
    public Game addGame(@Argument String id, @Argument String name, @Argument String developerId) {
        return Game.addGame(id, name, developerId);
    }

    @MutationMapping
    public Game updateGame(@Argument String id, @Argument String name, @Argument String developerId) {
        return Game.updateGame(id, name, developerId);
    }

    @MutationMapping
    public Game deleteGame(@Argument String id) {
        return Game.deleteGame(id);
    }

    @SchemaMapping
    public Developer developer(Game game) {
        return Developer.getById(game.getDeveloperId());
    }
}