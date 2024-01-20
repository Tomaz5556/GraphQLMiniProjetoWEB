package com.web.graphql;

import java.util.Arrays;
import java.util.List;

public record Developer (String id, String name) {

    private static List<Developer> developers = Arrays.asList(
            new Developer("developer-1", "Rockstar"),
            new Developer("developer-2", "Ubisoft"),
            new Developer("developer-3", "CD Projekt")
    );

    public static Developer getById(String id) {
        return developers.stream()
				.filter(developer -> developer.id().equals(id))
				.findFirst()
				.orElse(null);
    }
}