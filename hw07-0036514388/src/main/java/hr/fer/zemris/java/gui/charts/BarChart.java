package hr.fer.zemris.java.gui.charts;

import java.util.ArrayList;
import java.util.List;

/**
 * Razred predstavlja stupčasti dijagram.
 * 
 * @author borna
 *
 */
public class BarChart {
	
	private List<XYValue> list;
	private String descX;
	private String descY;
	private int minY;
	private int maxY;
	private int gap;
	
	/**
	 * Konstruktor.
	 * 
	 * @param list lista <code>XYValue</code> objekata
	 * @param descX opis uz x-os
	 * @param descY opis uz y-os
	 * @param minY minimalni y koji se prikazuje na osi
	 * @param maxY maksimalni y koji se prikazuje na osi
	 * @param gap razmak između dva susjedna y-a koji se prikazuju na osi
	 * @throws IllegalArgumentException
	 */
	public BarChart(List<XYValue> list, String descX, String descY, int minY, int maxY, int gap) {
		if (minY < 0) throw new IllegalArgumentException("Minimalni y mora biti pozitivan cijeli broj!");
		if (maxY - minY <= 0) throw new IllegalArgumentException("Maksimalni y mora biti strogo veći od predanog minimuma!");
		
		for (XYValue v : list) {
			if (v.getY() < minY) throw new IllegalArgumentException("Y-vrijednosti u svim podatcima predane liste moraju biti >= od Ymin!");
		}
		
		this.list = new ArrayList<>(list);
		this.descX = descX;
		this.descY = descY;
		this.minY = minY;
		this.gap = gap;
		
		if ((maxY-minY) % gap != 0) {
			while(true) {
				if ((maxY-minY) % gap == 0) break;
				maxY++;
			}
			this.maxY = maxY;
		} else {
			this.maxY = maxY;
		}
	}
	
	/**
	 * Getter. Dohvaća listu <code>XYValue</code> objekata.
	 * 
	 * @return lista <code>XYValue</code> objekata
	 */
	public List<XYValue> getList() {
		return list;
	}
	
	/**
	 * Getter. Dohvaća opis uz x-os.
	 * 
	 * @return opis uz x-os
	 */
	public String getDescX() {
		return descX;
	}
	
	/**
	 * Getter. Dohvaća opis uz y-os.
	 * 
	 * @return opis uz y-os
	 */
	public String getDescY() {
		return descY;
	}
	
	/**
	 * Getter. Dohvaća minimalni y koji se prikazuje na osi.
	 * 
	 * @return minimalni y koji se prikazuje na osi
	 */
	public int getMinY() {
		return minY;
	}
	
	/**
	 * Getter. Dohvaća maksimalni y koji se prikazuje na osi.
	 * 
	 * @return maksimalni y koji se prikazuje na osi
	 */
	public int getMaxY() {
		return maxY;
	}
	
	/**
	 * Getter. Dohvaća razmak između dva susjedna y-a koji se prikazuju na osi
	 * 
	 * @return razmak između dva susjedna y-a koji se prikazuju na osi
	 */
	public int getGap() {
		return gap;
	}
	
	
}
