package latex.controller.commands;

import java.io.File;

import javax.swing.JOptionPane;

import latex.controller.FileManager;

public class LoadCommand implements Command {
	public LoadCommand() {
	}

	public Object execute(Object[] argumentsOfCommand){
		File file = (File) argumentsOfCommand[0];
		FileManager lf = new FileManager();
		String contents = null;

		try {
			contents = lf.loadFile(file);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return contents;
	}

}
