package com.satc.recomendador_filmes_api.controller;

import com.satc.recomendador_filmes_api.model.Movie;
import com.satc.recomendador_filmes_api.model.UserPreferences;
import com.satc.recomendador_filmes_api.service.MovieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostMapping("/preferences")
    public ResponseEntity<String> savePreferences(@RequestBody UserPreferences preferences) {
        movieService.savePreferences(preferences);
        return ResponseEntity.ok("Preferências salvas com sucesso");
    }

    @GetMapping("/preferences")
    public ResponseEntity<List<UserPreferences>> getAllPreferences() {
        return ResponseEntity.ok(movieService.getAllPreferences());
    }

    @PostMapping("/recommendations")
    public ResponseEntity<List<Movie>> getRecommendations(@RequestBody UserPreferences preferences) {
        if (preferences.getYear() == null) preferences.setYear(2023);
        if (preferences.getMinRating() == null) preferences.setMinRating(5.0);

        List<Movie> movies = movieService.getRecommendedMovies(preferences);
        return ResponseEntity.ok(movies);
    }

    @GetMapping("/sobre")
    public ResponseEntity<Map<String, Object>> sobre() {
        Map<String, Object> response = new HashMap<>();
        response.put("integrantes", List.of("Davi Carlos"));
        response.put("nome_projeto", "Recomendador de Filmes");
        response.put("versao", "1.0");
        return ResponseEntity.ok(response);
    }
}