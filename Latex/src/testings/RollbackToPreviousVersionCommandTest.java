package testings;

import static org.junit.Assert.*;

import org.junit.Test;

import latex.controller.commands.CreateCommand;
import latex.controller.commands.EditCommand;
import latex.controller.commands.EnableVersionsManagementCommand;
import latex.controller.commands.RollbackToPreviousVersionCommand;
import latex.model.Document;
import latex.model.VersionsManager;
import latex.model.strategies.VersionsStrategy;
import latex.model.strategies.VersionsStrategyFactory;

public class RollbackToPreviousVersionCommandTest {
	private RollbackToPreviousVersionCommand rollbackToPreviousVersionCommand = new RollbackToPreviousVersionCommand();
	private static CreateCommand createCommand = new CreateCommand();
	private EditCommand editCommand = new EditCommand();
	private EnableVersionsManagementCommand enableCommand = new EnableVersionsManagementCommand();
	private Object[] argumentsOfRollback = new Object[3];

	private static String VOLATILE = "Volatile";

	private static VersionsStrategyFactory strategyFactory = new VersionsStrategyFactory();
	private static VersionsStrategy versionsStrategy = strategyFactory.createStrategy(VOLATILE);
	private static VersionsManager versionsManager = new VersionsManager(versionsStrategy);

	private static Document doc;

	@Test
	public void sameContentsAfterRollbackTest() {

		//Creating a document
		doc = theCreateCommand();
		theEnableCommand(doc);

		String expectedContents = doc.getContents();

		//Editing to the document
		String newContents = doc.getContents() + "Some new contents";
		theEditCommand(doc, newContents);
		theEnableCommand(doc);

		argumentsOfRollback[0] = versionsStrategy;
		argumentsOfRollback[1] = versionsManager;
		argumentsOfRollback[2] = doc;

		doc = (Document) rollbackToPreviousVersionCommand.execute(argumentsOfRollback);

		String actualContents = doc.getContents();

		assertEquals(expectedContents, actualContents);
	}

	private void theEditCommand(Document document, String newContents) {
		Object[] argumentsOfEdit = new Object[3];

		argumentsOfEdit[0] = document;
		argumentsOfEdit[1] = newContents;
		argumentsOfEdit[2] = "";

		doc = (Document) editCommand.execute(argumentsOfEdit);
	}

	private Document theCreateCommand() {
		Object[] argumentsOfCreate = new Object[1];
		argumentsOfCreate[0] = new String("book");
		doc = (Document) createCommand.execute(argumentsOfCreate);

		return doc;
	}

	private void theEnableCommand(Document document) {
		Object[] argumentsOfEnable = new Object[2];

		argumentsOfEnable[0] = versionsManager;
		argumentsOfEnable[1] = document;

		enableCommand.execute(argumentsOfEnable);
	}
}
