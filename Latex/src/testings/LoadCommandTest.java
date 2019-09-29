package testings;

import static org.junit.Assert.assertEquals;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

import latex.controller.commands.CreateCommand;
import latex.controller.commands.LoadCommand;
import latex.model.Document;

public class LoadCommandTest {

	private CreateCommand createCommand = new CreateCommand();
	private LoadCommand loadCommand = new LoadCommand();
	private static Object[] argumentsOfCommand = new Object[10];
	private Document doc;

	@Test
	public void test() throws IOException {
		argumentsOfCommand[0] = new String("book");
		doc =  (Document) createCommand.execute(argumentsOfCommand);

		String expectedContents = doc.getContents();

		//Creating a temporary test file
		File temp = File.createTempFile("tempfile", ".tmp");
		//write some contents
		BufferedWriter bw = new BufferedWriter(new FileWriter(temp));
 	    bw.write(expectedContents);
 	    bw.close();

		argumentsOfCommand[0] = temp;
		String actualContents = (String) loadCommand.execute(argumentsOfCommand);

		assertEquals(expectedContents, actualContents);
	}

}
