package hr.fer.oprpp1.hw04.db;

/**
 * Klasa predstavlja implementaciju leksičkog analizatora koji se koristi u svrhu tokenizacije korisničkog upita.
 * 
 * @author borna
 *
 */
public class QueryLexer {
	
	private char[] data; 
	private Token token; 
	private int currentIndex; 
	
	/**
	 * Konstruktor koji kao parametar prima korisnički upit nad kojim provodi tokenizaciju.
	 * 
	 * @param text ulazni tekst koji se tokenizira
	 */
	public QueryLexer(String query) {
		currentIndex = 0;
		data = query.toCharArray();
	}
	
	/**
	 * Metoda generira sljedeći token.
	 * 
	 * @return sljedeci token u upitu
	 * @throws IllegalStateException prilikom nedozvoljenog poziva metode
	 * @throws IllegalArgumentException ako je kao atribut uneseno bilo što osim jmbag, firstName ili lastName
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) throw new IllegalStateException();
		
		skipBlankSpaces();
		if (currentIndex >= data.length) {
			return addFinalToken();
		}
		
		if (data[currentIndex] == 'q') { //checking for a keyword "query"
			if (checkForKeyword()) {
				currentIndex += 5;
				skipBlankSpaces();
			}
		}

		if (Character.isLetter(data[currentIndex])) {
			if (checkForAND()) {
				currentIndex += 3;
				token = new Token(TokenType.AND, "AND");
				return token;
			}
			
			if (checkForLIKE()) {
				currentIndex += 4;
				token = new Token(TokenType.OPERATOR, ComparisonOperators.LIKE);
				return token;
			}
			
			int tokenStart = currentIndex;
			currentIndex++;
			
			while (Character.isLetter(data[currentIndex])) currentIndex++;
			
			int tokenEnd = currentIndex;
			String str = new String(data, tokenStart, tokenEnd-tokenStart);
			
			if (str.equals("jmbag")) {
				token =  new Token(TokenType.ATTRIBUTE, FieldValueGetters.JMBAG);
			} else if (str.equals("firstName")) {
				token =  new Token(TokenType.ATTRIBUTE, FieldValueGetters.FIRST_NAME);
			} else if (str.equals("lastName")) {
				token =  new Token(TokenType.ATTRIBUTE, FieldValueGetters.LAST_NAME);
			} else {
				throw new IllegalArgumentException();
			}
			return token;
			
		} else if (data[currentIndex] == '"' && currentIndex+1 < data.length) {
			currentIndex++;
			int tokenStart = currentIndex;
			
			while (data[currentIndex] != '"') currentIndex++;
			
			int tokenEnd = currentIndex;
			String str = new String(data, tokenStart, tokenEnd-tokenStart);
			currentIndex++;
			
			token =  new Token(TokenType.LITERAL, str);
			return token;
			
		} else {
			token =  new Token(TokenType.OPERATOR, findOperator());
			return token;
		}
	}
	
	/**
	 * Metoda vraca posljednji generirani token (moguce ju je pozivati vise puta). 
	 * Pozivom metode <code>getToken()</code> ne pokrece se generiranje sljedeceg tokena.
	 * 
	 * @return posljednji generirani token
	 */
	public Token getToken() {
		return token;
	}
	
	/**
	 * Metoda provjerava je li sljedeći token kojega lekser generira operator LIKE.
	 * 
	 * @return <code>true</code> ako je iduci token operator LIKE, <code>false</code> u protivnom
	 */
	private boolean checkForLIKE() {
		String s = new String (data, currentIndex, 4);
		return (s.equals("LIKE"));
	}

	/**
	 * Metoda pronalazi operator.
	 * 
	 * @return objekt tipa <code>IComparisonOperator</code>
	 * @throws IllegalArgumentException ukoliko je predan nedozvoljeni operator
	 */
	private IComparisonOperator findOperator() {
		currentIndex++;
		
		switch(data[currentIndex-1]) {
			case '=' : return ComparisonOperators.EQUALS;
			case '>' : {
				if (data[currentIndex] == '=') {
					currentIndex++;
					return ComparisonOperators.GREATER_OR_EQUALS;
				}
				return ComparisonOperators.GREATER;
			}
			case '<' : {
				if (data[currentIndex] == '=') {
					currentIndex++;
					return ComparisonOperators.LESS_OR_EQUALS;
				}
				return ComparisonOperators.LESS;
			}
			case '!' : {
				if (data[currentIndex] == '=') {
					currentIndex++;
					return ComparisonOperators.NOT_EQUALS;
				}
			}
			default : throw new IllegalArgumentException("Nedozvoljeni operator!");
		}
	}

	/**
	 * Metoda provjerava je li sljedeći token logički operator AND (logički operator je "case insensitive").
	 * 
	 * @return <code>true</code> ako je iduci token logički operator AND, <code>false</code> u protivnom
	 */
	private boolean checkForAND() {
		String s = new String (data, currentIndex, 3).toUpperCase();
		return (s.equals("AND"));
	}
	
	/**
	 * Metoda provjerava sadrži li predani string ključnu riječ "query".
	 * 
	 * @return <code>true</code> ako je iduci token ključna riječ "query", <code>false</code> u protivnom
	 */
	private boolean checkForKeyword() {
		String s = new String (data, currentIndex, 5);
		return (s.equals("query"));
	}

	/**
	 * Metoda generira token tipa <code>EOF</code> kao posljednji token predanog upita.
	 * 
	 * @return token tipa <code>EOF</code>
	 */
	private Token addFinalToken() {
		token = new Token(TokenType.EOF, null);
		return token;
	}
	
	/**
	 * Metoda omogućuje preskakanje prazina koje se nalaze unutar upita.
	 */
	private void skipBlankSpaces() {
		while(currentIndex < data.length) {
			char c = data[currentIndex];
			if (c == '\r' || c == '\n' || c == '\t' || c == ' ') {
				currentIndex++;
				continue;
			}
			break;
		}
	}
}
