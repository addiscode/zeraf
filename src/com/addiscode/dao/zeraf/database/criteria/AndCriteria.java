package com.addiscode.dao.zeraf.database.criteria;


public final class AndCriteria extends AbstractNestedCriteria implements Criteria {
	

	@Override
	public String getQueryString() {
		StringBuilder builder = new StringBuilder();
		
		if (firstCriteria != null && secondCriteria != null)
			builder.append("( ");
		
		if (firstCriteria != null)
			builder.append(firstCriteria.getQueryString());

		if (firstCriteria != null && secondCriteria != null)
			builder.append(" AND ");

		if (secondCriteria != null)
			builder.append(secondCriteria.getQueryString());

		if (firstCriteria != null && secondCriteria != null)
			builder.append(" )");
		
		return builder.toString();
	}

}
