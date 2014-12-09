package com.addiscode.dao.zeraf.factory;

import java.sql.SQLException;
import java.util.Hashtable;

import org.apache.tomcat.dbcp.pool.impl.GenericKeyedObjectPool.Config;

import com.addiscode.dao.zeraf.database.DBConfig;
import com.addiscode.dao.zeraf.database.Database;
import com.addiscode.dao.zeraf.database.MySql.MySqlDB;

public class DbConnectionFactory {

	//Hiding constructor
	private DbConnectionFactory(){}
	
	public enum DBTypes {
		MYSQL, SQLITE, MSSQL;
	}
	
	//Hashtable for trade safety
	private static Hashtable<String, Database> dbPool = new Hashtable<String, Database>();

	
	/**
	 * Because creating and estabilishing Database connections is typically expensive
	 * operation we should reuse connection pool using flyweight pattern
	 * if type of database connection is already estabilished we return the reference 
	 * without the cost of creating a new one B-)///>
	 * @param type
	 * @return
	 * @throws IllegalArgumentException
	 */
	public static Database getDatabase(DBTypes type, DBConfig dbConfig) throws IllegalArgumentException, SQLException, ClassNotFoundException {
		
		Database db = dbPool.get(dbConfig.toString());
		
		if(db == null) {
			switch(type) {
			case MYSQL:
				db = MySqlDB.getNewInstance(dbConfig);
				break;
			default:
				throw new IllegalArgumentException("Unsupported database type");
			}
			
			//put the connection to dbpool
			dbPool.put(dbConfig.toString(), db);
		}
		
		return db;
	}

}
