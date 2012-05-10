package com.example.jdbcdemo.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.List;
import org.junit.Test;

import com.example.jdbcdemo.domain.Movie;


public class MovieManagerTest {

	MovieManager movieManager = new MovieManager();
	
	private final static String NAME_1 = "Iron Sky";
	private final static int YEAR_1 = 2012;
	private final static String GENRE_1 = "SF";
	private final static int TIME_1 = 90;
	
	@Test
	public void checkConnect(){
		assertNotNull(movieManager.getConnection());
	}
	
	@Test
	public void checkAddMovie(){		
		Movie movie = new Movie(NAME_1, YEAR_1, GENRE_1, TIME_1);
		
		movieManager.deleteAllMovies();
		assertEquals(1,movieManager.addMovie(movie));
		
		List<Movie> movies = movieManager.getAllMovies();
		Movie movieRetrieved = movies.get(0);
		
		assertEquals(NAME_1, movieRetrieved.getName());
		assertEquals(YEAR_1, movieRetrieved.getYear());
		assertEquals(GENRE_1, movieRetrieved.getGenre());
		assertEquals(TIME_1, movieRetrieved.getTime());
		
		movieManager.deleteAllMovies();
	}
	
	@Test
	public void checkDeleteMovie(){
		Movie movie = new Movie(NAME_1, YEAR_1, GENRE_1, TIME_1);
		
		movieManager.deleteAllMovies();
		assertEquals(1,movieManager.addMovie(movie));
		
		List<Movie> movies = movieManager.getAllMovies();
		Movie movieRetrieved = movies.get(0);
		
		long id = movieRetrieved.getId();
		
		movieManager.deleteMovie(movieRetrieved);
		
		assertNull(movieManager.getMovie(id));	
		
		movieManager.deleteAllMovies();
	}
	
	@Test
	public void checkGetMovie(){
		Movie movie = new Movie(NAME_1, YEAR_1, GENRE_1, TIME_1);	
		
		movieManager.deleteAllMovies();
		assertEquals(1,movieManager.addMovie(movie));
		
		List<Movie> movies = movieManager.getAllMovies();
		Movie movieRetrieved = movies.get(0);
		
		long id = movieRetrieved.getId();
			
		assertEquals(id, movieRetrieved.getId());
		assertEquals(NAME_1, movieRetrieved.getName());
		assertEquals(YEAR_1, movieRetrieved.getYear());
		assertEquals(GENRE_1, movieRetrieved.getGenre());
		assertEquals(TIME_1, movieRetrieved.getTime());
		
		movieManager.deleteAllMovies();
	}
	
	@Test
	public void checkDeleteAllMovies() {
		Movie movie1 = new Movie(NAME_1, YEAR_1, GENRE_1, TIME_1);	
		
		movieManager.deleteAllMovies();
		assertEquals(1,movieManager.addMovie(movie1));
		assertEquals(1,movieManager.addMovie(movie1));
		
		List<Movie> movies = movieManager.getAllMovies();

		assertEquals(2, movies.size());
		
		movieManager.deleteAllMovies();
		
		movies = movieManager.getAllMovies();
		
		assertEquals(0, movies.size());	
		
		movieManager.deleteAllMovies();
	}
	
	@Test
	public void checkGetAllMovies() {
		Movie movie1 = new Movie(NAME_1, YEAR_1, GENRE_1, TIME_1);	
		Movie movie2 = new Movie(NAME_1+NAME_1, YEAR_1+YEAR_1, GENRE_1+GENRE_1, TIME_1+TIME_1);
		
		movieManager.deleteAllMovies();
		assertEquals(1,movieManager.addMovie(movie1));
		assertEquals(1,movieManager.addMovie(movie2));
		
		List<Movie> movies = movieManager.getAllMovies();

		assertEquals(2, movies.size());		
				
		assertEquals(NAME_1,movies.get(0).getName());
		assertEquals(YEAR_1,movies.get(0).getYear());
		assertEquals(GENRE_1,movies.get(0).getGenre());
		assertEquals(TIME_1,movies.get(0).getTime());
		
		assertEquals(NAME_1+NAME_1,movies.get(1).getName());
		assertEquals(YEAR_1+YEAR_1,movies.get(1).getYear());
		assertEquals(GENRE_1+GENRE_1,movies.get(1).getGenre());
		assertEquals(TIME_1+TIME_1,movies.get(1).getTime());
		
		movieManager.deleteAllMovies();
	}
	
	@Test
	public void checkUpdateMovie() {
		Movie movie1 = new Movie(NAME_1, YEAR_1, GENRE_1, TIME_1);
		Movie movie2 = new Movie(NAME_1+NAME_1, YEAR_1+YEAR_1, GENRE_1+GENRE_1, TIME_1+TIME_1);
		
		movieManager.deleteAllMovies();
		assertEquals(1,movieManager.addMovie(movie1));
		
		List<Movie> movies = movieManager.getAllMovies();
		Movie movieRetrieved = movies.get(0);
		
		long id = movieRetrieved.getId();
		
		movieManager.updateMovie(movie2, id);			
		
		movies = movieManager.getAllMovies();
		movieRetrieved = movies.get(0);
		
		assertEquals(NAME_1+NAME_1,movieRetrieved.getName());
		assertEquals(YEAR_1+YEAR_1,movieRetrieved.getYear());
		assertEquals(GENRE_1+GENRE_1,movieRetrieved.getGenre());
		assertEquals(TIME_1+TIME_1,movieRetrieved.getTime());
		
		movieManager.deleteAllMovies();
	}
	
 }
