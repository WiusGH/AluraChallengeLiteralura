package com.literaluraChallenge.Literalura;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.literaluraChallenge.Literalura.Service.AutorService;
import com.literaluraChallenge.Literalura.Service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {

	private final Options options;

	@Autowired
	public LiteraluraApplication(Options options) {
		this.options = options;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		start();
	}

	public void start() throws JsonProcessingException {
		Scanner scanner = new Scanner(System.in);
		String option;
		String welcomeText = """
				******************************
				Hola, bienvenido al buscador de libros de Literalura.\s
				\s
				Elige la opción a través de un número;\s
				1- Buscar libro por título\s
				2- Lista libros registrados\s
				3- Listar autores registrados\s
				4- Listar autores vivos en determinado año\s
				5- Listar libros por idioma\s
				0- Salir\s
				******************************
				""";

		mainLoop:
		while (true) {
			System.out.println(welcomeText);
			option = scanner.nextLine();
			switch (option) {
				case "1": {
					options.optionOne();
				}
				case "2": {
					options.optionTwo();
					break;
				}
				case "3": {
					options.optionThree();
					break;
				}
				case "4": {
					options.optionFour();
					break;
				}
				case "5": {
					options.optionFive();
					break;
				}
				case "0":
				case "salir": {
					break mainLoop;
				}
				default: {
					System.out.println("Opción inválida.");
				}
			}

		}
	}
}
