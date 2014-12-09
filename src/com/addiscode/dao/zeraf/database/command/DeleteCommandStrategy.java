package com.addiscode.dao.zeraf.database.command;

import java.lang.reflect.Field;
import java.sql.SQLException;

import com.addiscode.dao.zeraf.annotation.Column;
import com.addiscode.dao.zeraf.annotation.Id;
import com.addiscode.dao.zeraf.annotation.Table;
import com.addiscode.dao.zeraf.database.Restriction;
import com.addiscode.dao.zeraf.database.criteria.SingleCriteria;

public class DeleteCommandStrategy implements CommandStrategy {

	private Object modelObj = null;

	public DeleteCommandStrategy(Object modelObject) {
		this.modelObj = modelObject;
	}

	public void setModelObj(Object modelObj) {
		this.modelObj = modelObj;
	}

	@Override
	public String getPreparedStatment() throws SQLException, IllegalArgumentException, IllegalAccessException  {
		// TODO Auto-generated method stub
		
		char q = '\'';
		char fq = '`';
		
		StringBuilder sqlBilder = new StringBuilder();
		Class modelClass = modelObj.getClass();
		
		sqlBilder.append("DELETE FROM ");
		Table tableAnnot = (Table) modelClass.getAnnotation(Table.class);
		if (tableAnnot == null)
			throw new SQLException("Model not associated with table with @Table");
		
		sqlBilder.append(fq+tableAnnot.name()+fq);
		
		Field[] fields = modelClass.getDeclaredFields();
		
		SingleCriteria singleCriteria = new SingleCriteria();
		for(Field f: fields) {
			f.setAccessible(true);
			String fieldVal = String.valueOf(f.get(modelObj));
		
			
			Column colAnnot = (Column) f.getAnnotation(Column.class);
			Id idAnnot = (Id) f.getAnnotation(Id.class);
			
			if(colAnnot != null) {
				singleCriteria.addAnd(Restriction.eq(colAnnot.value(), fieldVal));
			}
		}
		
		
		sqlBilder.append(" WHERE " + singleCriteria.getQueryString() +";");
		
		return sqlBilder.toString();
	}

}
