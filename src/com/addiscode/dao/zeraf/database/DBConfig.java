package com.addiscode.dao.zeraf.database;


//jdbc:mysql://localhost:3306/video_rental?user=video_rental&password=v!d9@55
public class DBConfig {
	private String host;
	private int port;
	private String dbName;
	private String dbUser;
	private String dbPassword;
	
	
	public String getHost() {
		return host;
	}
	public DBConfig setHost(String host) {
		this.host = host;
		return this;
	}
	public int getPort() {
		return port;
	}
	public DBConfig setPort(int port) {
		this.port = port;
		return this;
	}
	public String getDbName() {
		return dbName;
	}
	public DBConfig setDbName(String dbName) {
		this.dbName = dbName;
		return this;
	}
	public String getDbUser() {
		return dbUser;
	}
	public DBConfig setDbUser(String dbUser) {
		this.dbUser = dbUser;
		return this;
	}
	public String getDbPassword() {
		return dbPassword;
	}
	public DBConfig setDbPassword(String dbPassword) {
		this.dbPassword = dbPassword;
		return this;
	}
	
	
	@Override
	public String toString() {
		return host+":"+port+"/"+dbName;
	}
}
