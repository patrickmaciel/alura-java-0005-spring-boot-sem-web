package br.com.alura.screenmatch.renderer;

import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SerieData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleMenu {
  public Scanner scanner;
  public ConsumoApi consumoApi;
  public ConvertData convertData;
  public Properties properties;
  public String apiKey;
  public final String API_ADDRESS = "https://omdbapi.com/";

  public ConsoleMenu() throws IOException {
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
      System.out.println("## Season " + i + ":");
      System.out.println(seasonData);
      System.out.println("");

      seasonDataList.add(seasonData);

      System.out.println("### Listing Episodes...");
      for (EpisodeData episode : seasonData.episodeDataList()) {
        System.out.println("- Episode " + episode.number() + ":");
        System.out.println(episode);
      }

      // Another alternative to the above for loop:
      seasonDataList.forEach(System.out::println);

      // Another alternative to the above for loop:
      for (i = 0; i < serieData.totalSeasons(); i++) {
        if (seasonDataList == null || seasonDataList.size() <= i) {
          System.out.println("Season data is missing for season index: " + i);
          continue;
        }

        SeasonData seasonDataItem = seasonDataList.get(i);
        if (seasonDataItem == null || seasonDataItem.episodeDataList() == null) {
          System.out.println("Episode data is missing for season index: " + i);
          continue;
        }

        List<EpisodeData> episodeDataList = seasonDataItem.episodeDataList();

        // Old school for loop
        // for (int j = 0; j < episodeDataList.size(); j++) {

        // Enhanced for loop
        for (EpisodeData episodeData : episodeDataList) {
          System.out.println("- Episode " + episodeData.title());
        }
      }

      System.out.println("------------------");
      System.out.println("");
    }

    // Another alternative to the above for loop, using Lambda
    seasonDataList.forEach(t -> t.episodeDataList().forEach(e -> System.out.println(e.title())));
  }
}
