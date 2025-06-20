package com.dutra.movieAPI;

import com.dutra.movieAPI.model.Movie;
import com.dutra.movieAPI.service.MovieService;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import jakarta.annotation.PostConstruct;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TesteOutseraApplication {

    public static void main(String[] args) {
        SpringApplication.run(TesteOutseraApplication.class, args);
    }

    private final MovieService movieService;

    public TesteOutseraApplication(MovieService movieService) {
        this.movieService = movieService;
    }

    @PostConstruct
    public void init() throws Exception {
        CSVParser parser = new CSVParserBuilder()
                .withSeparator(';')
                .withQuoteChar('"')
                .build();

        try (CSVReader reader = new CSVReaderBuilder(
                new InputStreamReader(
                        Objects.requireNonNull(getClass().getResourceAsStream("/movielist.csv"))
                ))
                .withCSVParser(parser)
                .build()
        ) {
            List<String[]> listMovies = reader.readAll();
            listMovies.removeFirst();

            for (String[] row : listMovies) {
                Movie movie = new Movie();
                movie.setYear(Integer.valueOf(row[0]));
                movie.setTitle(row[1]);
                movie.setStudios(row[2]);
                movie.setProducers(row[3]);

                if (row.length == 5) {
                    movie.setWinner(row[4].equals("yes"));
                }

                movieService.save(movie);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
