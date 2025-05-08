package com.satc.recomendador_filmes_api.service;

import com.satc.recomendador_filmes_api.model.Movie;
import com.satc.recomendador_filmes_api.model.UserPreferences;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Service
public class MovieService {

    @Value("${tmdb.api.key}")
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;
    private final List<UserPreferences> userPreferencesList = new CopyOnWriteArrayList<>();

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Movie> getPopularMovies() {
        String url = apiUrl + "/movie/popular?api_key=" + apiKey + "&language=pt-BR";
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);

        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<Movie> movies = new ArrayList<>();

        for (Map<String, Object> result : results) {
            Movie movie = new Movie();
            movie.setId((Integer) result.get("id"));
            movie.setTitle((String) result.get("title"));
            movie.setOverview((String) result.get("overview"));
            movie.setPosterPath((String) result.get("poster_path"));
            movie.setVoteAverage((Double) result.get("vote_average"));
            movies.add(movie);
        }

        return movies;
    }

    public List<Movie> getRecommendations() {
        if (userPreferencesList.isEmpty()) {
            return getPopularMovies();
        }

        // Simples recomendação baseada no último usuário que salvou preferências
        UserPreferences lastPrefs = userPreferencesList.get(userPreferencesList.size() - 1);
        String url = apiUrl + "/discover/movie?api_key=" + apiKey +
                "&language=pt-BR&sort_by=popularity.desc" +
                "&with_genres=" + String.join(",", lastPrefs.getFavoriteGenres()) +
                "&vote_average.gte=" + (lastPrefs.getMinRating() - 1);

        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) response.get("results");
        List<Movie> movies = new ArrayList<>();

        for (Map<String, Object> result : results) {
            Movie movie = new Movie();
            movie.setId((Integer) result.get("id"));
            movie.setTitle((String) result.get("title"));
            movie.setOverview((String) result.get("overview"));
            movie.setPosterPath((String) result.get("poster_path"));
            movie.setVoteAverage((Double) result.get("vote_average"));
            movies.add(movie);
        }

        return movies;
    }

    public void saveUserPreferences(UserPreferences preferences) {
        userPreferencesList.add(preferences);
    }

    public List<UserPreferences> getAllPreferences() {
        return new ArrayList<>(userPreferencesList);
    }
}