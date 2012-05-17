package com.example.jdbcdemo.service;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.SQLException;
import java.util.List;
import org.junit.Test;

import com.example.jdbcdemo.domain.Actor;

public class ActorManagerTest {

	ActorManager actorManager = new ActorManager();

	private final static String FIRSTNAME_1 = "Andrzej";
	private final static String LASTNAME_1 = "Kiełbasa";

	@Test
	public void checkConnect() {
		assertNotNull(actorManager.getConnection());
	}

	@Test
	public void checkAddActor() {
		try {
			Actor actor = new Actor(FIRSTNAME_1, LASTNAME_1);

			actorManager.deleteAllActors();
			assertEquals(1, actorManager.addActor(actor));

			List<Actor> actors = actorManager.getAllActors();
			Actor actorRetrieved = actors.get(0);

			assertEquals(FIRSTNAME_1, actorRetrieved.getFirstName());
			assertEquals(LASTNAME_1, actorRetrieved.getLastName());

			actorManager.deleteAllActors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkDeleteActor() {
		try {

			Actor actor = new Actor(FIRSTNAME_1, LASTNAME_1);

			actorManager.deleteAllActors();
			assertEquals(1, actorManager.addActor(actor));

			List<Actor> actors = actorManager.getAllActors();
			Actor actorRetrieved = actors.get(0);

			long id = actorRetrieved.getId();

			actorManager.deleteActor(actorRetrieved);

			assertNull(actorManager.getActor(id));

			actorManager.deleteAllActors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkGetActor() {

		try {
			Actor actor = new Actor(FIRSTNAME_1, LASTNAME_1);

			actorManager.deleteAllActors();
			assertEquals(1, actorManager.addActor(actor));

			List<Actor> actors = actorManager.getAllActors();
			Actor actorRetrieved = actors.get(0);

			long id = actorRetrieved.getId();

			assertEquals(id, actorRetrieved.getId());
			assertEquals(FIRSTNAME_1, actorRetrieved.getFirstName());
			assertEquals(LASTNAME_1, actorRetrieved.getLastName());

			actorManager.deleteAllActors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkDeleteAllActors() {

		try {
			Actor actor = new Actor(FIRSTNAME_1, LASTNAME_1);

			actorManager.deleteAllActors();
			assertEquals(1, actorManager.addActor(actor));
			assertEquals(1, actorManager.addActor(actor));

			List<Actor> actors = actorManager.getAllActors();

			assertEquals(2, actors.size());

			actorManager.deleteAllActors();

			actors = actorManager.getAllActors();

			assertEquals(0, actors.size());

			actorManager.deleteAllActors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkGetAllActors() {

		try {
			Actor actor1 = new Actor(FIRSTNAME_1, LASTNAME_1);
			Actor actor2 = new Actor(FIRSTNAME_1 + FIRSTNAME_1, LASTNAME_1
					+ LASTNAME_1);

			actorManager.deleteAllActors();
			assertEquals(1, actorManager.addActor(actor1));
			assertEquals(1, actorManager.addActor(actor2));

			List<Actor> actors = actorManager.getAllActors();

			assertEquals(2, actors.size());

			assertEquals(FIRSTNAME_1, actors.get(0).getFirstName());
			assertEquals(LASTNAME_1, actors.get(0).getLastName());

			assertEquals(FIRSTNAME_1 + FIRSTNAME_1, actors.get(1)
					.getFirstName());
			assertEquals(LASTNAME_1 + LASTNAME_1, actors.get(1).getLastName());

			actorManager.deleteAllActors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void checkUpdateActor() {

		try {
			Actor actor1 = new Actor(FIRSTNAME_1, LASTNAME_1);
			Actor actor2 = new Actor(FIRSTNAME_1 + FIRSTNAME_1, LASTNAME_1
					+ LASTNAME_1);

			actorManager.deleteAllActors();
			assertEquals(1, actorManager.addActor(actor1));

			List<Actor> actors = actorManager.getAllActors();
			Actor actorRetrieved = actors.get(0);

			long id = actorRetrieved.getId();

			actorManager.updateActor(actor2, id);

			actors = actorManager.getAllActors();
			actorRetrieved = actors.get(0);

			assertEquals(FIRSTNAME_1 + FIRSTNAME_1,
					actorRetrieved.getFirstName());
			assertEquals(LASTNAME_1 + LASTNAME_1, actorRetrieved.getLastName());

			actorManager.deleteAllActors();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
