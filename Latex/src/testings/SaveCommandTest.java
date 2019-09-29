package testings;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

import org.junit.Test;

import latex.controller.commands.CreateCommand;
import latex.controller.commands.SaveCommand;
import latex.model.Document;

public class SaveCommandTest {

	private SaveCommand saveCommand = new SaveCommand();
	private CreateCommand createCommand = new CreateCommand();
	private static Object[] argumentsOfCommand = new Object[10];
	private StringBuilder strBuilder = new StringBuilder();
	private Document doc;

	@Test
	public void testSameContents() throws IOException {
		argumentsOfCommand[0] = new String("book");
		doc =  (Document) createCommand.execute(argumentsOfCommand);

		String expectedContents = doc.getContents();

		//Creating a temporary test file
		File temp = File.createTempFile("tempfile", ".tmp");

		argumentsOfCommand[0] = doc;
		argumentsOfCommand[1] = temp;
		saveCommand.execute(argumentsOfCommand);

		//Try loading contents of the temp file
		Scanner input = new Scanner(temp);

		while(input.hasNext()){
			strBuilder.append(input.nextLine());
			strBuilder.append("\n");
		}
		input.close();

		String actualContents = strBuilder.toString();

		assertEquals(expectedContents, actualContents);
	}

}
