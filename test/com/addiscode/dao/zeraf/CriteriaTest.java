package com.addiscode.dao.zeraf;

import static org.junit.Assert.assertTrue;

import org.eclipse.jdt.core.compiler.InvalidInputException;
import org.junit.Before;
import org.junit.Test;

import com.addiscode.dao.zeraf.database.Restriction;
import com.addiscode.dao.zeraf.database.criteria.AndCriteria;
import com.addiscode.dao.zeraf.database.criteria.SingleCriteria;

public class CriteriaTest {
	SingleCriteria criteria;
	
	@Before
	public void setCriteria() {
		this.criteria = new SingleCriteria();
	}
	
	@Test
	public void testAddAnd() {
		criteria.addAnd(Restriction.eq("col1", "val1")).addOr(Restriction.like("col2", "val2"));
		assertTrue(true);
	}

	@Test(expected=InvalidInputException.class)
	public void testOr() throws InvalidInputException {
		criteria.addAnd(Restriction.eq("col1", "val1")).addOr(Restriction.like("col2", "val2"));
		SingleCriteria secondCriteria = new SingleCriteria(Restriction.like("col3", "val3"), Restriction.lte("col4", "val4"));
		AndCriteria andCriteria = new AndCriteria();
		andCriteria.setFirstCriteria(criteria);
		andCriteria.setSecondCriteria(secondCriteria);
		
		//must throw exception
		andCriteria.setFirstCriteria(andCriteria);
		
		System.out.println(andCriteria.getQueryString());
		assertTrue(true);
	}



}
