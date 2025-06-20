package com.dutra.movieAPI.controller;

import com.dutra.movieAPI.dto.IntervalDTO;
import com.dutra.movieAPI.model.Movie;
import com.dutra.movieAPI.service.MovieService;
import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/movies")
public class MovieController {

    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @GetMapping
    public List<Movie> getAll() {
        return movieService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Movie> getById(@PathVariable Long id) {
        return movieService.findById(id);
    }

    @GetMapping("/listMinAndMax")
    public IntervalDTO getListMinAndMax() {
        return movieService.getIntervals();
    }

    @PostMapping
    public Movie save(@RequestBody Movie movie) {
        return movieService.save(movie);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        movieService.delete(id);
    }

    @PutMapping
    public Movie update(@RequestBody Movie movie) {
        return movieService.save(movie);
    }
}
