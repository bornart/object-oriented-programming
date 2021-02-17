package hr.fer.zemris.java.gui.layouts;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * Upravljač razmještaja (<code>layout manager</code>).
 * 
 * @author borna
 *
 */
public class CalcLayout implements LayoutManager2 {
	
	private static final int ROWS = 5;
	private static final int COLUMNS = 7;
	
	private Map<Component, RCPosition> components;
	private int space;
	
	/**
	 * Konstruktor koji postavlja željeni razmak između redaka i stupaca (u pikselima).
	 * Ista vrijednost se koristi i za razmak između redaka, i za razmak između stupaca.
	 * 
	 * @param space razmak između redaka i stupaca u pikselima
	 */
	public CalcLayout(int space) {
		this.space = space;
		components = new HashMap<>();
	}
	
	/**
	 * Konstuktor koji vrijednost razmaka između redaka i stupaca postavlja na 0 piksela (komponente su međusobno zalijepljene).
	 */
	public CalcLayout() {
		this(0);
	}
	
	@Override
	public void addLayoutComponent(String name, Component comp) {
		throw new UnsupportedOperationException("Nije dozvoljeno pozivanje metode addLayoutComponent.");
	}

	@Override
	public void removeLayoutComponent(Component comp) {
		//kontejner ovom metodom dojavljuje i Vama da predane komponente vise nema...?
		components.remove(comp);
	}

	@Override
	public Dimension preferredLayoutSize(Container parent) {
		//return calculateLayoutSize(parent, "pref");
		return calculateLayoutSize(parent, c -> c.getPreferredSize()); //ili jos kraće: Component::getPreferredSize
	}

	@Override
	public Dimension minimumLayoutSize(Container parent) {
		//return calculateLayoutSize(parent, "min");
		return calculateLayoutSize(parent, c -> c.getMinimumSize()); //ili jos kraće: Component::getMinimumSize
	}
	
	@Override
	public Dimension maximumLayoutSize(Container parent) {
		//return calculateLayoutSize(parent, "max");
		return calculateLayoutSize(parent, c -> c.getMaximumSize()); //ili jos kraće: Component::getMaximumSize
	}
	
	/**
	 * Metoda izračunava odgovarajuću veličinu (dimenzije) layout-a.
	 * 
	 * @param parent roditeljska komponenta
	 * @param f funkcijsko sučelje <code>Function<Component, Dimension></code>
	 * @return odgovarajuće dimenzije layout-a
	 */
	//prije je drugi parametar bio String...
	private Dimension calculateLayoutSize(Container parent, Function<Component, Dimension> f) {
		Insets insets = parent.getInsets();
		int num = parent.getComponentCount();
		
		int width = 0;
		int height = 0;
		
		for (int i = 0; i < num; i++) {
			Component comp = parent.getComponent(i);
			
			if (components.get(comp) == null) continue;
			
			Dimension d = f.apply(comp); //koristenje Function<T, R>!
//			Dimension d;
//			if (s.equals("min")) {
//				d = comp.getMinimumSize();
//			} else if (s.equals("max")) {
//				d = comp.getMaximumSize();
//			} else {
//				d = comp.getPreferredSize();
//			}
			
			if (components.get(comp).equals(new RCPosition(1, 1))) {
				d.width = (d.width - 4 * space) / 5;
			}
			
			if (width < d.width) width = d.width;
			
			if (height < d.height) height = d.height;
			
		}
		
		return new Dimension(insets.left + insets.right + COLUMNS*width + (COLUMNS-1)*space, 
							insets.top + insets.bottom + ROWS*height + (ROWS-1)*space);
	}

	@Override
	public void layoutContainer(Container parent) {
		Insets insets = parent.getInsets();
        int ncomponents = parent.getComponentCount();
        
        int sumOfGapsW = (COLUMNS - 1) * space;
        int widthWOInsets = parent.getWidth() - (insets.left + insets.right);
        double widthOnComponent = (double) (widthWOInsets - sumOfGapsW) / COLUMNS;
        
        int sumOfGapsH = (ROWS - 1) * space;
        int heightWOInsets = parent.getHeight() - (insets.top + insets.bottom);
        double heightOnComponent = (double) (heightWOInsets - sumOfGapsH) / ROWS;
        
        int lessWidth = (int) (Math.round(widthOnComponent) * COLUMNS - widthWOInsets + sumOfGapsW); //broj komponenti koje moraju biti uže za 1 piksel
        int lessHeight = (int) (Math.round(heightOnComponent) * ROWS - heightWOInsets + sumOfGapsH); //broj komponenti koje moraju biti kraće za 1 piksel
        
        //ako su brojke NEGATIVNE -> imamo nekoliko piksela viška!
        boolean surplusW = false;
        boolean surplusH = false;
        
        if (lessWidth < 0) {
        	lessWidth *= -1;
        	surplusW = true;
        }
        if (lessHeight < 0) {
        	lessHeight *= -1;
        	surplusH = true;
        }
        
        List<Double> arrWidth = new ArrayList<>();
        List<Double> arrHeight = new ArrayList<>();
       
        int idx = 0;

        for (int i = 0; i < COLUMNS; i++) {
        	if (idx < lessWidth) {
        		if (surplusW) {
        			if (i % 2 == 0) {
        				arrWidth.add(widthOnComponent+1);
        				idx++;
            			continue;
        			}
        		} else {
        			if (i%2 != 0) {
        				arrWidth.add(widthOnComponent+1);
            			idx++;
            			continue;
        			}
        		}
        	}
        	arrWidth.add(widthOnComponent);
        }
        

        idx = 0;
        for (int i = 0; i < ROWS; i++) {
        	if (idx < lessHeight) {
        		if (surplusH) {
        			if (i % 2 == 0) {
        				arrHeight.add(heightOnComponent+1);
        				idx++;
            			continue;
        			}
        		} else {
        			if (i%2 != 0) {
        				arrHeight.add(heightOnComponent+1);
            			idx++;
            			continue;
        			}
        		}
        	}
        	arrHeight.add(heightOnComponent);
        }
        int x = insets.left;
        int y = insets.top;
        
        for (int i = 0; i < ncomponents; i++) {
        	Component comp = parent.getComponent(i);
        	
            if (components.get(comp) == null) continue;
            
            int c = components.get(comp).getColumn();
        	int r = components.get(comp).getRow();
            
            if (components.get(comp).equals(new RCPosition(1, 1))) {
            	int height = (int) Math.round(arrHeight.get(0));
            	int width = 0; 
            	for (int k = 0; k < 5; k++) {
            		width += arrWidth.get(k);
            	}
            	
            	comp.setBounds(x, y, width + 4 * space, height);
            } else {
            	int width = (int) (Math.round(arrWidth.get(c-1)));
            	int height = (int) Math.round(arrHeight.get(r-1));
            	
            	int skipComponentsW = 0;
            	for (int k = 0; k < c - 1; k++) {
            		skipComponentsW += arrWidth.get(k);
            	}
            	
            	int skipComponentsH = 0;
            	for (int k = 0; k < r - 1; k++) {
            		skipComponentsH += arrHeight.get(k);
            	}
            	
            	comp.setBounds(x + (c-1) * space + skipComponentsW , y + (r-1) * space + skipComponentsH, width, height);
            }
        }
	}
	
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		if (comp == null || constraints == null) throw new NullPointerException("Zabranjeno je kao ograničenje ili kao komponentu predati null-referencu!");
		if (constraints.getClass() != String.class && constraints.getClass() != RCPosition.class) throw new IllegalArgumentException("Ograničenje mora biti tipa RCPosition ili tipa String!");
		if (components.containsKey(comp)) throw new CalcLayoutException("Zabranjeno je dodavanje iste komponente uz više ograničenja!");
		if (components.containsValue(constraints)) throw new CalcLayoutException("Zabranjeno je dodavanje više komponenti uz isto ograničenje!");
		
		if (constraints.getClass() == String.class) {
			RCPosition constraint = RCPosition.parse((String) constraints);
			components.put(comp, constraint);
		} else {
			components.put(comp, (RCPosition) constraints);
		}
	}

	@Override
	public float getLayoutAlignmentX(Container target) {
		return 0;
	}

	@Override
	public float getLayoutAlignmentY(Container target) {
		return 0;
	}

	@Override
	public void invalidateLayout(Container target) {
		//?
	}

}
