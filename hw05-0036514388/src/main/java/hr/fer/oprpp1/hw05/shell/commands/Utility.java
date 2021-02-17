package hr.fer.oprpp1.hw05.shell.commands;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.ShellIOException;

/**
 * Utility is a "shared utility class" which reduces the duplication of code in command classes.
 * This class is final because it is not supposed to be subclassed.
 * 
 * @author borna
 *
 */
public final class Utility {
	
	/**
	 * Private constructor (to avoid unnecessary instantiation of the class).
	 */
	private Utility() {
		
	}
	
	/**
	 * Static method which reads paths from the String given by the user.
	 * Method <code>getPaths</code> accepts \" as escape sequence representing " and \\ as escape sequence representing \. 
	 *  
	 * @param args String given by the user which contains one or more paths
	 */
	public static List<Path> getPaths(String args) {
		List<Path> paths = new ArrayList<>();
		
		char[] data = args.toCharArray();
		int position = 0;

		while (position < args.length()) {
			//skip blank spaces:
			while (data[position] == ' ' && position < args.length()) position++;
			
			if (data[position] == '"' || data[position] == '\\' && data[position+1] == '"') {
				position += data[position+1] == '"' ? 2 : 1;
				
				String p = "";
				p += data[position];
				position++;
				
				while (position < args.length()) {
					if (data[position] == '\\') {
						if (data[position+1] == '\\') {
							position++;
						}
						if (data[position+1] == '"') {
							if (position+1 != args.length()-1) {
								if (data[position+2] != ' ') throw new ShellIOException("Invalid path!");
							}
							position += 2;
							break;
						}
					}
					if (data[position] == '"') {
						if (position != args.length()-1) {
							if (data[position+1] != ' ') throw new ShellIOException("Invalid path!");
						}
						position++;
						break;
					}
					p += data[position];
					position++;
				}
				Path path = Paths.get(p);
				paths.add(path);
			} else {
				int start = position;
				
				while (position < args.length() && data[position] != ' ') {
					position++;
				}
				
				int end = position;
				position++;
				
				String p = new String(data, start, end-start);
				Path path = Paths.get(p);
				paths.add(path);
			}
		}
		return paths;
	}
}
