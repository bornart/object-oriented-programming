package hr.fer.oprpp1.hw04.db;

/**
 * Klasa kojom je definiran token kao leksička jedinica.
 * Svaki token grupira jedan ili više uzastopnih znakova iz ulaznog stringa.
 * 
 * @author borna
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Konstruktor koji provodi inicijalizaciju tokena.
	 * 
	 * @param type TokenType enumeracija. Moguce vrijednosti su: ATTRIBUTE, OPERATOR, LITERAL, AND ili EOF
	 * @param value vrijednost tokena
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Metoda dohvaća vrijednost trenutnog tokena.
	 * 
	 * @return vrijednost trenutnog tokena
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Metoda dohvaća enumeraciju <code>TokenType</code> trenutnog tokena.
	 * 
	 * @return vrsta enumeracije trenutnog tokena.
	 */
	public TokenType getType() {
		return type;
	}
}
