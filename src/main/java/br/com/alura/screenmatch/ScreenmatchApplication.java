package br.com.alura.screenmatch;

import br.com.alura.screenmatch.renderer.ConsoleMenu;
import br.com.alura.screenmatch.renderer.ConsoleMenuFor;
import br.com.alura.screenmatch.renderer.ConsoleMenuForeach;
import br.com.alura.screenmatch.renderer.ConsoleMenuLambda;
import br.com.alura.screenmatch.renderer.ConsoleMenuStreams;
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
//		Study Case (using everything, a total mess)
//		ConsoleMenu consoleMenu = new ConsoleMenu();
//		consoleMenu.show();

//		Using For
//		ConsoleMenuFor consoleMenuFor = new ConsoleMenuFor();
//		consoleMenuFor.show();

//		Using Foreach
//		ConsoleMenuForeach consoleMenuForeach = new ConsoleMenuForeach();
//		consoleMenuForeach.show();

//		Using Lambda
//		ConsoleMenuLambda consoleMenuLambda = new ConsoleMenuLambda();
//		consoleMenuLambda.show();

//		Using Streams api
		ConsoleMenuStreams consoleMenuStreams = new ConsoleMenuStreams();
		consoleMenuStreams.show();
	}
}
