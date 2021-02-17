package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Enumeracija cije su moguce vrijednosti END, EOF, TEXT, STRING, INTEGER, DECIMAL, OPERATOR, FUNCTION, TAGEND i TAG. 
 * 
 * @author borna
 *
 */
public enum TokenType {
	TAG,
	TAGEND,
	END,
	TEXT,
	STRING,
	INTEGER,
	DECIMAL,
	OPERATOR,
	FUNCTION,
	EOF;
}
