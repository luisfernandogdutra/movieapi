package com.dutra.movieAPI.service;

import com.dutra.movieAPI.dto.IntervalDTO;
import com.dutra.movieAPI.dto.MinMaxDTO;
import com.dutra.movieAPI.model.Movie;
import com.dutra.movieAPI.repository.MovieRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class MovieService {

    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id) {
        return movieRepository.findById(id);
    }

    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }

    public void delete(Long id) {
        movieRepository.deleteById(id);
    }

    private Map<String, List<Integer>> getAllProducersWinners() {
        List<Movie> listWinners = movieRepository.findByWinnerTrue();
        Map<String, List<Integer>> mapWinnerYears = new HashMap<>();
        for (Movie winner : listWinners) {
            String[] producers = winner.getProducers().split(", | and ");
            for (String producer : producers) {
                mapWinnerYears
                        .computeIfAbsent(producer.trim(), k -> new ArrayList<>())
                        .add(winner.getYear());
            }
        }

        return mapWinnerYears;
    }

    private Map<String, List<Integer>> getWinnersMoreThenOne() {
        return getAllProducersWinners().entrySet().stream()
                        .filter(winnerTime -> winnerTime.getValue().size() > 1)
                        .collect(Collectors.toMap(
                                Map.Entry::getKey,
                                Map.Entry::getValue
                        ));
    }

    private List<Map.Entry<String, Integer>> getMinValueOnList(Map<String, Integer> mapInterval) {
        int min = mapInterval.values().stream()
                .min(Integer::compareTo)
                .orElse(Integer.MAX_VALUE);

        return mapInterval.entrySet().stream()
                .filter(e -> e.getValue() == min)
                .toList();
    }

    private List<Map.Entry<String, Integer>> getMaxValueOnList(Map<String, Integer> mapInterval) {
        int max = mapInterval.values().stream()
                .max(Integer::compareTo)
                .orElse(Integer.MIN_VALUE);

        return mapInterval.entrySet().stream()
                .filter(e -> e.getValue() == max)
                .toList();
    }

    public IntervalDTO getIntervals() {
        Map<String, List<Integer>> mapWinnerYears = getWinnersMoreThenOne();
        Map<String, Integer> mapInterval = new HashMap<>();
        for (Map.Entry<String, List<Integer>> entry : mapWinnerYears.entrySet()) {
            Integer interval = entry.getValue().getLast() - entry.getValue().getFirst();
            mapInterval.put(entry.getKey(), interval);
        }

        List<Map.Entry<String, Integer>> min = getMinValueOnList(mapInterval);
        List<Map.Entry<String, Integer>> max = getMaxValueOnList(mapInterval);
        return new IntervalDTO(
                loadMinMaxDTO(min, mapWinnerYears),
                loadMinMaxDTO(max, mapWinnerYears)
        );
    }

    private List<MinMaxDTO> loadMinMaxDTO(
            Collection<Map.Entry<String, Integer>> entries,
            Map<String, List<Integer>> mapWinnerYears
    ) {
        List<MinMaxDTO> listMinMaxDTO = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : entries) {
            List<Integer> years = mapWinnerYears.get(entry.getKey());
            if (years != null && !years.isEmpty()) {
                listMinMaxDTO.add(
                        new MinMaxDTO(
                            entry.getKey(),
                            entry.getValue(),
                            years.getFirst(),
                            years.getLast()
                        )
                );
            }
        }
        return listMinMaxDTO;
    }
}
