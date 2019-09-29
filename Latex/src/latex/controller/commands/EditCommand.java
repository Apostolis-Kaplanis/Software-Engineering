package latex.controller.commands;

import latex.model.Document;

/* EditCommand changes the
contents of a document, */
public class EditCommand implements Command {

	public EditCommand() {
	}

	public Object execute(Object[] argumentsOfCommand) {
		Document doc = (Document) argumentsOfCommand[0];
		String currentContents = (String) argumentsOfCommand[1];
		String latexCommand = (String) argumentsOfCommand[2];

		doc.setContents(currentContents + "\n" + latexCommand);

		return doc;
	}
}
