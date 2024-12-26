package com.literaluraChallenge.Literalura;

import com.literaluraChallenge.Literalura.DTO.LibroDTO;
import com.literaluraChallenge.Literalura.Request.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraApplication implements CommandLineRunner {
	private final Request request;

	@Autowired
	public LiteraluraApplication(Request request) {
		this.request = request;
	}

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		start();
	}

	public void start() {
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
						System.out.println("Introduce el título del libro o parte del título:");
						System.out.println("(Solo se mostrarán libros que no estén bajo protección de derechos de autor).");
						String words = scanner.nextLine();
						System.out.println("Buscando libro...");
						List<String> queryWords = List.of(words.split(" "));

						try {
							LibroDTO libro = request.fetchFirstResult(queryWords);
							System.out.println("******************************");
							System.out.println("Libro encontrado:");
							System.out.println("ID: " + libro.id());
							System.out.println("Título: " + libro.titulo());
							System.out.println("Autor: " + libro.autor());
							System.out.println("Idiomas: " + libro.idioma());
							System.out.println("Número de Descargas: " + libro.numeroDescargas());
							System.out.println("******************************");
						} catch (IOException e) {
							System.out.println(e.getMessage());
						} catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                        while (true) {
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
							break;
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
