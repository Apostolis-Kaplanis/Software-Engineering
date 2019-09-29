package testings;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import latex.controller.commands.CreateCommand;
import latex.controller.commands.DisableVersionsManagementCommand;
import latex.controller.commands.EditCommand;
import latex.model.Document;
import latex.model.VersionsManager;
import latex.model.strategies.VersionsStrategy;
import latex.model.strategies.VersionsStrategyFactory;

public class DisableVersionsManagementCommandTest {
	private DisableVersionsManagementCommand disableVersionsManagementCommand = new DisableVersionsManagementCommand();
	private static CreateCommand createCommand = new CreateCommand();
	private EditCommand editCommand = new EditCommand();
	private Object[] argumentsOfDisable = new Object[1];
	private Object[] argumentsOfEdit = new Object[3];

	private static String VOLATILE = "Volatile";

	private static VersionsStrategyFactory strategyFactory = new VersionsStrategyFactory();
	private static VersionsStrategy versionsStrategy = strategyFactory.createStrategy(VOLATILE);
	private static VersionsManager versionsManager = new VersionsManager(versionsStrategy);

	private static Document doc;

	@Test
	public void versionsHistoryRemainsUnchangedTest() {

		//Creating a document
		doc = theCreateCommand();
		List<Document> expectedVersionsHistory = versionsStrategy.getEntireHistory();
		versionsManager.enable();
		boolean expectedEnabled = versionsManager.isEnabled();

		argumentsOfDisable[0] = versionsManager;
		disableVersionsManagementCommand.execute(argumentsOfDisable);

		//Editing to the document
		String newContents = "Some new contents";
		doc = theEditCommand(doc, newContents);

		List<Document> actualVersionsHistory = versionsStrategy.getEntireHistory();
		boolean actualDisabled = versionsManager.isEnabled();

		assertEquals(expectedVersionsHistory, actualVersionsHistory);
		assertNotEquals(expectedEnabled, actualDisabled);
	}

	private Document theEditCommand(Document document, String newContents) {
		argumentsOfEdit[0] = document;
		argumentsOfEdit[1] = newContents;
		argumentsOfEdit[2] = "";
		doc = (Document) editCommand.execute(argumentsOfEdit);

		return doc;
	}

	private Document theCreateCommand() {
		Object[] argumentsOfCreate = new Object[1];
		argumentsOfCreate[0] = new String("book");
		doc = (Document) createCommand.execute(argumentsOfCreate);

		return doc;
	}

}
