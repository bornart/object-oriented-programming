package hr.fer.oprpp1.hw05.shell;

import hr.fer.oprpp1.hw05.shell.commands.ShellCommand;
import hr.fer.oprpp1.hw05.shell.commands.ShellStatus;


/**
 * Class represents a command-line program MyShell.
 * 
 * @author borna
 *
 */
public class MyShell{
	
	public static void main(String[] args) {
		
		System.out.printf("Welcome to MyShell v 1.0%n");
		
		Shell shell = new Shell();
		while (true) {
			String prompt = shell.getPromptSymbol()+ " ";
			shell.write(prompt);
			String l = shell.readLine();
			String[] arr = l.split(" ");
			String commandName = arr[0];
			String arguments = "";
			for (int i = 1; i < arr.length; i++) {
				if (!arr[i].equals(shell.getMorelinesSymbol().toString())) {
					arguments += arr[i] + " ";
				} else if (i == arr.length-1) {
					String multiline = shell.getMultilineSymbol()+ " ";
					shell.write(multiline);
					
					while (true) {
						String nextLine = shell.readLine();
						String[] elems = nextLine.split(" ");
						
						for (int j = 0; j < elems.length; j++) {
							if (j == elems.length-1) {
								if (!elems[j].equals(shell.getMorelinesSymbol().toString())) arguments += elems[j] + " ";
							} else {
								arguments += elems[j] + " ";
							}
						}
						if (!elems[elems.length-1].equals(shell.getMorelinesSymbol().toString())) break;
						shell.write(multiline);
					}
				}
			}
			ShellCommand command = shell.commands().get(commandName);
			arguments = arguments.trim();
			ShellStatus status = command.executeCommand(shell, arguments);
			if (status == ShellStatus.TERMINATE) {
				shell.closeScanner();
				break;
			}
		}
	}
}
