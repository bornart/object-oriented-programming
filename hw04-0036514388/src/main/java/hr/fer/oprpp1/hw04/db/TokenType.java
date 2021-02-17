package hr.fer.oprpp1.hw04.db;

/**
 * Enumeracija čije su moguce vrijednosti ATTRIBUTE, OPERATOR, LITERAL, AND ili EOF. 
 * 
 * @author borna
 *
 */
public enum TokenType {
	ATTRIBUTE, 
	OPERATOR, 
	LITERAL,
	AND,
	EOF;
}
