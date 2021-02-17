package hr.fer.zemris.java.gui.charts;

import java.awt.BorderLayout;
import java.awt.Container;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

/**
 * Razred izveden iz razreda <code>JFrame</code>, zadužen za prikaz jednog primjerka stupčastog dijagrama.
 * 
 * @author borna
 *
 */
public class BarChartDemo extends JFrame {
	/*
	 * UPUTA ZA POKRETANJE PROGRAMA BarChart:
	 * 
	 * Program se pokreće tako da se kao argument preda tekstualna datoteka 
	 * na temelju čijeg sadržaja se crta stupčasti dijagram. 
	 * U projektu je stvorena mapa "resources" u kojoj se nalazi datoteka "tablica.txt".
	 * 
	 * Prilikom pokretanja programa kao argument je potrebno predati sljedeći put do datoteke:
	 * src/main/resources/tablica.txt
	 */
	
	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor.
	 * 
	 * @param barchart objekt tipa <code>BarChart</code>
	 * @param path putanja do datoteke iz koje su podatci dohvaćeni
	 */
	public BarChartDemo(BarChart barchart, Path path) {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setTitle("Bar chart");
		setSize(500, 500);
		initGUI(barchart, path);
	}
	
	/**
	 * Inicijalizacija grafičkog korisničkog sučelja.
	 * 
	 * @param barchart objekt tipa <code>BarChart</code>
	 * @param path putanja do datoteke iz koje su podatci dohvaćeni
	 */
	private void initGUI(BarChart barchart, Path path) {
		Container cp = getContentPane();
		cp.setLayout(new BorderLayout());
		
		JLabel label = new JLabel();
		label.setText(path.toString());
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		cp.add(label, BorderLayout.PAGE_START);
		cp.add(new BarChartComponent(barchart), BorderLayout.CENTER);
	}
	
	
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Program mora primiti točno jedan argument!");
			System.exit(0);
		}
		
		Path path = Paths.get(args[0]);
		File file = path.toFile();
		if (!file.isFile()) throw new IllegalArgumentException("Predani argument mora biti staza do datoteke!");
		
		List<String> list = new ArrayList<>(readFile(file.toPath(), 6));
		if (list.size() != 6) {
			System.out.println("Dogodila se pogreška prilikom čitanja datoteke.");
			System.exit(0);
		}
		
		List<XYValue> values = parse(list.get(2));
		
		BarChart bc = new BarChart(values, list.get(0), list.get(1), Integer.parseInt(list.get(3)), Integer.parseInt(list.get(4)), Integer.parseInt(list.get(5)));
		
		SwingUtilities.invokeLater(()->{
			new BarChartDemo(bc, path).setVisible(true);
		});
	}
	
	/**
	 * Metoda parsira redak datoteke koji sadrži niz točaka u listu tipa <code>XYValue</code>.
	 * 
	 * @param line redak datoteke
	 * @return lista tipa <code>XYValue</code>
	 */
	private static List<XYValue> parse(String line) {
		List<XYValue> values = new ArrayList<>();
		String[] arr = line.split("\\s+");
		for (int i = 0, size = arr.length; i < size; i++) {
			String[] elem = arr[i].split(",");
			values.add(new XYValue(Integer.parseInt(elem[0]), Integer.parseInt(elem[1])));
		}
		return values;
	}

	/**
	 * Metoda koja čita prvih <code>numOfLines</code> redaka predane datoteke.
	 * 
	 * @param path staza do datoteke
	 * @param numOfLines broj redaka koje je potrebno pročitati iz datoteke (počevši od prvog)
	 * @return lista Stringova koja predstavlja prvih <code>numOfLines</code> redaka predane datoteke
	 */
	private static List<String> readFile(Path path, int numOfLines) {
		try (Stream<String> lines = Files.lines(path)) {
			return lines.limit(numOfLines).collect(Collectors.toList());
		} catch(IOException e) {
			return null;
		}
	}
}
