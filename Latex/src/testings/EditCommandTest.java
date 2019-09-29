package testings;

import static org.junit.Assert.*;

import org.junit.BeforeClass;
import org.junit.Test;

import latex.controller.commands.EditCommand;
import latex.model.Document;

public class EditCommandTest {
	private static EditCommand editCommand = new EditCommand();
	private static Object[] argumentsOfCommand = new Object[10];
	private static Document doc;

	private String actualContents;
	private String expectedContents;

	@BeforeClass
	public static void Initialization(){
		String contents = "old contents";
		doc = new Document("author", "date", "copyright", "versionID", contents);
	}

	@Test
	public void testEditCommand_CompareChangedContents() {
		expectedContents = doc.getContents();

		argumentsOfCommand[0] = doc;
		argumentsOfCommand[1] = "new contents";
		argumentsOfCommand[2] = "";
		doc = (Document) editCommand.execute(argumentsOfCommand);

		actualContents = doc.getContents();

		assertNotEquals(expectedContents, actualContents);
	}

}
