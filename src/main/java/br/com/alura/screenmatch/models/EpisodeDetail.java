package br.com.alura.screenmatch.models;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class EpisodeDetail {
  private Integer seasonNumber;
  private String title;
  private Integer number;
  private Double rating;
  private LocalDate releaseDate;

  public EpisodeDetail(Integer seasonNumber, EpisodeData episodeData) {
    this.seasonNumber = seasonNumber;
    this.title = episodeData.title();
    this.number = episodeData.number();

    try {
      this.rating = Double.parseDouble(episodeData.rating());
    } catch (NumberFormatException e) {
      this.rating = 0.0;
    }

    try {
      this.releaseDate = LocalDate.parse(episodeData.releaseDate());
    } catch (DateTimeParseException e) {
      this.releaseDate = null;
    }
  }

  public Integer getSeasonNumber() {
    return seasonNumber;
  }

  public void setSeasonNumber(Integer seasonNumber) {
    this.seasonNumber = seasonNumber;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public Double getRating() {
    return rating;
  }

  public void setRating(Double rating) {
    this.rating = rating;
  }

  public LocalDate getReleaseDate() {
    return releaseDate;
  }

  public void setReleaseDate(LocalDate releaseDate) {
    this.releaseDate = releaseDate;
  }

  @Override
  public String toString() {
    return "SeasonNumber=" + seasonNumber +
        ", title='" + title + '\'' +
        ", number=" + number +
        ", rating=" + rating +
        ", releaseDate=" + releaseDate;
  }
}
