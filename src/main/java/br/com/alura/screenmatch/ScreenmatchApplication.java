package br.com.alura.screenmatch;

import br.com.alura.screenmatch.models.SerieData;
import br.com.alura.screenmatch.service.ConsumoApi;
import br.com.alura.screenmatch.service.ConvertData;
import java.io.FileInputStream;
import java.util.Properties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScreenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScreenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ConsumoApi consumoApi = new ConsumoApi();

		Properties properties = new Properties();
		properties.load(new FileInputStream("config.properties"));
		String apiKey = properties.getProperty("omdbKey");
		String endereco = "https://omdbapi.com/?apikey=" + apiKey + "&t=Gilmore+Girls";

		String json = consumoApi.obterDados(endereco);

		ConvertData convertData = new ConvertData();
		SerieData serieData = convertData.getData(json, SerieData.class);

		System.out.println(serieData);
	}
}
