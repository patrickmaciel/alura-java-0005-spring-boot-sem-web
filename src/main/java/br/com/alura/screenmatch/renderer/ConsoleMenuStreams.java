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
import java.util.Properties;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ConsoleMenuStreams {
  public Scanner scanner;
  public ConsumoApi consumoApi;
  public ConvertData convertData;
  public Properties properties;
  public String apiKey;
  public final String API_ADDRESS = "https://omdbapi.com/";

  public ConsoleMenuStreams() throws IOException {
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
    List<SeasonData> seasonDataList = new ArrayList<>();
    for (int i = 1; i <= serieData.totalSeasons(); i++) {
      endereco = API_ADDRESS + "?apikey=" + apiKey + "&t=" + serie.replace(" ", "+") + "&season=" + i;
      json = consumoApi.obterDados(endereco);
      SeasonData seasonData = convertData.getData(json, SeasonData.class);
      seasonDataList.add(seasonData);
    }

    // Using toList(), we can't modify that list
    List<EpisodeData> episodeDataList = seasonDataList.stream()
        .flatMap(t -> t.episodeDataList().stream())
        .toList();

    // Using collect(), we can't modify that list
    List<EpisodeData> episodeDataListCollected = seasonDataList.stream()
        .flatMap(t -> t.episodeDataList().stream())
        .collect(Collectors.toList());
    episodeDataListCollected.add(new EpisodeData("Episode 40 Test", 40, "10", "2024-06-16"));

    // Listing episodes descending by rating
    episodeDataListCollected.stream()
        .filter(e -> !e.rating().equalsIgnoreCase("N/A"))
        .sorted(Comparator.comparing(EpisodeData::rating). reversed())
        .limit(5)
        .forEach(System.out::println);

    List<EpisodeDetail> episodes = seasonDataList.stream()
        .flatMap(t -> t.episodeDataList().stream())
        .map(d -> new EpisodeDetail(d.number(), d))
        .collect(Collectors.toList());

    episodes.forEach(System.out::println);

    System.out.println("From what year do you want to watch the episodes?");
    int year = scanner.nextInt();
    scanner.nextLine();

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    LocalDate searchDate = LocalDate.of(year, 1, 1);
    episodes.stream()
        .filter(e -> e.getReleaseDate() != null && e.getReleaseDate().isAfter(searchDate))
        .forEach(e -> System.out.println(
            "Season: " + e.getNumber()
                + ", Episode: "
                + e.getTitle()
                + ", Date: "
                + e.getReleaseDate().format(formatter)));
  }
}
