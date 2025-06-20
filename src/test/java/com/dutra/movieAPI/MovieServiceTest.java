package com.dutra.movieAPI;

import com.dutra.movieAPI.dto.IntervalDTO;
import com.dutra.movieAPI.dto.MinMaxDTO;
import com.dutra.movieAPI.model.Movie;
import com.dutra.movieAPI.repository.MovieRepository;
import com.dutra.movieAPI.service.MovieService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Transactional
public class MovieServiceTest {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieService movieService;

    @BeforeEach
    void setUp() {
        movieRepository.deleteAll();

        movieRepository.save(new Movie(1980, "Can't Stop the Music", "Can't Stop the Music", "Allan Carr", true));
        movieRepository.save(new Movie(1984, "Where the Boys Are '84", "Allan Carr", "Allan Carr", false));
        movieRepository.save(new Movie(1984, "Bolero", "Cannon Films", "Bo Derek", true));
        movieRepository.save(new Movie(1990, "Ghosts Can't Do It", "Triumph Releasing", "Bo Derek", true));
        movieRepository.save(new Movie(2002, "Swept Away", "Screen Gems", "Matthew Vaughn", true));
        movieRepository.save(new Movie(2015, "Fantastic Four", "20th Century Fox", "Simon Kinberg, Matthew Vaughn, Hutch Parker, Robert Kulzer and Gregory Goodman", true));
    }

    @Test
    void returnIntervalCorrect() {
        IntervalDTO intervalDTO = movieService.getIntervals();

        assertEquals(1, intervalDTO.getMin().size());
        MinMaxDTO min = intervalDTO.getMin().getFirst();
        assertEquals("Bo Derek", min.getProducer());
        assertEquals(6, min.getInterval());
        assertEquals(1984, min.getPreviousWin());
        assertEquals(1990, min.getFollowingWin());

        assertEquals(1, intervalDTO.getMax().size());
        MinMaxDTO max = intervalDTO.getMax().getFirst();
        assertEquals("Matthew Vaughn", max.getProducer());
        assertEquals(13, max.getInterval());
        assertEquals(2002, max.getPreviousWin());
        assertEquals(2015, max.getFollowingWin());
    }

    @Test
    void ignoreProducerWithOnlyOneWin() {
        IntervalDTO result = movieService.getIntervals();
        boolean haveProducer = result.getMin().stream().anyMatch(d -> d.getProducer().equals("Simon Kinberg"));
        assertFalse(haveProducer, "Producer don't be in the result");
    }

    @Test
    void returnProducerWithAnd() {
        IntervalDTO result = movieService.getIntervals();
        boolean haveProducer = result.getMax().stream().anyMatch(d -> d.getProducer().equals("Matthew Vaughn"));
        assertTrue(haveProducer, "Productor with and in the name");
    }

    @Test
    void returnEmptyListWhenDontHaveIntervals() {
        movieRepository.deleteAll();
        movieRepository.save(new Movie(1996, "Striptease", "Columbia Pictures", "Andrew Bergman and Mike Lobell", true));
        IntervalDTO result = movieService.getIntervals();
        assertTrue(result.getMin().isEmpty());
        assertTrue(result.getMax().isEmpty());
    }
}
