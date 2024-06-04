package br.com.alura.screenmatch.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SeasonData(
    @JsonAlias("Season") Integer number,
    @JsonAlias("Episodes") List<EpisodeData> episodeDataList
    ) {
}
