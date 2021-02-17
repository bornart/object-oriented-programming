package hr.fer.oprpp1.hw05.shell.commands;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellIOException;

/**
 * Class CatShellCommand represents cat command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class CatShellCommand implements ShellCommand {
	
	/**
	 * Method opens given file and writes its content to console while taking one or two arguments.
	 * First argument is mandatory and represents a path to some file, while the other represents a charset name.
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		char[] data = arguments.toCharArray();
		String charsetName = "";
		
		for (int i = data.length-1; i >= 0; i--) {
			if (data[i] == '"' || data[i] == ' ') {
				charsetName = new String(data, i+1, data.length-1-i);
				break;
			}
		}
		String p;
		if (charsetName.equals("")) {
			charsetName = Charset.defaultCharset().displayName();
			p = arguments;
		} else {
			p = new String(data, 0, data.length-1-charsetName.length());
		}
		
		Path path = null;
		try {
			path = Utility.getPaths(p).get(0);
		} catch(ShellIOException | InvalidPathException e) {
			env.writeln("Exception occurred while creating path!");
			return ShellStatus.CONTINUE;
		}

		try (InputStream is = new BufferedInputStream(new FileInputStream(path.toFile()))) {
			byte[] buffer = new byte[1024];
			
			int length;
			while ((length = is.read(buffer)) > 0) {
				String s = new String (buffer, 0, length);
				byte[] bytes = s.getBytes(charsetName);
				String line = new String (bytes, charsetName);
				env.writeln(line);
		    }
		} catch (IOException e) {
			env.writeln("IOException occurred!");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "cat";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command cat opens given file and writes its content to console.");
		desc.add("Command cat takes one or two arguments.");
		desc.add("	The first argument is path to some file and is mandatory.");
		desc.add("	The second argument is charset name that should be used to interpret chars from bytes (optional).");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
