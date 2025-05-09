package com.satc.recomendador_filmes_api.service;

import com.satc.recomendador_filmes_api.model.Movie;
import com.satc.recomendador_filmes_api.model.UserPreferences;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MovieService {
    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final List<UserPreferences> preferences = new ArrayList<>();
    private final RestTemplate restTemplate = new RestTemplate();

    public List<Movie> getRecommendedMovies(UserPreferences preferences) {
        String url = String.format("%s/discover/movie?api_key=%s&with_genres=%s&primary_release_year=%d&vote_average.gte=%.1f&language=pt-BR",
                apiUrl, apiKey, preferences.getGenre(), preferences.getYear(), preferences.getMinRating());

        try {
            TMDBResponse response = restTemplate.getForObject(url, TMDBResponse.class);
            if (response != null && response.getResults() != null) {
                return response.getResults().stream()
                        .peek(movie -> {
                            if (movie.getPosterPath() != null) {
                                movie.setPosterPath("https://image.tmdb.org/t/p/w500" + movie.getPosterPath());
                            }
                        })
                        .collect(Collectors.toList());
            }
            return Collections.emptyList();
        } catch (Exception e) {
            System.err.println("Erro ao acessar TMDB: " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public void savePreferences(UserPreferences userPreferences) {
        preferences.add(userPreferences);
    }

    public List<UserPreferences> getAllPreferences() {
        return new ArrayList<>(preferences);
    }

    private static class TMDBResponse {
        private List<Movie> results;
        public List<Movie> getResults() { return results; }
        public void setResults(List<Movie> results) { this.results = results; }
    }
}