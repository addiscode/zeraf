package com.addiscode.dao.zeraf.database.criteria;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import com.addiscode.dao.zeraf.database.Restriction;

public final class SingleCriteria implements Criteria {

	private LinkedList<Restriction> restrictions = new LinkedList<Restriction>();


	public SingleCriteria(Restriction... resList) {
		restrictions.addAll(Arrays.asList(resList));
	}

	public SingleCriteria addAnd(Restriction res) {
		if (restrictions.size() > 0)
			restrictions.getLast().setLogicGlue("AND");

		restrictions.add(res);
		return this;
	}

	public SingleCriteria addOr(Restriction res) {
		if (restrictions.size() > 0)
			restrictions.getLast().setLogicGlue("OR");

		restrictions.add(res);
		return this;
	}

	

	public static SingleCriteria newInstance(Restriction... res) {
		return new SingleCriteria(res);
	}

	public LinkedList<Restriction> getRestrictions() {
		return restrictions;
	}


	@Override
	public String getQueryString() {
		StringBuilder sqlBuilder = new StringBuilder();
		appendQueryString(sqlBuilder, this);
		return sqlBuilder.toString();
	}
	
	
	private void  appendQueryString(StringBuilder sqlBuilder, SingleCriteria criteria) {
		sqlBuilder.append("( ");

		Iterator<Restriction> resIterator = criteria.getRestrictions().iterator();
		while (resIterator.hasNext()) {
			Restriction res = resIterator.next();
			sqlBuilder.append(res);
			if (resIterator.hasNext())
				sqlBuilder.append(" " + res.getLogicGlue() + " ");
		}
		sqlBuilder.append(" ) ");

	}
}
