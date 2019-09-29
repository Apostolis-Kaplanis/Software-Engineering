package latex.controller.commands;

import latex.model.Document;
import latex.model.DocumentManager;

public class CreateCommand implements Command {
	private DocumentManager documentManager = new DocumentManager();

	public CreateCommand() {
	}

	public Object execute(Object[] argumentsOfCommand){
		String templateType = (String) argumentsOfCommand[0];

		Document doc = documentManager.createDocument(templateType);

		return doc;
	}

}
