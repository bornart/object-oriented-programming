package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellIOException;

/**
 * Class CopyShellCommand represents copy command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class CopyShellCommand implements ShellCommand {
	
	/**
	 * Method expects two arguments: source file name and destination file name.
	 * It copies the content from the source file (mustn't be a directory!) to a destination file.
	 * If destination file already exists, method waits users approval to override the file.
	 * 
	 * @param env object which implements interface Environment
	 * @param arguments single string which represents everything that user entered AFTER the command name
	 * @return ShellStatus enumeration (CONTINUE or TERMINATE)
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<Path> paths = null;
		try {
			paths = Utility.getPaths(arguments);
		} catch(ShellIOException | InvalidPathException e) {
			env.writeln("Exception occurred while creating path(s)!");
			return ShellStatus.CONTINUE;
		}
		
		if (paths.size() != 2) {
			env.writeln("The copy command expects two arguments!");
			return ShellStatus.CONTINUE;
		}
		
		Path source = paths.get(0);
		Path destination = paths.get(1);
		
		if (!source.toFile().isFile()) {
			env.writeln("The copy command must except file as first argument (source)!");
			return ShellStatus.CONTINUE;
		}
		
		//destination file isn't empty... User must allow overriding. 
		if (destination.toFile().isFile() && destination.toFile().length() != 0) {
			env.writeln("Destination file already exists. Would you like to override it? (y/n)");
			env.write(env.getPromptSymbol()+ " ");
			String decision = env.readLine().toLowerCase();
			if (!decision.equals("y")) {
				env.writeln("Command terminated.");
				return ShellStatus.CONTINUE;
			}
		}
		
		if (destination.toFile().isDirectory()) {
			String s = destination.toFile().getAbsolutePath() + source.getFileName();
			File f = new File(s);
			f.getParentFile().mkdirs(); //just in case
			destination = Paths.get(s);
		}
		
		try(InputStream is = Files.newInputStream(source);
				OutputStream os = Files.newOutputStream(destination)) {
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
	            os.write(buffer, 0, length);
	        }
		} catch(IOException ex) {
			env.writeln("IOException occurred during the proccess od copying file from source to destination!");
			return ShellStatus.CONTINUE;
		}
		env.writeln("File successfully copied!");
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "copy";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("The copy command expects two arguments: source file name and destination file name.");
		desc.add("Command works only with files (no directories).");
		desc.add("If the second argument is directory, command will copy the original file into that directory using the original file name");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
