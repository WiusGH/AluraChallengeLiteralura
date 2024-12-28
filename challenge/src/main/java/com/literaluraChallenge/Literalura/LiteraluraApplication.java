package com.literaluraChallenge.Literalura;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.literaluraChallenge.Literalura.DTO.AutorDTO;
import com.literaluraChallenge.Literalura.DTO.LibroDTO;
import com.literaluraChallenge.Literalura.Request.Request;
import com.literaluraChallenge.Literalura.Service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	private final Request request;
	private final LibroService libroService;
	Options options = new Options();

	@Autowired
	public LiteraluraApplication(Request request, LibroService libroService) {
		this.request = request;
		this.libroService = libroService;
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
					firstOption:
					while (true) {
						String response = options.optionOne();
						ObjectMapper objectMapper = new ObjectMapper();
						String json = "{\"author\":\"Melville, Herman\",\"death_year\":\"1891\",\"language\":\"en\",\"title\":\"Moby Dick; Or, The Whale\",\"download_count\":72786,\"birth_year\":\"1819\"}";

						// Deserialize the JSON string into DTO objects
						LibroDTO libroDTO = objectMapper.readValue(json, LibroDTO.class);
						AutorDTO autorDTO = objectMapper.readValue(json, AutorDTO.class);
						libroService.saveLibro(libroDTO);

//						System.out.println(libroDTO);
//						System.out.println(autorDTO);
//						Utilizar response para guardar entradas a la base de datos
//						System.out.println(response);
                        System.out.println("""
                                ¿Deseas buscar otro libro? Elige una opción:\s
                                
                                1- Buscar otro libro.\s
                                2- Volver al menu principal.\s
                                0- Salir.""");
                        option = scanner.nextLine().toLowerCase();
                        switch (option) {
                            case "1": {
                                break;
                            }
                            case "2": {
                                break firstOption;
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
				break;
				}
				case "2": {
					System.out.println("Opción 2 en construcción.");
					break;
				}
				case "3": {
					System.out.println("Opción 3 en construcción.");
					break;
				}
				case "4": {
					System.out.println("Opción 4 en construcción.");
					break;
				}
				case "5": {
					System.out.println("Opción 5 en construcción.");
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
