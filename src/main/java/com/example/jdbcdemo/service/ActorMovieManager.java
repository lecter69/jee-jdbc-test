package com.example.jdbcdemo.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.example.jdbcdemo.domain.Actor;
import com.example.jdbcdemo.domain.ActorMovie;

public class ActorMovieManager {

	private Connection connection;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";
	
	private String createTableActorMovie = "CREATE TABLE ActorMovie(actorId bigint, movieId bigint)";
	
	private PreparedStatement getActorsInMovieStmt;
	private PreparedStatement getMoviesInActorStmt;
	private PreparedStatement addActorMovieStmt;
	private PreparedStatement deleteActorMovieStmt;
	private PreparedStatement getActorMovieStmt;
	private PreparedStatement deleteAllActorMoviesStmt;
	private PreparedStatement getAllActorMoviesStmt;

	private Statement statement;
	
	public ActorMovieManager() {
		try {
			connection = DriverManager.getConnection(url);
			
			connection.setAutoCommit(false);
			
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
			getActorsInMovieStmt = connection
					.prepareStatement("SELECT * FROM ActorMovie WHERE movieId = ?");
			// filmy, w których wystąpił aktor
			getMoviesInActorStmt = connection
					.prepareStatement("SELECT * FROM ActorMovie WHERE actorId = ?");				
			addActorMovieStmt = connection
					.prepareStatement("INSERT INTO ActorMovie (actorId, movieId) VALUES (?, ?)");
			getActorMovieStmt = connection
					.prepareStatement("SELECT * FROM ActorMovie WHERE actorId = ? and movieId = ?");
			deleteActorMovieStmt = connection
					.prepareStatement("DELETE FROM ActorMovie WHERE actorId = ? and movieId = ?");			
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

	public ActorMovie getActorMovie(long actorId, long movieId) {
		ActorMovie actorMovie = new ActorMovie();

		try {			
			getActorMovieStmt.setLong(1, actorId);
			getActorMovieStmt.setLong(2, movieId);
			ResultSet rs = getActorMovieStmt.executeQuery();
									
			boolean found = rs.next();
						
			if( !found ) {
				return null;
			} else {			
				actorMovie.setActorId(rs.getInt("actorId"));
				actorMovie.setMovieId(rs.getInt("movieId"));

			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return actorMovie;		
	}
	
	void deleteAllActorMovies() {
		try {
			deleteAllActorMoviesStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	public int addActorMovie(ActorMovie actorMovie) {
		int count = 0;
		try {
			addActorMovieStmt.setLong(1, actorMovie.getActorId());
			addActorMovieStmt.setLong(2, actorMovie.getMovieId());
	
		count = addActorMovieStmt.executeUpdate();
		connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return count;
	}
	
	public int deleteActorMovie(ActorMovie actorMovie) {
		int count = 0;
		try {
			deleteActorMovieStmt.setLong(1, actorMovie.getActorId());
			deleteActorMovieStmt.setLong(2, actorMovie.getMovieId());
		
		count = deleteActorMovieStmt.executeUpdate();
		connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return count;
	}
	
	public List<ActorMovie> getActorsInMovie(long movieId) {		
		List<ActorMovie> actorMovies = new ArrayList<ActorMovie>();

		try {
			getActorsInMovieStmt.setLong(1, movieId);
			ResultSet rs = getActorsInMovieStmt.executeQuery();

			while (rs.next()) {
				ActorMovie p = new ActorMovie();
				p.setActorId(rs.getLong("actorId"));
				p.setMovieId(rs.getLong("movieId"));
				actorMovies.add(p);
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return actorMovies;
	}

	public List<ActorMovie> getMoviesInActor(long actorId) {
		List<ActorMovie> actorMovies = new ArrayList<ActorMovie>();

		try {
			getMoviesInActorStmt.setLong(1, actorId);
			ResultSet rs = getMoviesInActorStmt.executeQuery();

			while (rs.next()) {
				ActorMovie p = new ActorMovie();
				p.setActorId(rs.getLong("actorId"));
				p.setMovieId(rs.getLong("movieId"));
				actorMovies.add(p);
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return actorMovies;
	}

	public List<ActorMovie> getAllActorMovies() {
		List<ActorMovie> actorMovies = new ArrayList<ActorMovie>();

		try {
			ResultSet rs = getAllActorMoviesStmt.executeQuery();

			while (rs.next()) {
				ActorMovie p = new ActorMovie();
				p.setActorId(rs.getLong("actorId"));
				p.setMovieId(rs.getLong("movieId"));
				actorMovies.add(p);
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return actorMovies;
	}
	
}
