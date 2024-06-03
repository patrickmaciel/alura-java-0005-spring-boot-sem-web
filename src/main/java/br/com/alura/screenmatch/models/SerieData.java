package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;

public record SerieData(@JsonAlias({"Title", "Titulo", "Nome"}) String title,
                        @JsonAlias("totalSeasons") Integer totalSeasons,
                        @JsonAlias("imdbRating") String rating,
                        @JsonProperty("imdbVotes") String votes) {

}
