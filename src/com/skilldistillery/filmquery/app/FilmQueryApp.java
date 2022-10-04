package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

	boolean stillRunning = true;
	DatabaseAccessor db = new DatabaseAccessorObject();

	public static void main(String[] args) {
		FilmQueryApp app = new FilmQueryApp();
		app.launch();
	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to Klayton's Film Query App");
		while (stillRunning) {
			try {
				startUserInterface(input);
			} catch (SQLException e) {
				e.printStackTrace();
				System.err.println("Something went wrong...");
			}
		}

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		System.out.println("-------------------MENU-------------------");
		System.out.println("| 1. Look up a film by its id.           |");
		System.out.println("| 2. Look up a film by a search keyword. |");
		System.out.println("| 3. Look up an actor by id.             |");
		System.out.println("| 4. Look up an actor by name.           |");
		System.out.println("| 5. Exit the application.               |");
		System.out.println("------------------------------------------");
		String menuSelection = input.nextLine();
		switch (menuSelection) {
		case "1":
			System.out.print("Enter the id you would like to search: ");
			Film caseOneFilm = db.findFilmById(Integer.parseInt(input.nextLine()));
			if (caseOneFilm == null) {
				System.out.println("\nThat film does not exist\n");
			} else {
				System.out.println("\n" + caseOneFilm + "\n");
				System.out.println("Actors:");
				for (Actor caseOneActor : caseOneFilm.getActors()) {
					System.out.println(caseOneActor.toStringIndented());
				}
				System.out.print(
						"\nWould you like to see more details about this movie (1) or return to the main menu (2)? ");
				if (input.nextLine().equals("1")) {
					System.out.println(caseOneFilm.toStringMoreInfo());
					System.out.println("Actors:");
					for (Actor caseOneActor : caseOneFilm.getActors()) {
						System.out.println(caseOneActor.toStringIndented());
					}
				}
				System.out.println();
			}
			break;
		case "2":
			System.out.print("Enter the keyword you would like to search: ");
			List<Film> caseTwoFilmArr = db.findFilmByKeyword(input.nextLine());
			if (caseTwoFilmArr.size() == 0) {
				System.out.println("\nNo films match that keyword.\n");
			} else {
				System.out.println("\n" + caseTwoFilmArr.size() + " films matched your keyword.\n");
				for (Film caseTwoFilm : caseTwoFilmArr) {
					System.out.println(caseTwoFilm);
					System.out.println("Actors:");
					for (Actor caseTwoActor : caseTwoFilm.getActors()) {
						System.out.println(caseTwoActor.toStringIndented());
					}
					System.out.println();
				}
				System.out.print(
						"\nWould you like to see more details about this movie (1) or return to the main menu (2)? ");
				if (input.nextLine().equals("1")) {
					for (Film caseTwoInnerFilm : caseTwoFilmArr) {
						System.out.println(caseTwoInnerFilm.toStringMoreInfo());
						System.out.println("Actors:");
						for (Actor caseTwoInnerActor : caseTwoInnerFilm.getActors()) {
							System.out.println(caseTwoInnerActor.toStringIndented());
						}
					}
				}
				System.out.println();
			}
			break;
		case "3":
			System.out.print("Enter the actor's id: ");
			Actor caseThreeActor = db.findActorById(Integer.parseInt(input.nextLine()));
			input.nextLine();
			if (caseThreeActor == null) {
				System.out.println("\nThat actor does not exist\n");
			} else {
				System.out.println("\n" + caseThreeActor + "\n");
				System.out.println("Films:");
				for (Film caseThreeFilm : caseThreeActor.getFilms()) {
					System.out.println(caseThreeFilm.toStringIndented() + "\n");
				}
			}
			break;
		case "4":
			System.out.print("Enter the name you would like to search: ");
			List<Actor> caseFourActorArr = db.findActorsByName(input.nextLine());
			if (caseFourActorArr.size() == 0) {
				System.out.println("\nNo actors with that name.\n");
			} else {
				System.out.println("\n" + caseFourActorArr.size() + " actors with that name.\n");
				for (Actor caseFourActor : caseFourActorArr) {
					System.out.println(caseFourActor + "\n");
					System.out.println("Films:");
					for (Film caseFourFilm : caseFourActor.getFilms()) {
						System.out.println(caseFourFilm.toStringIndented() + "\n");
					}
				}
			}
			break;
		case "5":
			stillRunning = false;
			System.out.println("Thanks for using Klayton's Film Query App");
			break;
		default:
			System.out.println("Invalid Entry. Please Enter A Number from 1-5.\n");
			break;
		}

	}

}
