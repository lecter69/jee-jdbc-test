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

public class ActorManager {

	private Connection connection;

	private String url = "jdbc:hsqldb:hsql://localhost/workdb";

	private String createTableActor = "CREATE TABLE Actor(id bigint GENERATED BY DEFAULT AS IDENTITY, firstName varchar(20), lastName varchar(20))";

	private PreparedStatement getActorStmt;
	private PreparedStatement addActorStmt;
	private PreparedStatement updateActorStmt;
	private PreparedStatement deleteActorStmt;
	private PreparedStatement deleteAllActorsStmt;
	private PreparedStatement getAllActorsStmt;

	private Statement statement;

	public ActorManager() {
		try {
			connection = DriverManager.getConnection(url);
			connection.setAutoCommit(false);

			statement = connection.createStatement();

			ResultSet rs = connection.getMetaData().getTables(null, null, null,
					null);

			boolean tableExists = false;

			while (rs.next()) {
				if ("Actor".equalsIgnoreCase(rs.getString("TABLE_NAME"))) {
					tableExists = true;
					break;
				}
			}

			if (!tableExists)
				statement.executeUpdate(createTableActor);

			getActorStmt = connection
					.prepareStatement("SELECT * FROM Actor WHERE id = ?");
			addActorStmt = connection
					.prepareStatement("INSERT INTO Actor (firstName, lastName) VALUES (?, ?)");
			deleteActorStmt = connection
					.prepareStatement("DELETE FROM Actor WHERE id = ?");
			updateActorStmt = connection
					.prepareStatement("UPDATE Actor SET firstName = ?, lastName = ? WHERE id = ?");
			deleteAllActorsStmt = connection
					.prepareStatement("DELETE FROM Actor");
			getAllActorsStmt = connection
					.prepareStatement("SELECT * FROM Actor");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Connection getConnection() {
		return connection;
	}

	void deleteAllActors() throws SQLException {
		try {
			deleteAllActorsStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		}
	}

	public int updateActor(Actor actor, long id) throws SQLException {
		int count = 0;
		try {
			updateActorStmt.setString(1, actor.getFirstName());
			updateActorStmt.setString(2, actor.getLastName());
			updateActorStmt.setLong(3, id);

			count = updateActorStmt.executeUpdate();
			connection.commit();

		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		}
		return count;
	}

	public int addActor(Actor actor) throws SQLException {
		int count = 0;
		try {
			addActorStmt.setString(1, actor.getFirstName());
			addActorStmt.setString(2, actor.getLastName());

			count = addActorStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		}
		return count;
	}

	public int deleteActor(Actor actor) throws SQLException {
		int count = 0;
		try {
			deleteActorStmt.setLong(1, actor.getId());

			count = deleteActorStmt.executeUpdate();
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		}
		return count;
	}

	public Actor getActor(long id) throws SQLException {
		Actor actor = new Actor();

		try {
			getActorStmt.setLong(1, id);
			ResultSet rs = getActorStmt.executeQuery();

			boolean found = rs.next();

			if (!found) {
				return null;
			} else {
				actor.setId(rs.getInt("id"));
				actor.setFirstName(rs.getString("firstName"));
				actor.setLastName(rs.getString("lastName"));
			}
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		}
		return actor;
	}

	public List<Actor> getAllActors() throws SQLException {
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
			connection.commit();
		} catch (SQLException e) {
			e.printStackTrace();
			connection.rollback();
		}
		return actors;
	}

}
