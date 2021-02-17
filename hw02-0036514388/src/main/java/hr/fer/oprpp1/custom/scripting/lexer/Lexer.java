package hr.fer.oprpp1.custom.scripting.lexer;

/**
 * Klasa kojom je definiran slozeni leksicki analizator. Ova klasa predstavlja podsustav za izradu leksicke analize.
 * 
 * @author borna
 *
 */
public class Lexer {
	
	/**
	 * Privatno sucelje koje definira jednu metodu <code>findToken()</code>.
	 * 
	 * @author borna
	 *
	 */
	private interface IToken {
		/**
		 * Metoda definira uvjete pod kojima iduci znak u Stringu klasificiramo kao dio dotad izgeneriranog tokena.
		 * 
		 * @return <code>true</code> ukoliko je uvjet zadovoljen, <code>false</code> u protivnom
		 */
		boolean findToken();
	}
	
	private char[] data; // ulazni tekst
	private Token token; // trenutni token
	private int currentIndex; // indeks prvog neobrađenog znaka
	private LexerState currentState; //trenutni nacin rada Lexer-a
	
	/**
	 * Konstruktor koji kao parametar prima ulazni tekst nad kojim se provodi tokenizacija.
	 * 
	 * @param text ulazni tekst koji se tokenizira
	 */
	public Lexer(String text) {
		currentIndex = 0;
		data = text.toCharArray();
		currentState = LexerState.BASIC;
	}
	
	/**
	 * Metoda generira i vraca sljedeci token.
	 * 
	 * @return sljedeci izgenerirani token
	 * @throws LexerException ako dode do pogreske prilikom postupka tokenizacije
	 * @throws NumberFormatException ako broj nije prikaziv u tipu Long
	 */
	public Token nextToken() {
		if (token != null && token.getType() == TokenType.EOF) throw new LexerException("Nema dostupnih tokena!");
		
		skipBlankSpaces();
		if (currentIndex >= data.length) {
			return addFinalToken();
		}
		
		if (currentState == LexerState.BASIC) {
			if (data[currentIndex] == '{' && data[currentIndex+1] == '$') {
				token = new Token(TokenType.TEXT, "");
				return token;
			} else {
				String tokenStr = findText();
				token = new Token(TokenType.TEXT, tokenStr);
				return token;
			}
		} else {
			if (data[currentIndex] == '{' && data[currentIndex+1] == '$') {
				currentIndex += 2;
				skipBlankSpaces();
				
				if (endOfLoop()) {
					token = new Token(TokenType.END, "{$END$}");
					return token;
				}
				
				String tokenStr = tokenIndex(this, new IToken() {
					@Override
					public boolean findToken() {
						return currentIndex < data.length && data[currentIndex] != ' ' && data[currentIndex] != '\n' && data[currentIndex] != '\r' && data[currentIndex] != '\t';
					}
				}).toUpperCase();
				
				token = new Token(TokenType.TAG, tokenStr);
				return token;

			} else if (data[currentIndex] == '$' && data[currentIndex+1] == '}') {
				currentIndex += 2;
				token = new Token(TokenType.TAGEND, "$}");
				return token;
			} else {
				if (Character.isLetter(data[currentIndex])) {	
					
					String tokenStr = tokenIndex(this, new IToken() {
						@Override
						public boolean findToken() {
							return currentIndex < data.length && Character.isLetter(data[currentIndex]);
						}
					});
					
					token = new Token(TokenType.STRING, tokenStr);
					return token;
				} else if (Character.isDigit(data[currentIndex]) || Character.isDigit(data[currentIndex+1]) && data[currentIndex] == '-') {
					boolean isDecimal = false;
					
					int tokenStart = data[currentIndex-1] == '-' ? currentIndex-1 : currentIndex;
					currentIndex++;
					
					while (currentIndex < data.length && Character.isDigit(data[currentIndex]) || currentIndex < data.length && data[currentIndex] == '.') {
						if (data[currentIndex] == '.') isDecimal = true;
						currentIndex++;
					}
					
					int tokenEnd = currentIndex;
					String tokenStr = new String(data, tokenStart, tokenEnd-tokenStart);
					
					try {
						if (isDecimal) {
							double num = Double.parseDouble(tokenStr);
							token = new Token(TokenType.DECIMAL, num);
						} else {
							int num = Integer.parseInt(tokenStr);
							token = new Token(TokenType.INTEGER, num);
						}
					} catch(NumberFormatException e) {
						throw new LexerException("Nevaljan prikaz broja!");
					}
					return token;
	
				} else if (data[currentIndex] == '"') {
					currentIndex++;
					if (data[currentIndex] == '\\') {
						if (currentIndex == data.length-1 || (data[currentIndex+1] != '"' && data[currentIndex+1] != '\\' && data[currentIndex+1] != 'n' && data[currentIndex+1] != 'r' && data[currentIndex+1] != 't')) throw new LexerException("Neispravno koristenje escape mehanizma!");
						if (data[currentIndex+1] == '\\' || data[currentIndex+1] == '"') currentIndex++;
					}
					
					String str = "";
					str += data[currentIndex];
					currentIndex++;
					
					while (currentIndex < data.length) {
						if (data[currentIndex] == '"' && data[currentIndex-1] != '\\') break;
						if(!(data[currentIndex] == '\\')) {
							str += data[currentIndex];
						} else {
							if (currentIndex == data.length-1 || (data[currentIndex+1] != '"' && data[currentIndex+1] != '\\' && data[currentIndex+1] != 'n' && data[currentIndex+1] != 'r' && data[currentIndex+1] != 't')) throw new LexerException("Neispravno koristenje escape mehanizma!");
							if (data[currentIndex+1] == '\\' || data[currentIndex+1] == '"'){
								str += data[currentIndex+1];
								currentIndex++;
							}
						}
						currentIndex++;
					}
					
					currentIndex++;
					token = new Token(TokenType.STRING, '"'+str+'"');
					return token;
					
				} else if (data[currentIndex] == '+' || data[currentIndex] == '-' || data[currentIndex] == '*' || data[currentIndex] == '/' || data[currentIndex] == '^') {
					if (data[currentIndex] == '-' && Character.isDigit(data[currentIndex+1])) {}
					token = new Token(TokenType.OPERATOR, data[currentIndex]);
					currentIndex++;
					return token;
				} else if (data[currentIndex] == '@') {
					currentIndex++;
					if (!Character.isLetter(data[currentIndex])) throw new LexerException("Neispravno zadana funkcija!");
					String tokenStr = tokenIndex(this, new IToken() {
						@Override
						public boolean findToken() {
							return currentIndex < data.length && data[currentIndex] != ' ';
						}
					});
					if (!tokenStr.matches("^([\\w\\s*\\_])+$")) {
						throw new LexerException("Neispravno zadana funkcija!");
					}
					token = new Token(TokenType.FUNCTION, tokenStr);
					return token;
				} else {
					throw new LexerException();
				}
			}
		}
	}
	
	/**
	 * Metoda omogucava preskakanje praznina u Stringu.
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
	
	/**
	 * Metoda generira token tipa <code>EOF</code> kao posljednji token u obradi, nakon sto lexer grupira sve znakove iz ulaza i bude ponovno pozvan da obavi daljnje grupiranje.
	 * 
	 * @return novogenerirani token tipa <code>EOF</code>
	 */
	private Token addFinalToken() {
		token = new Token(TokenType.EOF, null);
		return token;
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
	 * Metoda kojom pozivatelj postavlja zeljeni nacin grupiranja (odabire jedan od dva moguca nacina rada Lexer-a).
	 * 
	 * @param state stanje rada u kojem se nalazi Lexer (<code>BASIC</code> ili <code>EXTENDED</code>)
	 * @throws NullPointerException ako je umjesto stanja lexera predana null-referenca
	 */
	public void setState(LexerState state) {
		if (state == null) throw new NullPointerException("Predana je null-referenca umjesto stanja lexera!");
		
		currentState = state;
	}
	
	/**
	 * Metoda provjerava radi li se o zavrsetku petlje (<code>{$END$}</code>).
	 * 
	 * @return <code>true</code> ako se radi o zavrsetku petlje, <code>false</code> u protivnom
	 */
	private boolean endOfLoop() {
		String s = new String (data, currentIndex, 3).toUpperCase();
		if (s.equals("END")) {
			currentIndex += 3;
			skipBlankSpaces();
			if (data[currentIndex] == '$' && data[currentIndex+1] == '}') {
				currentIndex += 2;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Metoda generira <code>String</code> koji predstavlja iduci token u teksutalnom obliku.
	 * 
	 * @param l referenca na trenutni Lexer
	 * @param t referenca na objekt koji implementira sucelje IToken
	 * @return <code>String</code> koji predstavlja iduci token
	 */
	private static String tokenIndex(Lexer l, IToken t) {
		int tokenStart = l.currentIndex;
		l.currentIndex++;
		
		while(t.findToken()) {
			l.currentIndex++;
		}
		
		int tokendEnd = l.currentIndex;
		return new String(l.data, tokenStart, tokendEnd-tokenStart);
	}
	
	/**
	 * Metoda koja pronalazi tekst koji se nalazi prije otvorenog TAG-a ("{$") ili izmedu dva zatvorenog i sljedeceg otvorenog TAG-a ("$}" i "{$") i vraca ga u obliku stringa.
	 * 
	 * @return tekst koji se nalazi izvan TAG-a
	 */
	private String findText() {
		int tokenStart = currentIndex;
		currentIndex++;
		
		while (currentIndex < data.length) {
			if (data[currentIndex] == '\\' ) {
				if (data[currentIndex+1] != '\\' && data[currentIndex+1] != '{') {
					throw new LexerException("Neispravna uporaba escape-a!");
				}
			}
			
			if (data[currentIndex] == '{' && data[currentIndex+1] == '$' && data[currentIndex-1] != '\\') break;
			currentIndex++;
		}
		
		int tokenEnd = currentIndex;
		return new String(data, tokenStart, tokenEnd-tokenStart);
	}
}
