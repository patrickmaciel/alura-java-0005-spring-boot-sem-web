package br.com.alura.screenmatch.renderer;

import br.com.alura.screenmatch.models.EpisodeData;
import br.com.alura.screenmatch.models.SeasonData;
import br.com.alura.screenmatch.models.SerieData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class ConsoleMenuFor {
  public Scanner scanner;
  public ConsumoApi consumoApi;
  public ConvertData convertData;
  public Properties properties;
  public String apiKey;
  public final String API_ADDRESS = "https://omdbapi.com/";

  public ConsoleMenuFor() throws IOException {
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
      for (int e = 0; e < seasonData.episodeDataList().size(); e++) {
        EpisodeData episode = seasonData.episodeDataList().get(e);
        System.out.println("- Episode " + episode.number() + ":");
        System.out.println(episode);
      }

      System.out.println("");
      System.out.println("..........................");
      System.out.println("");
    }
  }
}
