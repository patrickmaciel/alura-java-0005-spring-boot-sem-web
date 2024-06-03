package br.com.alura.screenmatch;

import br.com.alura.screenmatch.service.ConsumoApi;
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
		String endereco = "https://omdbapi.com/?apikey=" + apiKey + "&t=Matrix";

		System.out.println(consumoApi.obterDados(endereco));
	}
}
