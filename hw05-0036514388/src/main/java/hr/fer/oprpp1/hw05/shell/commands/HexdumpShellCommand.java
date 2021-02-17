package hr.fer.oprpp1.hw05.shell.commands;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellIOException;

/**
 * Class HexdumpShellCommand represents hexdump command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class HexdumpShellCommand implements ShellCommand {
	
	/**
	 * Method produces a hex-output while respecting the necessary format.
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		Path path = null;
		try {
			List<Path> paths = Utility.getPaths(arguments);
			if (paths.size() > 1) {
				env.writeln("Hexdump command excepts only one argument!");
				return ShellStatus.CONTINUE;
			}
			path = paths.get(0);
		} catch(ShellIOException | InvalidPathException e) {
			env.writeln("Exception occurred while creating path!");
			return ShellStatus.CONTINUE;
		}
		
		if (path.toFile().isDirectory()) {
			env.writeln("The hexdump command expects a file as argument, not directory!");
			return ShellStatus.CONTINUE;
		}
		
		try (InputStream is = new FileInputStream(path.toFile())) {
			byte[] buff = new byte[1024];
			int n = 0;
			while(true) {
				int r = is.read(buff);
				if(r<1) break;
				
				for (int i = 0; i < r; i++) {
					if (i != 0) i--;
					String hex = Integer.toHexString(i+n);
					int len = hex.length();
					StringBuilder sb = new StringBuilder(60);
					
					//formatting the far left column:
					for (int j = 0; j < 8 - len; j++) {
						sb.append("0");
					}
					sb.append(hex + ": ");
					
					//formatting the rest of the row:
					String subset = "";
					int split = 0;
					for (int k = i; i < k + 16; i++) {
						split++;
						
						if (buff[i] > 127 || buff[i] < 0) {
							buff[i] = 46;
						}
						
						String s = Integer.toHexString(buff[i]).toUpperCase();
						if (s.length() == 1) {
							if (s.equals("0")) {
								s = "  ";
							} else {
								s = "0" + s;
							}
						}
						//split (8|8):
						if (split == 8) {
							sb.append(s + "|");
						} else {
							sb.append(s + " ");
						}
						//replacement of all bytes whose value is less than 32 or greater than 127 with '.'
						subset += (buff[i] < 32 && buff[i] != 0) ? "." : Character.toString(buff[i]);
					}
					sb.append("| ");
					env.writeln(sb + subset);
				}
				n += r;
			}
		} catch(IOException ex) {
			env.writeln("Exception occurred while reading file!");
			return ShellStatus.CONTINUE;
		}

		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "hexdump";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("The hexdump command expects a single argument: file name, and produces hex-output.");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
