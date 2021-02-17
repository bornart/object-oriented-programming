package hr.fer.oprpp1.hw02.prob1;

/**
 * Klasa kojom je definiran jednostavni leksicki analizator. Ova klasa predstavlja podsustav za izradu leksicke analize.
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
		
		if (currentState == LexerState.EXTENDED) {
			skipBlankSpaces(); //dodao
			if (currentIndex < data.length) {
				if (data[currentIndex] == '#') {
					token = new Token(TokenType.SYMBOL, data[currentIndex]);
					currentIndex++; 
					return token;
				}
			}
			
			//skipBlankSpaces();
			
			if (currentIndex >= data.length) {
				return addFinalToken();
			}
			
			String str = tokenIndex(this, new IToken() {
				@Override
				public boolean findToken() {
					return data[currentIndex] != '#' && data[currentIndex] != ' ';
				}
			});
			token = new Token(TokenType.WORD, str);
			return token;

		}

		skipBlankSpaces();

		if (currentIndex >= data.length) {
			return addFinalToken();
		}
		
		if (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\') {	
			if (data[currentIndex] == '\\') {
				if (currentIndex == data.length-1 || Character.isLetter(data[currentIndex+1])) throw new LexerException("Neispravno koristenje escape mehanizma!");
				else currentIndex++;
			}
			
			String str = "";
			str += data[currentIndex];
			currentIndex++;
			
			while((currentIndex < data.length && (Character.isLetter(data[currentIndex]) || data[currentIndex] == '\\'))) {
				
				if(!(data[currentIndex] == '\\')) {
					str += data[currentIndex];
				} else {
					if (Character.isLetter(data[currentIndex+1])) throw new LexerException("Neispravno koristenje escape mehanizma!");
					str += data[currentIndex+1];
					currentIndex++;
				}
					
				currentIndex++;
			}

			token = new Token(TokenType.WORD, str);
			
		} else if (Character.isDigit(data[currentIndex])) {
			
			String str = tokenIndex(this, new IToken() {
				@Override
				public boolean findToken() {
					return currentIndex < data.length && Character.isDigit(data[currentIndex]);
				}
			});
			
			try {
				long num = Long.parseLong(str);
				token = new Token(TokenType.NUMBER, num);
			} catch(NumberFormatException e) {
				throw new LexerException("Broj nije prikaziv u tipu Long!");
			}
			
		} else {
			token = new Token(TokenType.SYMBOL, data[currentIndex]); //1 token = 1 simbol!
			currentIndex++;
		}
		
		return token;
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
}