package com.addiscode.dao.zeraf.database;

import java.util.List;

import com.addiscode.dao.zeraf.database.criteria.Criteria;

public interface Database {
	public void connect() throws Exception;
	public void disconnect() throws Exception;


	public List find(Criteria criteriaObject, Class modelClass) throws Exception;
	public int insert(Object modelObj)  throws Exception;
	public int update(Object modelObj)  throws Exception;
	public int delete(Object modelObj)  throws Exception;

}
