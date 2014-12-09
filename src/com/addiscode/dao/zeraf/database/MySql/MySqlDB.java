package com.addiscode.dao.zeraf.database.MySql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.addiscode.dao.zeraf.database.DBConfig;
import com.addiscode.dao.zeraf.database.Database;
import com.addiscode.dao.zeraf.database.command.CommandStrategy;
import com.addiscode.dao.zeraf.database.command.DeleteCommandStrategy;
import com.addiscode.dao.zeraf.database.command.InsertCommandStrategy;
import com.addiscode.dao.zeraf.database.command.SelectCommandStrategy;
import com.addiscode.dao.zeraf.database.command.UpdateCommandStrategy;
import com.addiscode.dao.zeraf.database.criteria.Criteria;

public class MySqlDB implements Database {

	private DBConfig dbConfig = null;

	// hide constructor
	private MySqlDB(DBConfig config) throws SQLException,
			ClassNotFoundException {
		this.dbConfig = config;
	}

	private Connection connection = null;

	@Override
	public void connect() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");

		String connectionString = "jdbc:mysql://" + dbConfig.getHost() + ":"
				+ dbConfig.getPort() + "/" + dbConfig.getDbName() + "?user="
				+ dbConfig.getDbUser() + "&password="
				+ dbConfig.getDbPassword();

		this.connection = DriverManager.getConnection(connectionString);
	}

	@Override
	public void disconnect() throws SQLException {
		// TODO Auto-generated method stub
		System.out.println("Closing connection to mysql");
		this.connection.close();
	}

	public static Database getNewInstance(DBConfig config) throws SQLException,
			ClassNotFoundException {
		MySqlDB db = new MySqlDB(config);
		db.connect();
		return db;
	}

	private PreparedStatement getPreparedStatment(CommandStrategy strategy)
			throws Exception {
		String sql = strategy.getPreparedStatment();
		System.out.println(sql);
		return connection.prepareStatement(sql);
	}

	@Override
	public List find(Criteria criteriaObject, Class modelClass)
			throws SQLException, Exception {
		System.out.println("Inside DB::find method");
		List<Object> resultList = new ArrayList<>();

		SelectCommandStrategy selectStrategy = new SelectCommandStrategy(
				criteriaObject, modelClass);

		ResultSet resultSet = getPreparedStatment(selectStrategy)
				.executeQuery();

		while (resultSet.next()) {
			Object modelObj = selectStrategy.getModelObject(resultSet,
					modelClass);
			if (modelObj != null)
				resultList.add(modelObj);
		}

		return resultList;
	}

	@Override
	public int update(Object modelObj) throws SQLException, Exception {
		UpdateCommandStrategy updateStrategy = new UpdateCommandStrategy(
				modelObj);
		return getPreparedStatment(updateStrategy).executeUpdate();
	}

	@Override
	public int insert(Object modelObj) throws SQLException, Exception {
		InsertCommandStrategy insertStrategy = new InsertCommandStrategy(
				modelObj);
		return getPreparedStatment(insertStrategy).executeUpdate();

	}

	public int delete(Object modelObj) throws SQLException, Exception {
		DeleteCommandStrategy deleteStrategy = new DeleteCommandStrategy(
				modelObj);
		return getPreparedStatment(deleteStrategy).executeUpdate();

	}

}
