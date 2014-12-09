package com.addiscode.dao.zeraf.database;



public class Restriction {
	
	private String field = null;
	private String val = null;
	private String operator = null;
	private String logicGlue = null;
	
	private Restriction(String field, String val, String op) {
		this.field = field;
		this.val = val;
		this.operator = op;
		this.logicGlue = "AND";
	}
	
	
	
	public String getField() {
		return field;
	}



	public String getVal() {
		return val;
	}



	public String getOperator() {
		return operator;
	}



	public String getLogicGlue() {
		return logicGlue;
	}

	public void setLogicGlue(String logic) {
		this.logicGlue = logic;
	}
	
	public static Restriction eq(String field, String val) {
		return new Restriction(field, val, "=");
	}
	
	public static Restriction like(String field, String val) {
		return new Restriction(field, val, "LIKE");
	}
	
	public static Restriction lt(String field, String val) {
		return new Restriction(field, val, "<");
	}
	
	public static Restriction gt(String field, String val) {
		return new Restriction(field, val, ">");
	}
	
	public static Restriction lte(String field, String val) {
		return new Restriction(field, val, "<=");
	}
	
	public static Restriction gte(String field, String val) {
		return new Restriction(field, val, ">=");
	}
	
	public static Restriction neq(String field, String val) {
		return new Restriction(field, val, "<>");
	}
	
	
	@Override
	public String toString() {
		char q = '\'';
		char fq = '`';
		
		return fq + this.field + fq + " " + this.operator + " " + q + this.val + q;
	}
	
}
