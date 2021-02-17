package hr.fer.oprpp1.hw04.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Klasa omogućava filtriranje studentskih podataka na temelju korisničkog upita. Implementira sučelje <code>IFilter</code>.
 *  
 * @author borna
 *
 */
public class QueryFilter implements IFilter {
	
	private List<ConditionalExpression> query;
	
	/**
	 * Konstruktor koji kao parametar prima listu tipa <code>ConditionalExpression</code> koja sadrži korisnički upit.
	 * 
	 * @param usersQuery lista tipa <code>ConditionalExpression</code> koja sadrži korisnički upit
	 */
	public QueryFilter(List<ConditionalExpression> usersQuery) {
		query = new ArrayList<>(usersQuery);
	}
	
	/**
	 * Metoda obavlja provjeru nad predanim parametrom tipa <code>StudentRecord</code> i u skladu s rezultatom provjere vraća boolean vrijednost <code>true</code> ili <code>false</code>.
	 * Provjera se obavlja na način da se nad svakim operatorom predanog upita poziva metoda <code>satisfied</code> sa literalom i odgovarajućim podatkom kao parametrima.
	 * 
	 * @param record studentovi podatci
	 * @return <code>true</code> ako studentovi podatci zadovoljavaju uvjet, <code>false</code> u protivnom
	 */
	@Override
	public boolean accepts(StudentRecord record) {
		
		for (int i = 0; i < query.size(); i++) {
			ConditionalExpression expression = query.get(i);
			if (!expression.getOperator().satisfied(expression.getValue().get(record), expression.getLiteral())) return false;
		}
		return true;
	}

}
