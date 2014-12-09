package com.addiscode.dao.zeraf.database.command;

import java.lang.reflect.Field;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.addiscode.dao.zeraf.annotation.Column;
import com.addiscode.dao.zeraf.annotation.Id;
import com.addiscode.dao.zeraf.annotation.Table;

public class UpdateCommandStrategy implements CommandStrategy {
	private Object modelObj = null;
	
	
	public UpdateCommandStrategy(Object modelObject) {
		this.modelObj = modelObject;
	}
	
	public void setModelObj(Object modelObj) {
		this.modelObj = modelObj;
	}
	
	@Override
	public String getPreparedStatment() throws SQLException, IllegalArgumentException, IllegalAccessException  {
		// TODO Auto-generated method stub
		StringBuilder sqlBilder = new StringBuilder();
		Class modelClass = modelObj.getClass();
		
		sqlBilder.append("UPDATE ");
		Table tableAnnot = (Table) modelClass.getAnnotation(Table.class);
		if (tableAnnot == null)
			throw new SQLException("Model not associated with table with @Table");
		
		sqlBilder.append(tableAnnot.name());
		
		sqlBilder.append(" SET ");
		
		StringBuilder whereClause = new StringBuilder();
		char q = '\'';
		char fq = '`';
		
		Field[] fields = modelClass.getDeclaredFields();
		for(Field f: fields) {
			f.setAccessible(true);
			
		
			Column colAnnot = (Column) f.getAnnotation(Column.class);
			Id idAnnot = (Id) f.getAnnotation(Id.class);
			
			String fieldVal = String.valueOf(f.get(modelObj));
			
			if(idAnnot != null)
				whereClause.append(colAnnot.value() + "="+ q +fieldVal + q);
			
			if(colAnnot != null && f.get(modelObj) != null && idAnnot == null) {	
				sqlBilder.append(fq +colAnnot.value() + fq + "="+ q + fieldVal + q + ", ");
			}
		}
		
		//remove the last ", " chars
		sqlBilder.delete(sqlBilder.length()-2, sqlBilder.length());
	
		sqlBilder.append(" WHERE ");
		
		sqlBilder.append(whereClause);
		
		sqlBilder.append(";");
		return sqlBilder.toString();
	}

}
