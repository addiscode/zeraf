package com.addiscode.dao.zeraf.database.command;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.addiscode.dao.zeraf.annotation.Column;
import com.addiscode.dao.zeraf.annotation.Ref;
import com.addiscode.dao.zeraf.annotation.Table;
import com.addiscode.dao.zeraf.database.criteria.Criteria;

public class SelectCommandStrategy implements CommandStrategy {
	private Criteria criteria = null;
	private Class modelClass = null;
	
	
	public SelectCommandStrategy(Criteria criteria, Class modelClass) {
		this.setCriteria(criteria);
		this.modelClass = modelClass;
	}
	
	public void setCriteria(Criteria criteria) {
		this.criteria = criteria;
	}
	
	@Override
	public String getPreparedStatment() throws SQLException  {
		StringBuilder sqlBuilder = new StringBuilder();

		sqlBuilder.append("SELECT * ");

		Table tableAnnot = (Table) modelClass.getAnnotation(Table.class);
		if (tableAnnot == null)
			throw new SQLException("Model not associated with table with @Table");

		sqlBuilder.append(" FROM " + tableAnnot.name());

		Field[] fields = modelClass.getDeclaredFields();
		for (Field f : fields) {
			Ref refAnnot = (Ref) f.getAnnotation(Ref.class);
			if (refAnnot != null) {
				sqlBuilder.append(" INNER JOIN " + refAnnot.on() + " ON "
						+ tableAnnot.name() + "_" + refAnnot.on() + "_id" + "="
						+ refAnnot.on() + "_id");

			}
		}

		if (criteria != null)
			sqlBuilder.append(" WHERE " + criteria.getQueryString());

		sqlBuilder.append(";");
		return sqlBuilder.toString();
	}

	/**
	 * Accepts new instance of model object and returns object params populated
	 * accordingly!!
	 * 
	 * @param resultSet
	 * @param modelClass
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public Object getModelObject(ResultSet resultSet, Class modelClass)
			throws InstantiationException, IllegalAccessException,
			NoSuchMethodException, SecurityException, IllegalArgumentException,
			InvocationTargetException, SQLException, ClassNotFoundException {
		Object model = modelClass.newInstance();

		Field[] fields = modelClass.getDeclaredFields();

		for (Field f : fields) {
			setProperty(resultSet, model, f);
		}

		return model;
	}

	/**
	 * maps Object to result set based on annotation info. it's not recursive!!
	 * 
	 * @param resultSet
	 * @param bean
	 * @param field
	 * @param annot
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 * @throws InstantiationException
	 */
	private void setProperty(ResultSet resultSet, Object bean, Field f)
			throws NoSuchMethodException, SecurityException,
			IllegalAccessException, IllegalArgumentException,
			InvocationTargetException, SQLException, InstantiationException,
			ClassNotFoundException {

		String setterMethodName = "set"
				+ f.getName().substring(0, 1).toUpperCase()
				+ f.getName().substring(1);

		Method setterMethod = bean.getClass().getMethod(setterMethodName,
				f.getType());

		Column colAnnot = f.getAnnotation(Column.class);
		Ref refAnnot = f.getAnnotation(Ref.class);

		if (colAnnot != null) {
			if (f.getType() == String.class) {
				setterMethod
						.invoke(bean, resultSet.getString(colAnnot.value()));
			} else if (f.getType() == int.class) {
				setterMethod.invoke(bean, resultSet.getInt(colAnnot.value()));
			} else if (f.getType() == double.class) {
				setterMethod
						.invoke(bean, resultSet.getDouble(colAnnot.value()));
			}
		}

		if (refAnnot != null) {
			System.out.println(f.getName() + " refers to: " + refAnnot.model());
			Class<?> c = Class.forName(refAnnot.model());
			Object modelObject = getModelObject(resultSet, c);
			setterMethod.invoke(bean, modelObject);
		}

		// JAVA BEANS CONVENTIONAL SETTER METHOD NAME

	}

	

}
