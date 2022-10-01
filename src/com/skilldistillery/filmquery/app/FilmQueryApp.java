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
//		try {
//			app.test();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		app.launch();
	}

	private void test() throws SQLException {
		List<Film> films = db.findFilmByKeyword("the");
//    Actor actor = db.findActorById(3);
		for (Film film : films) {
			System.out.println(film);

		}
	}

	private void launch() {
		Scanner input = new Scanner(System.in);
		System.out.println("Welcome to the Film Query App");
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
		System.out.println("| 3. Exit the application.               |");
		System.out.println("------------------------------------------");
		String menuSelection = input.nextLine();
		switch (menuSelection) {
		case "1":
			System.out.print("Enter the id you would like to search: ");
			Film film = db.findFilmById(input.nextInt());
			input.nextLine();
			if (film == null) {
				System.out.println("\nThat film does not exist\n");
			} else {
				System.out.println("\n" + film.toStringWithActors() + "\n");
			}
			break;
		case "2":
			System.out.print("Enter the keyword you would like to search: ");
			List<Film> films = db.findFilmByKeyword(input.nextLine());
			if (films.size() == 0) {
				System.out.println("\nNo films match that keyword.\n");
			} else {
				System.out.println("\n" + films.size() + " films found that matched your keyword.\n");
				for (Film aFilm : films) {
					System.out.println(aFilm.toStringWithActors() + "\n");
				}
			}
			break;
		case "3":
			stillRunning = false;
			System.out.println("Thanks for using the Film Query App");
			break;
//		case "4":
//			System.out.print("Enter the actor's id: ");
//			Actor actor = db.findActorById(input.nextInt());
//			input.nextLine();
//			if (actor == null) {
//				System.out.println("\nThat actor does not exist\n");
//			} else {
//				System.out.println("\n" + actor.toStringWithFilms() + "\n");
//			}
//			break;
		default:
			System.out.println("Invalid Entry. Please Enter A Number from 1-3.\n");
			break;
		}

	}

}
