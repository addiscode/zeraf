package com.addiscode.dao.zeraf.database.criteria;

import org.eclipse.jdt.core.compiler.InvalidInputException;

public abstract class AbstractNestedCriteria {
	protected Criteria firstCriteria = null;
	protected Criteria secondCriteria = null;

	public Criteria getFirstCriteria() {
		return firstCriteria;
	}

	/**
	 * Checks input to prevent possible stackoverflow at runtime
	 * 
	 * @param criteria
	 * @throws InvalidInputException
	 */
	private void checkSelfRef(Criteria criteria) throws InvalidInputException {
		
		if (this == criteria)
			throw new InvalidInputException(
					"You cann't give self as a reference");

	}

	public void setFirstCriteria(Criteria firstCriteria)
			throws InvalidInputException {
		checkSelfRef(firstCriteria);
		this.firstCriteria = firstCriteria;
	}

	public Criteria getSecondCriteria() {
		return secondCriteria;
	}

	public void setSecondCriteria(Criteria secondCriteria)
			throws InvalidInputException {
		checkSelfRef(firstCriteria);
		this.secondCriteria = secondCriteria;
	}
}
