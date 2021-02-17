package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Klasa kojom je definiran token kao leksicka jedinicu koja grupira jedan ili vise uzastopnih znakova iz ulaznog teksta.
 * 
 * @author borna
 *
 */
public class Token {
	
	private TokenType type;
	private Object value;
	
	/**
	 * Konstruktor koji inicijalizira token.
	 * 
	 * @param type TokenType enumeracija. Moguce vrijednosti su: EOF, TEXT ili TAG
	 * @param value vrijednost tokena
	 */
	public Token(TokenType type, Object value) {
		this.type = type;
		this.value = value;
	}
	
	/**
	 * Metoda koja dohvaca vrijednost trenutnog tokena.
	 * 
	 * @return referenca na objekt koji je vrijednost trenutnog tokena
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * Metoda dohvaca vrijednost enumeracije TokenType.
	 * 
	 * @return vrsta enumeracije trenutnog tokena.
	 */
	public TokenType getType() {
		return type;
	}
}
