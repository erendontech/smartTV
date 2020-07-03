package com.stvc.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Set;

/**
 * @author Edgard Rendón
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Movie {

    private Integer id;

    private String title;

    private Integer year;

    private Set<Genre> genres;

    private String description;

    private Audio audio;

    private Quality quality;

    private Double imdbRate;

    private Director director;

    private Set<Cast> casts;

    private boolean free;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Set<Genre> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genre> genres) {
        this.genres = genres;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Audio getAudio() {
        return audio;
    }

    public void setAudio(Audio audio) {
        this.audio = audio;
    }

    public Quality getQuality() {
        return quality;
    }

    public void setQuality(Quality quality) {
        this.quality = quality;
    }

    public Double getImdbRate() {
        return imdbRate;
    }

    public void setImdbRate(Double imdbRate) {
        this.imdbRate = imdbRate;
    }

    public Director getDirector() {
        return director;
    }

    public void setDirector(Director director) {
        this.director = director;
    }

    public Set<Cast> getCasts() {
        return casts;
    }

    public void setCasts(Set<Cast> casts) {
        this.casts = casts;
    }

    public boolean isFree() {
        return free;
    }

    public void setFree(boolean free) {
        this.free = free;
    }
}
