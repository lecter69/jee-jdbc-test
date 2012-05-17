package com.example.jdbcdemo.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.sql.PreparedStatement;
import java.util.List;

import org.junit.Test;

import com.example.jdbcdemo.domain.Actor;
import com.example.jdbcdemo.domain.ActorMovie;

public class ActorMovieManagerTest {
	
	ActorMovieManager actorMovieManager = new ActorMovieManager();
	
	private final static int ACTORID_1 = 1;
	private final static int MOVIEID_1 = 2;
	
	@Test
	public void checkConnect(){
		assertNotNull(actorMovieManager.getConnection());
	}
	
	// private PreparedStatement deleteAllActorMoviesStmt;
	// private PreparedStatement getAllActorMoviesStmt;
	
	@Test
	public void checkAddActorMovie(){		
		ActorMovie actorMovie = new ActorMovie(ACTORID_1, MOVIEID_1);
		
		actorMovieManager.deleteAllActorMovies();
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie));
		
		List<ActorMovie> actorMovies = actorMovieManager.getAllActorMovies();
		ActorMovie actorMovieRetrieved = actorMovies.get(0);
		
		assertEquals(ACTORID_1, actorMovieRetrieved.getActorId());
		assertEquals(MOVIEID_1, actorMovieRetrieved.getMovieId());
		
		actorMovieManager.deleteAllActorMovies();
	}
	
	@Test
	public void checkDeleteActorMovie(){
		ActorMovie actorMovie = new ActorMovie(ACTORID_1, MOVIEID_1);
		
		actorMovieManager.deleteAllActorMovies();
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie));
		
		List<ActorMovie> actorMovies = actorMovieManager.getAllActorMovies();
		ActorMovie actorMovieRetrieved = actorMovies.get(0);
		
		long actorId = actorMovieRetrieved.getActorId();
		long movieId = actorMovieRetrieved.getMovieId();
		
		actorMovieManager.deleteActorMovie(actorMovieRetrieved);
		
		assertNull(actorMovieManager.getActorMovie(actorId, movieId));	
		
		actorMovieManager.deleteAllActorMovies();
	}
	
	@Test
	public void checkGetActorMovie(){
		ActorMovie actorMovie = new ActorMovie(ACTORID_1, MOVIEID_1);	
		
		actorMovieManager.deleteAllActorMovies();
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie));
		
		List<ActorMovie> actorMovies = actorMovieManager.getAllActorMovies();
		ActorMovie actorMovieRetrieved = actorMovies.get(0);
			
		assertEquals(ACTORID_1, actorMovieRetrieved.getActorId());
		assertEquals(MOVIEID_1, actorMovieRetrieved.getMovieId());
		
		actorMovieManager.deleteAllActorMovies();
	}
	
	@Test
	public void checkDeleteAllActorMovies() {
		ActorMovie actorMovie = new ActorMovie(ACTORID_1, MOVIEID_1);	
		
		actorMovieManager.deleteAllActorMovies();
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie));
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie));
		
		List<ActorMovie> actorMovies = actorMovieManager.getAllActorMovies();

		assertEquals(2, actorMovies.size());
		
		actorMovieManager.deleteAllActorMovies();
		
		actorMovies = actorMovieManager.getAllActorMovies();
		
		assertEquals(0, actorMovies.size());	
		
		actorMovieManager.deleteAllActorMovies();
	}
	
	@Test
	public void checkGetAllActorMovies() {
		ActorMovie actorMovie1 = new ActorMovie(ACTORID_1, MOVIEID_1);	
		ActorMovie actorMovie2 = new ActorMovie(ACTORID_1+ACTORID_1, MOVIEID_1+MOVIEID_1);
		
		actorMovieManager.deleteAllActorMovies();
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie1));
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie2));
		
		List<ActorMovie> actorMovies = actorMovieManager.getAllActorMovies();

		assertEquals(2, actorMovies.size());		
				
		assertEquals(ACTORID_1,actorMovies.get(0).getActorId());
		assertEquals(MOVIEID_1,actorMovies.get(0).getMovieId());
		
		assertEquals(ACTORID_1+ACTORID_1,actorMovies.get(1).getActorId());
		assertEquals(MOVIEID_1+MOVIEID_1,actorMovies.get(1).getMovieId());
		
		actorMovieManager.deleteAllActorMovies();
	}
	
	@Test
	public void checkGetActorsInMovieStmt() {
		ActorMovie actorMovie1 = new ActorMovie(ACTORID_1, MOVIEID_1);	
		ActorMovie actorMovie2 = new ActorMovie(ACTORID_1+ACTORID_1, MOVIEID_1);
		
		actorMovieManager.deleteAllActorMovies();
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie1));
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie2));
		
		List<ActorMovie> actorsInMovie = actorMovieManager.getActorsInMovie(MOVIEID_1);

		assertEquals(2, actorsInMovie.size());	
		
		assertEquals(ACTORID_1,actorsInMovie.get(0).getActorId());
		assertEquals(MOVIEID_1,actorsInMovie.get(0).getMovieId());
		
		assertEquals(ACTORID_1+ACTORID_1,actorsInMovie.get(1).getActorId());
		assertEquals(MOVIEID_1,actorsInMovie.get(1).getMovieId());
		
		actorMovieManager.deleteAllActorMovies();
	}
	
	@Test
	public void checkGetMoviesInActorStmt() {
		ActorMovie actorMovie1 = new ActorMovie(ACTORID_1, MOVIEID_1);	
		ActorMovie actorMovie2 = new ActorMovie(ACTORID_1, MOVIEID_1+MOVIEID_1);
		
		actorMovieManager.deleteAllActorMovies();
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie1));
		assertEquals(1,actorMovieManager.addActorMovie(actorMovie2));
		
		List<ActorMovie> moviesInActor = actorMovieManager.getMoviesInActor(ACTORID_1);

		assertEquals(2, moviesInActor.size());	
		
		assertEquals(ACTORID_1,moviesInActor.get(0).getActorId());
		assertEquals(MOVIEID_1,moviesInActor.get(0).getMovieId());
		
		assertEquals(ACTORID_1,moviesInActor.get(1).getActorId());
		assertEquals(MOVIEID_1+MOVIEID_1,moviesInActor.get(1).getMovieId());
		
		actorMovieManager.deleteAllActorMovies();
	}

}
