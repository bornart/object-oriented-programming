package hr.fer.oprpp1.hw04.db;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class ConditionalExpressionTest {
	
	@Test
	public void testConditionalExpression() {
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 ComparisonOperators.LIKE,
				 "Bos*"
		);
		
		StudentRecord record = new StudentRecord("0000000001", "Akšamović", "Marin", 2);
		
		assertFalse(expr.getOperator().satisfied(expr.getValue().get(record), expr.getLiteral()));
	}
	
	@Test
	public void testConditionalExpression2() {
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 ComparisonOperators.LIKE,
				 "Bos*ić"
		);
		
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		
		assertTrue(expr.getOperator().satisfied(expr.getValue().get(record), expr.getLiteral()));
	}
	
	@Test
	public void testConditionalExpression3() {
		
		ConditionalExpression expr = new ConditionalExpression(
				 FieldValueGetters.LAST_NAME,
				 ComparisonOperators.LIKE,
				 "Bos*"
		);
		
		StudentRecord record = new StudentRecord("0000000003", "Bosnić", "Andrea", 4);
		
		assertTrue(expr.getOperator().satisfied(expr.getValue().get(record), expr.getLiteral()));
	}
}
