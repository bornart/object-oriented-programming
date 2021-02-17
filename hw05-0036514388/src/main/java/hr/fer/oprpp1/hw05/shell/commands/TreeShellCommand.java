package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellIOException;

/**
 * Class TreeShellCommand represents tree command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class TreeShellCommand implements ShellCommand {
	
	/**
	 * Method expects a single argument: directory name and prints a tree while shifting each directory level two charatcers to the right.
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
				env.writeln("Tree command excepts only one argument!");
				return ShellStatus.CONTINUE;
			}
			path = paths.get(0);;
		} catch(ShellIOException | InvalidPathException e) {
			env.writeln("Exception occurred while creating path!");
			return ShellStatus.CONTINUE;
		}
		
		recursive(path.toFile(), 0, env);
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Recursive method which prints a tree with each directory level shifted two charatcers to the right.
	 * 
	 * @param file file whose tree needs to be printed
	 * @param shift number of empty spaces on the left side of the output (used to print a tree)
	 * @param env object which implements the Environment interface
	 */
	private void recursive(File file, int shift, Environment env) {
		if (shift == 0) {
			env.writeln(file.getName());
		} else {
			String s = "";
			for (int i = 0; i < shift; i++) {
				s += " ";
			}
			env.writeln(s + file.getName());
		}
		
		File[] children = file.listFiles();
		if (children == null) return;
		shift += 2;
		for (File f : children) {
			if (f.isFile()) {
				String s = "";
				for (int i = 0; i < shift; i++) {
					s += " ";
				}
				env.writeln(s + f.getName());
			} else if (f.isDirectory()) {
				recursive(f, shift, env);
			}
		}
	}

	@Override
	public String getCommandName() {
		return "tree";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("The tree command expects a single argument: directory name and prints a tree.");
		desc.add("Command shifts each directory level two charatcers to the right.");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
