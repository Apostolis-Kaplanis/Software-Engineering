package latex.controller.commands;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;
import latex.controller.FileManager;
import latex.model.Document;

public class SaveCommand implements Command {


	public SaveCommand() {
	}

	public Object execute(Object[] argumentsOfCommand){
		Document document = (Document) argumentsOfCommand[0];
		File file = (File) argumentsOfCommand[1];

		FileManager sf = new FileManager();

		try {
			sf.saveFile(document, file);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		}

		return null;
	}
}
