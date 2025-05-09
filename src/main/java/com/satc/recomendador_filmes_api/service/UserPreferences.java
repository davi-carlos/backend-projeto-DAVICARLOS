package com.satc.recomendador_filmes_api.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserPreferences {
    private String genre;
    private Integer year;
    private Double minRating;
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public Integer getYear() { return year; }
    public void setYear(Integer year) { this.year = year; }
    public Double getMinRating() { return minRating; }
    public void setMinRating(Double minRating) { this.minRating = minRating; }
}