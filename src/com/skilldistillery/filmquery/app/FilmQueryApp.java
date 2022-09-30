package com.skilldistillery.filmquery.app;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

import com.skilldistillery.filmquery.database.DatabaseAccessor;
import com.skilldistillery.filmquery.database.DatabaseAccessorObject;
import com.skilldistillery.filmquery.entities.Film;

public class FilmQueryApp {

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
		try {
			startUserInterface(input);
		} catch (SQLException e) {
			
		}

		input.close();
	}

	private void startUserInterface(Scanner input) throws SQLException {
		System.out.println("-----------------MENU-----------------");
		System.out.println("1. Look up a film by its id.");
		System.out.println("2. Look up a film by a search keyword.");
		System.out.println("3. Exit the application.");
		String menuSelection = input.nextLine();
		switch (menuSelection) {
		case "1":
			System.out.print("Enter the id you would like to search: ");
			Film film = db.findFilmById(input.nextInt());
			input.nextLine();
			System.out.println(film);
			break;
		case "2":
			System.out.print("Enter the keyword you would like to search: ");
			List<Film> films = db.findFilmByKeyword(input.nextLine());
			for (Film aFilm : films) {
				System.out.println(aFilm);
				System.out.println();
			}
			break;
		case "3":
			
			break;
		default:
			break;
		}

	}

}
