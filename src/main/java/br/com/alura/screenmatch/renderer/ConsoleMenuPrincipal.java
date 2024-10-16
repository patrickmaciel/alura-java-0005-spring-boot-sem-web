package br.com.alura.screenmatch.renderer;

import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.EpisodeDetail;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SerieData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleMenuPrincipal {
  public Scanner scanner;
  public ConsumoApi consumoApi;
  public ConvertData convertData;
  public Properties properties;
  public String apiKey;
  public final String API_ADDRESS = "https://omdbapi.com/";

  public ConsoleMenuPrincipal() throws IOException {
    this.scanner = new Scanner(System.in);
    this.consumoApi = new ConsumoApi();
    this.convertData = new ConvertData();
    this.properties = new Properties();
    properties.load(new FileInputStream("config.properties"));
    this.apiKey = properties.getProperty("omdbKey");
  }

  public void show() {
    System.out.println("What Serie do you want to search?");
    String serie = scanner.nextLine();

    System.out.println("Searching...");
    String endereco = API_ADDRESS + "?apikey=" + apiKey + "&t=" + serie.replace(" ", "+");
    String json = consumoApi.obterDados(endereco);

    SerieData serieData = convertData.getData(json, SerieData.class);
    System.out.println("##########################");
    System.out.println("Serie: " + serieData.title());
    System.out.println(serieData);
    System.out.println("");

    System.out.println("Listing Seasons...");
    ArrayList<SeasonData> seasonDataList = new ArrayList<>();
    if (serieData.totalSeasons() != null) {
      for (int i = 1; i <= serieData.totalSeasons(); i++) {
        endereco =
            API_ADDRESS + "?apikey=" + apiKey + "&t=" + serie.replace(" ", "+") + "&season=" + i;
        json = consumoApi.obterDados(endereco);
        SeasonData seasonData = convertData.getData(json, SeasonData.class);
        seasonDataList.add(seasonData);
      }

      List<EpisodeDetail> episodes = seasonDataList.stream()
          .flatMap(t -> t.episodeDataList().stream())
          .map(d -> new EpisodeDetail(d.number(), d))
          .collect(Collectors.toList());

      // the block below was commented to test the Map's features
      //    episodes.forEach(System.out::println);
      //
      //    System.out.println("");
      //    System.out.println("What the name of the episode you want to search?");
      //    String searchEpisode = scanner.nextLine();
      //
      //    Optional<EpisodeDetail> firstEpisodeFound = episodes.stream()
      //        .filter(e -> e.getTitle().toUpperCase().contains(searchEpisode.toUpperCase()))
      //        .findFirst();
      //
      //    if (firstEpisodeFound.isPresent()) {
      //      firstEpisodeFound.ifPresent(
      //          episodeDetail -> System.out.println("Episode found: " + episodeDetail));
      //    } else {
      //      System.out.println("Episode not found");
      //    }

      Map<Integer, Double> ratingPerSeason = episodes.stream()
          .filter(e -> e.getRating() > 0.0)
          .collect(Collectors.groupingBy(EpisodeDetail::getSeasonNumber,
              Collectors.averagingDouble(EpisodeDetail::getRating)));

      System.out.println(ratingPerSeason);
    }
  }
}
