package hr.fer.oprpp1.hw04.db;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;

/**
 * Class used to read lines from a file, generate student database and then run the program.
 * @author Zvonimir Petar Rezo
 *
 */
public class StudentDB {

	public static void main(String[] args) {
		List<String> lines = null;
		try {
			lines = Files.readAllLines(
						 Paths.get("C:\\Users\\Zvone\\Desktop\\Faks\\database.txt"),
						 StandardCharsets.UTF_8
						);
		} catch (IOException e) {
			e.printStackTrace();
		}
		StudentDatabase database = new StudentDatabase(lines);
		Scanner sc = new Scanner(System.in);
		System.out.println("Starting...");
		System.out.print("> ");
		while (sc.hasNextLine()) {
			String line = sc.nextLine();
			if (line.equals("exit")) {
				System.out.println("Goodbye!");
				break;
			}
			
			database.query(line);
			System.out.print("> ");
		}
		sc.close();

	}

}
