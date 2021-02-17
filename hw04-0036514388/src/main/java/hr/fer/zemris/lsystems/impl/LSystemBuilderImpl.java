package hr.fer.zemris.lsystems.impl;

import java.awt.Color;
import java.util.Scanner;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.commands.ColorCommand;
import hr.fer.zemris.lsystems.impl.commands.DrawCommand;
import hr.fer.zemris.lsystems.impl.commands.PopCommand;
import hr.fer.zemris.lsystems.impl.commands.PushCommand;
import hr.fer.zemris.lsystems.impl.commands.RotateCommand;
import hr.fer.zemris.lsystems.impl.commands.ScaleCommand;
import hr.fer.zemris.lsystems.impl.commands.SkipCommand;

/**
 * Klasa predstavlja implementaciju izgranje Lindermayerovih sustava.
 * Implementira sučelje LSystemBuilder.
 * 
 * @author borna
 *
 */
public class LSystemBuilderImpl implements LSystemBuilder {
	
	private Dictionary<String, String> productions;
	private Dictionary<String, Command> commands;
	private double unitLength;
	private double unitLengthDegreeScaler;
	private Vector2D origin;
	private double angle;
	private String axiom;
	
	
	/**
	 * Pretpostavljeni konstruktor kojim se postavljaju pretpostavljene vrijednosti privatnih članskih varijabli.
	 */
	public LSystemBuilderImpl() {
		productions = new Dictionary<>();
		commands = new Dictionary<>();
		unitLength = 0.1;
		unitLengthDegreeScaler = 1;
		origin = new Vector2D(0, 0);
		angle = 0.;
		axiom = "";
	}
	
	/**
	 * Ugniježđena klasa koja implementira sučelje <code>LSystem</code>.
	 * Omogućava crtanje Lindermayerovih sustava kao i generiranje produkcija do n-te razine (n <= 6).
	 * 
	 * @author borna
	 *
	 */
	private class LSystemClass implements LSystem {
		
		/**
		 * Metoda uporabom primljenog objekta za crtanje <code>painter</code> koji je tipa <code>Painter</code> crta rezultantni fraktal pozivajući metodu <code>execute</code> za svaki generirani simbol produkcije.
		 * 
		 *  @param level razina za koju je potrebno izgenerirati produkcije
		 *  @param painter objekt za crtanje tipa <code>Painter</code>
		 */
		@Override
		public void draw(int level, Painter painter) {
			Context newContext = new Context();
			
			Color defaultColor = Color.BLACK;
			double currentEffectiveLength = unitLength * Math.pow(unitLengthDegreeScaler, level);
			
			TurtleState newState = new TurtleState(origin, new Vector2D(1, 0).rotated(angle), defaultColor, currentEffectiveLength);
			newContext.pushState(newState);
			
			String generatedSequence = generate(level);
			char[] c = generatedSequence.toCharArray();
			for (int i = 0; i < c.length; i++) {
				Command command = commands.get(String.valueOf(c[i]));
				if (command != null) {
					command.execute(newContext, painter);
				}
			}
		}
		
		/**
		 *  Metoda prima razinu i vraća string koji odgovara generiranom nizu nakon zadanog broja razina primjena produkcija.
		 * 
		 * @param level razina za koju je potrebno izgenerirati produkcije
		 * @return string koji odgovara generiranom nizu produkcija
		 */
		@Override
		public String generate(int level) {
			if (level == 0) {
				return axiom;
			}

			String s = axiom;
			for (int i = 0; i < level; i++) {
				char[] symbols = s.toCharArray();
				String novi = "";
				for (int j = 0; j < symbols.length; j++) {
					char symbol = symbols[j];
					String production = productions.get(String.valueOf(symbol)) != null ? productions.get(String.valueOf(symbol)) : "";
					if (production.equals("")) {
						novi += symbol;
					} else {
						novi += production;
					}
				}
				s = novi;
			}
			return s;
		}
	}
	
	/**
	 * Metoda stvara se jedan konkretan Lindermayerov sustav, <code>LSystem</code>.
	 * 
	 * @return primjerak ugniježđenog razreda <code>LSystemClass</code> koji implementira sučelje LSystem
	 */
	@Override
	public LSystem build() {
		return new LSystemClass();
	}
	
	/**
	 * Metoda na temelju ulaznog polja stringova stvara objekte za konfiguriranje Lindermayerovih sustava.
	 * Konfiguracija podrazumijeva da svaka linija sadrži po jednu direktivu (ili je prazna).
	 * 
	 * @param polje stringova ciji svaki pojedini redak predstavlja po jedan objekt za konfiguriranje Lindermayerovih sustava
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 * @throws IllegalArgumentException ako je zadana nepodržana direktiva
	 * @throws NoSuchElementException ako Scanner pokuša pročitati nepostojeći element
	 */
	@Override
	public LSystemBuilder configureFromText(String[] lines) {
		
		for (int i = 0; i < lines.length; i++) {
			Scanner sc = new Scanner(lines[i]);
			
			if (sc.hasNext()) {
				
				String str = sc.next();
				if (str.equals("origin")) {
					double x = sc.nextDouble();
					double y = sc.nextDouble();
					setOrigin(x, y);
				} else if (str.equals("angle")) {
					setAngle(sc.nextDouble());
				} else if (str.equals("unitLength")) {
					setUnitLength(sc.nextDouble());
				} else if (str.equals("unitLengthDegreeScaler")) {
					double value1 = sc.nextDouble();
					sc.reset();
					sc.useDelimiter("\\s*/\\s*");

					if (sc.hasNext()) {
						double value2 = sc.nextDouble();
						double value = value1/value2;
						setUnitLengthDegreeScaler(value);
					} else {
						setUnitLengthDegreeScaler(value1);
					}
				} else if (str.equals("command")) {
					String s = sc.next();
					char sym = s.charAt(0);
					String com;
					if (s.equals("[") || s.equals("]")) {
						com = sc.next();
					} else {
						com = sc.next() + " " + sc.next();
					}
					registerCommand(sym, com);
				} else if (str.equals("axiom")) {
					setAxiom(sc.next());
				} else if (str.equals("production")) {
					char sym = sc.next().charAt(0);
					String prod = sc.next();
					registerProduction(sym, prod);
				} else {
					sc.close();
					throw new IllegalArgumentException("Nevaljana direktiva!");
				}
			}
			sc.close();
		}
		return this;
	}
	
	/**
	 * Metoda u rječnik naredbi dodaje novu naredbu u obliku (ključ, vrijednost) gdje je ključ jedan znak produkcije, a vrijednost objekt tipa <code>Command</code>.
	 * 
	 * @param symbol jedan znak izgenerirane produkcije 
	 * @param action string koji predstavlja naredbu pridruženu simbolu <code>symbol</code>
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 */
	@Override
	public LSystemBuilder registerCommand(char symbol, String action) {
		Command command;
		if (action.equals("push") || action.equals("pop")) {
			command = findCommand(action, "");
		} else {
			String[] elems = action.split(" ");
			command = findCommand(elems[0], elems[1]);
		}
		
		commands.put(String.valueOf(symbol), command);
		return this;
	}
	
	/**
	 * Metoda u rječnik produkcija dodaje novu produkciju u obliku (ključ, vrijednost) gdje je ključ jedan znak produkcije (<code>symbol</code>), a vrijednost produkcijsko pravilo za predani <code>symbol</code>.
	 * 
	 * @param symbol jedan znak izgenerirane produkcije 
	 * @param production string koji predstavlja produkcijsko pravilo za predani <code>symbol</code>
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 */
	@Override
	public LSystemBuilder registerProduction(char symbol, String production) {
		productions.put(String.valueOf(symbol),  production);
		
		return this;
	}
	
	/**
	 * Metoda postavlja kut prema pozitivnoj osi-x smjera u kojem kornjača gleda.
	 * 
	 * @param angle kut rotacije vektora
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 */
	@Override
	public LSystemBuilder setAngle(double angle) {
		this.angle = Math.toRadians(angle);
		
		return this;
	}
	
	/**
	 * Metoda postavlja početni niz iz kojeg kreće razvoj sustava; može biti samo jedan simbol ali može biti niz).
	 * 
	 * @param axiom početni niz
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 */
	@Override
	public LSystemBuilder setAxiom(String axiom) {
		this.axiom = axiom;
		
		return this;
	}
	
	/**
	 * Metoda zadaje točku iz koje kornjača kreće.
	 * 
	 * @param x komponenta uz jedinicni vektor <code>i</code>
	 * @param y komponenta uz jedinicni vektor <code>j</code>
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 */
	@Override
	public LSystemBuilder setOrigin(double x, double y) {
		this.origin = new Vector2D(x, y);
		
		return this;
	}

	/**
	 * Metoda postavlja decimalnu vrijednost koja predstavlja koliko je dugačak jedinični pomak kornjače.
	 * 
	 * @param unitLength duljina jediničnog pomaka kornjače
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 */
	@Override
	public LSystemBuilder setUnitLength(double unitLength) {
		this.unitLength = unitLength;
		
		return this;
	}
	
	/**
	 * Metoda koja postavlja početnu efektivnu duljinu koraka za kornjaču u svrhu zadržavanja dimenzija prikazanog fraktala konstantnima.
	 * 
	 * @param unitLengthDegreeScaler početna efektivna duljina koraka
	 * @return trenutni objekt tipa <code>LSystemBuilder</code>
	 */
	@Override
	public LSystemBuilder setUnitLengthDegreeScaler(double unitLengthDegreeScaler) {
		this.unitLengthDegreeScaler = unitLengthDegreeScaler;
		
		return this;
	}
	
	/**
	 * Metoda pronalazi zadanu naredbu na temelju prvog predanog parametra <code>command</code> i stvara primjerak razreda pridružen odgovarajućoj naredbi.
	 * 
	 * @param command naredba 
	 * @param action vrijednost koju je (eventualno) potrebno predati konstruktoru prilikom stvaranja primjerka odgovarajućeg razreda
	 * @return objekt tipa <code>Command</code> koji predstavlja primjerak novostvorenog primjerka razreda koji nasljeđuje sučelje <code>Command</code>
	 * @throws IllegalArgumentException ako je zadana nepodržana naredba
	 */
	private Command findCommand(String command, String action) {
		switch(command) {
			case "draw": return new DrawCommand(Double.parseDouble(action));
			case "rotate": return new RotateCommand(Double.parseDouble(action));
			case "skip" : return new SkipCommand(Double.parseDouble(action));
			case "scale": return new ScaleCommand(Double.parseDouble(action));
			case "push": return new PushCommand();
			case "pop": return new PopCommand();
			case "color": return new ColorCommand(Color.decode("#"+action));
			default: throw new IllegalArgumentException("Zadana je nevaljana naredba!");
		}
	}
}
