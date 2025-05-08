package com.satc.recomendador_filmes_api.controller;

import com.satc.recomendador_filmes_api.model.Movie;
import com.satc.recomendador_filmes_api.model.UserPreferences;
import com.satc.recomendador_filmes_api.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/recommendations")
    public ResponseEntity<List<Movie>> getRecommendations() {
        List<Movie> recommendations = movieService.getRecommendations();
        return ResponseEntity.ok(recommendations);
    }

    @GetMapping("/popular")
    public ResponseEntity<List<Movie>> getPopularMovies() {
        List<Movie> popularMovies = movieService.getPopularMovies();
        return ResponseEntity.ok(popularMovies);
    }

    @PostMapping("/preferences")
    public ResponseEntity<String> setUserPreferences(@RequestBody UserPreferences preferences) {
        movieService.saveUserPreferences(preferences);
        return ResponseEntity.ok("Preferences saved successfully");
    }

    @GetMapping("/sobre")
    public ResponseEntity<Map<String, Object>> getProjectInfo() {
        return ResponseEntity.ok(Map.of(
                "integrantes", List.of("Davi Carlos"),
                "nome_projeto", "Recomendador de Filmes API",
                "versao", "1.0",
                "descricao", "API para recomendação de filmes baseada nas preferências do usuário"
        ));
    }
}