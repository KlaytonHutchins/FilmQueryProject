package com.skilldistillery.filmquery.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.skilldistillery.filmquery.entities.Actor;
import com.skilldistillery.filmquery.entities.Film;

public class DatabaseAccessorObject implements DatabaseAccessor {
	private static final String URL = "jdbc:mysql://localhost:3306/sdvid?useSSL=false&useLegacyDatetimeCode=false&serverTimezone=US/Mountain";
	static {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			System.out.println("ClassNotFoundException");
		}
	}

	public DatabaseAccessorObject() {
	}

	@Override
	public Film findFilmById(int filmId) throws SQLException {
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM film WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet filmResult = stmt.executeQuery();
		if (filmResult.next()) {
			film = new Film(filmResult.getInt("id"), filmResult.getString("title"),
					filmResult.getString("description"), filmResult.getInt("release_year"),
					determineLanguage(filmResult.getInt("language_id")), filmResult.getInt("rental_duration"),
					filmResult.getDouble("rental_rate"), filmResult.getInt("length"),
					filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
					filmResult.getString("special_features"));
			film.setActors(findActorsByFilmId(filmResult.getInt("id")));
		}
		conn.close();
		return film;
	}

	public List<Film> findFilmByKeyword(String keyword) throws SQLException {
		List<Film> films = new ArrayList<>();
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM film WHERE title LIKE ? OR description LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + keyword + "%");
		stmt.setString(2, "%" + keyword + "%");
		ResultSet filmResult = stmt.executeQuery();
		while (filmResult.next()) {
				film = new Film(filmResult.getInt("id"), filmResult.getString("title"),
						filmResult.getString("description"), filmResult.getInt("release_year"),
						determineLanguage(filmResult.getInt("language_id")), filmResult.getInt("rental_duration"),
						filmResult.getDouble("rental_rate"), filmResult.getInt("length"),
						filmResult.getDouble("replacement_cost"), filmResult.getString("rating"),
						filmResult.getString("special_features"));
			film.setActors(findActorsByFilmId(filmResult.getInt("id")));
			films.add(film);
		}
		conn.close();
		return films;
	}

	public Actor findActorById(int actorId) throws SQLException {
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM actor WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet actorResult = stmt.executeQuery();
		while (actorResult.next()) {
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
					actorResult.getString("last_name"));
			actor.setFilms(findFilmsByActorId(actorResult.getInt("id")));
		}
		conn.close();
		return actor;
	}

	public List<Actor> findActorsByName(String name) throws SQLException {
		List<Actor> actors = new ArrayList<>();
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM actor WHERE first_name LIKE ? OR last_name LIKE ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, "%" + name + "%");
		stmt.setString(2, "%" + name + "%");
		ResultSet actorResult = stmt.executeQuery();
		while (actorResult.next()) {
			actor = new Actor(actorResult.getInt("id"), actorResult.getString("first_name"),
					actorResult.getString("last_name"));
			actor.setFilms(findFilmsByActorId(actorResult.getInt("id")));
			actors.add(actor);
		}
		conn.close();
		return actors;
	}

	public List<Actor> findActorsByFilmId(int filmId) throws SQLException {
		List<Actor> actors = new ArrayList<Actor>();
		Actor actor = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM actor JOIN film_actor ON actor.id = film_actor.actor_id JOIN film ON film_actor.film_id = film.id WHERE film.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, filmId);
		ResultSet actorsResult = stmt.executeQuery();
		while (actorsResult.next()) {
			actor = new Actor(actorsResult.getInt("id"), actorsResult.getString("first_name"),
					actorsResult.getString("last_name"));
			actors.add(actor);
		}
		conn.close();
		return actors;
	}

	public List<Film> findFilmsByActorId(int actorId) throws SQLException {
		List<Film> films = new ArrayList<Film>();
		Film film = null;
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT * FROM film JOIN film_actor ON film.id = film_actor.film_id JOIN actor ON film_actor.actor_id = actor.id WHERE actor.id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, actorId);
		ResultSet filmsResult = stmt.executeQuery();
		while (filmsResult.next()) {
			film = new Film(filmsResult.getInt("id"), filmsResult.getString("title"),
					filmsResult.getString("description"), filmsResult.getInt("release_year"),
					determineLanguage(filmsResult.getInt("language_id")), filmsResult.getInt("rental_duration"),
					filmsResult.getDouble("rental_rate"), filmsResult.getInt("length"),
					filmsResult.getDouble("replacement_cost"), filmsResult.getString("rating"),
					filmsResult.getString("special_features"));
			films.add(film);
		}
		conn.close();
		return films;
	}

	private String determineLanguage(int langId) throws SQLException {
		String language = "";
		String user = "student";
		String pass = "student";
		Connection conn = DriverManager.getConnection(URL, user, pass);
		String sql = "SELECT name FROM language WHERE id = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, langId);
		ResultSet langResult = stmt.executeQuery();
		if (langResult.next()) {
			language = langResult.getString("name");
		}
		conn.close();
		return language;
	}

}
