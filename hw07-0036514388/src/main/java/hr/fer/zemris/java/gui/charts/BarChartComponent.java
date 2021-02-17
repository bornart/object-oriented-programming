package hr.fer.zemris.java.gui.charts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;

import javax.swing.JComponent;

/**
 * Razred zadužen za iscrtavanje stupčastog dijagrama.
 * 
 * @author borna
 *
 */
public class BarChartComponent extends JComponent{
	
	private static final int FIXED_SPACE = 20;
	private static final int EDGE = 20;
	
	private BarChart chart;
	private int width;
	private int height;

	/**
	 * Pretpostavljeni <code>serialVersionUID</code>.
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Konstruktor; prima referencu na objekt tipa <code>BarChart</code>.
	 * 
	 * @param chart objekt tipa <code>BarChart</code
	 */
	public BarChartComponent(BarChart chart) {
		this.chart = chart;
	}
	
	
	@Override
	protected void paintComponent(Graphics g) {
		FontMetrics fm = g.getFontMetrics();
		int fontHeight = fm.getHeight();
		
		//ovo je vazno zbog udaljenosti y-osi od lijevog ruba ekrana: ta udaljenost ovisi o sirini brojki koje su pridruzene koordinatnoj osi!
		int largestNumber = fm.stringWidth(Integer.toString(chart.getMaxY()));
		
		int widthOfDescX = g.getFontMetrics().stringWidth(chart.getDescX()); 
		int widthOfDescY = g.getFontMetrics().stringWidth(chart.getDescY()); 
		
		comuputeSize(fontHeight, largestNumber);
		
		Graphics2D g2 = (Graphics2D) g;
		
		drawColumns(g2, fontHeight);
		
		drawYMeasures(g2);
		
		drawAxes(g2, fontHeight, widthOfDescX, widthOfDescY);
	}
	
	/**
	 * Metoda iscrtava vrijednosti na y-osi.<p>
	 * Vrijednosti koje se ispisuju zadane su razmakom u datoteci.
	 * 
	 * @param g2 Objekt tipa <code>Graphics2D</code>
	 */
	private void drawYMeasures(Graphics2D g2) {
		FontMetrics fm = g2.getFontMetrics();
		int ascent = fm.getAscent();
		
		int min = chart.getMinY();
		double len = height; //-EDGE
		
		int heightGap = (height-EDGE) / (chart.getMaxY()/chart.getGap());
		if (heightGap <= 0) heightGap = 1;
		
		for (double i = 0; i < len; i += heightGap) {
			if (i > height - EDGE || min > chart.getMaxY()) break;
			
			int size = g2.getFontMetrics().stringWidth(Integer.toString(min));
        	g2.drawString(Integer.toString(min), getWidth()-width-4-size, (int)(height-i) + ascent/2-2);
        	min += chart.getGap();
        }
        
	}

	/**
	 * Metoda iscrtava koordinatne osi.
	 * 
	 * @param g2 Objekt tipa <code>Graphics2D</code>
	 * @param fontHeight visina fonta
	 * @param widthOfDescX širina opisa na x-osi
	 * @param widthOfDescY širina opisa na y-osi
	 */
	private void drawAxes(Graphics2D g2, int fontHeight, int widthOfDescX, int widthOfDescY) {
		//iscrtavanje opisa koordinatnih osi:
		g2.drawString(chart.getDescX(), getWidth()-width/2 - widthOfDescX/2, height+FIXED_SPACE+ 2*fontHeight);
		
		Font originalFont = g2.getFont();
		int size = originalFont.getSize();
		/*
		 * NAPOMENA: Primjetio sam da se u ovisnosti o veličini ekrana na kojem pokrećem program razlikuje font varijable descY.
		 * Bez promjene fonta na većem ekranu (npr monitor) dobijam dobar ispis, ali na ekranu laptopa potrebna su "namještanja"?
		 * Ovdje navodim rješenje koje odgovara manjem ekranu.
		 */
		g2.setFont(new Font(originalFont.getName(), Font.BOLD, size+4));
		
		AffineTransform original = g2.getTransform();
		AffineTransform at = new AffineTransform();
		at.rotate(-Math.PI / 2);
		g2.setTransform(at);
		
		/*
		 * -(EDGE + height/2 + widthOfDescY/2) -> ista stvar kao sa fontom... -> ovo bi trebalo dobro izgledati
		 */
		g2.drawString(chart.getDescY(), -(getHeight() - height/2 + EDGE + widthOfDescY), FIXED_SPACE/2 + 2 * fontHeight);
		g2.setTransform(original);
		g2.setFont(originalFont);
		
		//NAPOMENA: ishodiste koordinatnog sustava se nalazi u tocki (getWidth() - width, height)
		 //iscrtavanje x-osi:
		drawArrow(g2, getWidth() - width, height, getWidth()-EDGE/4, height);
		//iscrtavanje y-osi:
		drawArrow(g2, getWidth() - width, height, getWidth() - width, 0);
	}
	
	/**
	 * Metoda iscrtava "strelice" koje predstavljaju koordinatne osi.
	 * 
	 * @param g2 Objekt tipa <code>Graphics2D</code>
	 * @param x1 početna koordinata x
	 * @param y1 početna koordinata y
	 * @param x2 završna koordinata x
	 * @param y2 završna koordinata y
	 */
	private void drawArrow(Graphics2D g2, int x1, int y1, int x2, int y2) {
		int d = 5, h = 5;
		
		int dx = x2 - x1, dy = y2 - y1;
	    double D = Math.sqrt(dx*dx + dy*dy);
	    double xm = D - d, xn = xm, ym = h, yn = -h, x;
	    double sin = dy / D, cos = dx / D;

	    x = xm*cos - ym*sin + x1;
	    ym = xm*sin + ym*cos + y1;
	    xm = x;

	    x = xn*cos - yn*sin + x1;
	    yn = xn*sin + yn*cos + y1;
	    xn = x;

	    int[] xpoints = {x2, (int) xm, (int) xn};
	    int[] ypoints = {y2, (int) ym, (int) yn};

	    g2.drawLine(x1, y1, x2, y2);
	    g2.fillPolygon(xpoints, ypoints, 3);
	    
	    //dodatno, crtanje "crtica" na koordinatnim osima:
	    AffineTransform original = g2.getTransform();
        double len = Math.sqrt(dx*dx + dy*dy);
        
        AffineTransform at = AffineTransform.getTranslateInstance(x1, y1);
        at.concatenate(AffineTransform.getRotateInstance(Math.atan2(dy, dx)));
        g2.transform(at);
        
        //(height-EDGE) / (chart.getMaxY()/chart.getGap())
        double axisGapD = y2 == 0 ? ((double)(height-EDGE) / chart.getMaxY() * chart.getGap()) : (width-EDGE)/chart.getList().size();

        int axisGap = (int) axisGapD;
        if (axisGap <= 0) axisGap = 1;
        
        for (int i = 0; i < len; i += axisGap) {
        	if (y2 == 0) {
        		if (i > height - EDGE) break;
        	}
        	g2.drawLine(i, -4, i, 4);
        }
        g2.setTransform(original);
	}

	/**
	 * Metoda iscrtava stupce stupčastog dijagrama.
	 * 
	 * @param g2 Objekt tipa <code>Graphics2D</code>
	 * @param fontHeight visina fonta
	 */
	private void drawColumns(Graphics2D g2, int fontHeight) {
		Color c = g2.getColor();
		int numOfColumns = chart.getList().size();
		int columnWidth = (width-EDGE) / numOfColumns; //EDGE je razmak izmedu kraja zadnjeg stupca i kraja cijelog prozora!
		int maxHeight = chart.getMaxY();
		int count = 0;
		
		for (XYValue value : chart.getList()) {
			double chDouble = ((double)value.getY()/maxHeight) * (height-EDGE); //EDGE je razmak izmedu gornjeg vrha prostora za dijagram i maximalne visine stupaca
			int columnHeight = (int) chDouble;
			
			/*NAPOMENA: KOORDINATE X I Y PREDSTAVLJAJU *UPPER LEFT CORNER* POJEDINOG STUPCA!*/
			int x = getWidth() - width + count * columnWidth; //x-koordinata na kojoj zapocinje crtanje stupca
			int y = height - columnHeight; //y-koordinata na kojoj zapocinje crtanje stupca
			/*
			 * objasnjenje za koordinatu y:
			 * u gornjem lijevom kutu otvorenog prozora nalazi se tocka (0,0)!!!
			 * kako bi dobio gornju lijevu tocku stupca kojega moram nacrtati radim sljedece: height - visinaStupca
			 * zato sto je na dnu prozora (na x-osi) y = height, a od toga moram *ODUZETI* visinu stupca
			*/
			
			g2.setColor(Color.ORANGE);
			Rectangle r = new Rectangle(x, y, columnWidth-1, columnHeight);
			g2.fill(r);
			g2.setColor(c);
			
			int measure = g2.getFontMetrics().stringWidth(Integer.toString(value.getX()));
			
			g2.drawString(Integer.toString(value.getX()), x + columnWidth/2 - measure/2, height + FIXED_SPACE/4 + fontHeight);
			count++;
		}
	}

	/**
	 * Metoda računa dimenzije (visinu i širinu) prostora dostupnog za iscrtavanje koordinatne mreže.
	 * 
	 * @param fontHeight visina fonta
	 * @param largestNumber najveći y; <code>yMax</code>
	 */
	private void comuputeSize(int fontHeight, int largestNumber) {
		int w = getWidth();
		int h = getHeight();
		
		width = w - FIXED_SPACE - largestNumber - 2 * EDGE;
		height = h - FIXED_SPACE - 2 * fontHeight - EDGE;  
	}
}
