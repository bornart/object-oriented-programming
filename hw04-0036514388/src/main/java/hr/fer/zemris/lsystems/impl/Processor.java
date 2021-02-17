package hr.fer.zemris.lsystems.impl;

/**
 * Klasa Collection predstavlja konceptualnu poveznicu izmedu klijenata koji imaju svoje zahtjeve i pojedinacnih Processor-a koji mogu izvesti zadane operacije.
 * Svaki Processor ce biti definiran kao nova klasa koja implementira sucelje Processor.
 * 
 * @author borna
 *
 * @param <T> parametrizacija klase <code>Processor</code>
 */
public interface Processor<T>{
	/**
	 * Metoda obavlja odredenu operaciju nad predanim objektom.
	 * 
	 * @param value objekt nad kojim se izvodi zadana operacija.
	 */
	void process(T value);
}
