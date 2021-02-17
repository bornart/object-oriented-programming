package hr.fer.oprpp1.hw05.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import hr.fer.oprpp1.hw05.shell.Environment;
import hr.fer.oprpp1.hw05.shell.ShellIOException;

/**
 * Class LsShellCommand represents ls command in a command-line program <code>MyShell</code>. 
 * This class provides basic informations about the command and also offers the method <code>executeCommand</code>.
 * 
 * @author borna
 *
 */
public class LsShellCommand implements ShellCommand {
	
	/**
	 * Method takes a single argument – directory, and writes a directory listing (not recursive).
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
				env.writeln("Ls command excepts only one argument!");
				return ShellStatus.CONTINUE;
			}
			path = paths.get(0);
		} catch(ShellIOException | InvalidPathException e) {
			env.writeln("Exception occurred while creating path!");
			return ShellStatus.CONTINUE;
		}
		
		File file = path.toFile();
		if (!file.isDirectory())  {
			env.writeln("Command ls takes directory as argument!");
			return ShellStatus.CONTINUE;
		}
		
		File[] children = file.listFiles();
		if (children == null) {
			env.writeln("Directory is empty!");
			return ShellStatus.CONTINUE;
		}
		
		for (File f : children) {
			StringBuilder sb = new StringBuilder(36);

			sb.append(f.isDirectory() ? "d" : "-");
			sb.append(Files.isReadable(f.toPath()) ? "r" : "-");
			sb.append(Files.isWritable(f.toPath()) ? "w" : "-");
			sb.append(Files.isExecutable(f.toPath()) ? "x" : "-");
			
			sb.append(" ");
			
			//this could be easily solved with String.format()...
			long size = f.length();
			int numOfDigits = (int) (Math.log10(size)+1);
			int n = size != 0 ? 10-numOfDigits : 9;
			for (int i = 0; i < n; i++) {
				sb.append(" ");
			}
			sb.append(size + " ");
			
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(
			f.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS);
			BasicFileAttributes attributes = null;
			try {
				attributes = faView.readAttributes();
			} catch (IOException e) {
				env.writeln("Exception occured while reading attributes!");
				return ShellStatus.CONTINUE;
			}
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			sb.append(formattedDateTime + " ");
			env.writeln(sb + f.getName());
		}
		return ShellStatus.CONTINUE;
	}

	@Override
	public String getCommandName() {
		return "ls";
	}

	@Override
	public List<String> getCommandDescription() {
		List<String> desc = new ArrayList<>();
		desc.add("Command ls takes a single argument – directory, and writes a directory listing (not recursive).");
		desc = Collections.unmodifiableList(desc);
		return desc;
	}

}
