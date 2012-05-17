package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.ActorMovie;

public class ActorMovieManager {

	private Connection connection;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTableActorMovie = "CREATE TABLE ActorMovie(actorId bigint, movieId bigint)";
	
	private PreparedStatement getActorInMovieStmt;
	private PreparedStatement getMovieInActorStmt;
	private PreparedStatement addActorMovieStmt;
	private PreparedStatement deleteActorMovieStmt;
	private PreparedStatement deleteAllActorMoviesStmt;
	private PreparedStatement getAllActorMoviesStmt;

	private Statement statement;
	
	public ActorMovieManager() {
		try {
			connection = DriverManager.getConnection(url);
			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);
			boolean tableExists = false;
			while (rs.next()) {
				if ("ActorMovie".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(createTableActorMovie);
			
			// aktorzy w filmie
			getActorInMovieStmt = connection
					.prepareStatement("SELECT actorId FROM ActorMovie WHERE movieId = ?");
			// filmy, w których wystąpił aktor
			getMovieInActorStmt = connection
					.prepareStatement("SELECT movieId FROM ActorMovie WHERE actorId = ?");	
			
			addActorMovieStmt = connection
					.prepareStatement("INSERT INTO Actor (actorId, movieId) VALUES (?, ?)");
			deleteActorMovieStmt = connection
					.prepareStatement("DELETE FROM Actor WHERE actorId = ? and movieId = ?");			
			deleteAllActorMoviesStmt = connection
					.prepareStatement("DELETE FROM ActorMovie");
			getAllActorMoviesStmt = connection
					.prepareStatement("SELECT * FROM ActorMovie");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	void deleteAllActorMovies() {
		try {
			deleteAllActorMoviesStmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int addActorMovie(ActorMovie actorMovie) {
		int count = 0;
		try {
			addActorMovieStmt.setLong(1, actorMovie.getActorId());
			addActorMovieStmt.setLong(2, actorMovie.getMovieId());
	
		count = addActorMovieStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public int deleteActorMovie(ActorMovie actorMovie) {
		int count = 0;
		try {
			deleteActorMovieStmt.setLong(1, actorMovie.getActorId());
			deleteActorMovieStmt.setLong(2, actorMovie.getMovieId());
		
		count = deleteActorMovieStmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return count;
	}
	
	public ActorMovie getActorInMovie(long movieId) {		
		List<ActorMovie> actorMovies = new ArrayList<ActorMovie>();

		try {
			getActorInMovieStmt.setLong(1, movieId);
			ResultSet rs = getActorInMovieStmt.executeQuery();

			while (rs.next()) {
				ActorMovie p = new ActorMovie();
				p.setActorId(rs.getLong("actorId"));
				p.setMovieId(rs.getLong("movieId"));
				actorMovies.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actorMovies;
	}
	
	// ######################## tu skonczylem
	public ActorMovie getMovieInActor(long id) {
		Actor actor = new Actor();

		try {			
			getActorStmt.setLong(1, id);
			ResultSet rs = getActorStmt.executeQuery();
									
			boolean found = rs.next();
						
			if( !found ) {
				return null;
			} else {			
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("firstName"));
				actor.setLastName(rs.getString("lastName"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actor;		
	}

	public List<Movier> getAllActorMovies() {
		List<Actor> actors = new ArrayList<Actor>();

		try {
			ResultSet rs = getAllActorsStmt.executeQuery();

			while (rs.next()) {
				Actor p = new Actor();
				p.setId(rs.getInt("id"));
				p.setFirstName(rs.getString("firstName"));
				p.setLastName(rs.getString("lastName"));
				actors.add(p);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return actors;
	}
	
}
