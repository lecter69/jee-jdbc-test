package com.example.jdbcdemo.domain;

public class ActorMovie {

	private long actorId;
	private long movieId;

	
	public ActorMovie() {
	}
	
	public ActorMovie(long actorId, long movieId) {
		super();
		this.actorId = actorId;
		this.movieId = movieId;
	}
	
	public long getActorId() {
		return actorId;
	}
	public void setActorId(long actorId) {
		this.actorId = actorId;
	}
	
	public long getMovieId() {
		return movieId;
	}
	public void setMovieId(long movieId) {
		this.movieId = movieId;
	}	
	
}
