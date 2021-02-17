package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa implementira parsiranje upita koje se provode nad bazom podataka.
 * 
 * @author borna
 *
 */
public class QueryParser {

	private List<ConditionalExpression> query;
	private QueryLexer lexer;
	
	/**
	 * Konstruktor koji kao parametar prima korisnikov upit.
	 * Poziva metodu <code>parse</code> koja provodi postupak parsiranja.
	 * 
	 * @param query korisnički upit u obliku stringa (sve što je korisnik unio iza ključne riječi <code>query</code>)
	 */
	public QueryParser(String userQuery) {
		query = new ArrayList<>();
		lexer = new QueryLexer(userQuery);
		
		parse();
	}
	
	/**
	 * Metoda provodi postupak parsiranja.
	 * 
	 * @throws IllegalArgumentException ukoliko se u postupku parsiranja pojavi nedozvoljeni token
	 * @throws IllegalStateException ako se metoda <code>nextToken</code> pozove nad nepostojećim tokenom
	 */
	private void parse() {
		while (lexer.nextToken().getType() != TokenType.EOF) {
			Token token = lexer.getToken();

			if (token.getType() == TokenType.ATTRIBUTE) {
				
				IFieldValueGetter attribute = (IFieldValueGetter)token.getValue();
				IComparisonOperator operator = (IComparisonOperator)lexer.nextToken().getValue();
				String literal = (String)lexer.nextToken().getValue();
				
				//additional check for wildcard used with operator "LIKE":
				String s = literal;
				if (operator == ComparisonOperators.LIKE && (s.length() - s.replace("*", "").length()) > 1) {
					System.err.println("Dozvoljena je uporaba samo jednog wildcard znaka!");
				}
				
				query.add(new ConditionalExpression(attribute, operator, literal));
			} else if (token.getType() == TokenType.AND) {
				continue;
			} else {
				throw new IllegalArgumentException("Nedozvoljeni token!");
			}
		}
	}

	/**
	 * Metoda provjera spada li korisnički upit u tzv. "direktne upite". 
	 * Direktni upit je upit koji se sastoji od isključivo jedne usporedbe, atributa <code>jmbag</code> i operatora <code>EQUALS</code>.
	 * 
	 * @return <code>true</code> ako je upit direktan, <code>false</code> u protivnom
	 */
	public boolean isDirectQuery() {
		return query.size() == 1 && query.get(0).getOperator() == ComparisonOperators.EQUALS && query.get(0).getValue() == FieldValueGetters.JMBAG;
	}
	
	/**
	 * Metoda vraća jmbag (u obliku stringa) koji je predan u direktnom upitu. 
	 * 
	 * @return studentov jmbag u obliku stringa
	 * @throws IllegalStateException ako upit nad kojim je pozvana metoda <code>getQueriedJMBAG</code> nije direktan
	 */
	public String getQueriedJMBAG() {
		if (!isDirectQuery()) throw new IllegalStateException("Radi se o direktnom upitu!");
		
		return query.get(0).getLiteral();
	}
	
	/**
	 * Metoda za svaki upit vraća listu kondicionalnih izraza.
	 * 
	 * @return objekt tipa <code>ConditionalExpression</code> koji sadrži sve kondicionalne izraze u upitu
	 */
	public List<ConditionalExpression> getQuery() {
		return query;
	}
}
