package testings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import latex.controller.FileManager;
import latex.controller.commands.CreateCommand;
import latex.model.Document;

public class CreateCommandTest {

	private static CreateCommand createCommand;
	private static FileManager reader;
	private static String[] templateTypesArray = {"empty", "article", "book", "letter", "report"};
	private Object[] templateType = new Object[1];
	private String[] templatePathsArray = {
			"src/latex/recources/templates/empty-template.tex",
			"src/latex/recources/templates/article-template.tex",
			"src/latex/recources/templates/book-template.tex",
			"src/latex/recources/templates/letter-template.tex",
			"src/latex/recources/templates/report-template.tex"};
	private String templatePath;
	private Document doc;
	int next = 0;

	private String actualContents;
	private String expectedContents;

	@BeforeClass
	public static void Initialization(){
		createCommand = new CreateCommand();
		reader = new FileManager();
	}

	@Before
	public void settings(){
		templateType[0] = templateTypesArray[next];
		templatePath = templatePathsArray[next];
		next++;
	}

	@Test
	public void testSameContents(){
		doc = (Document) createCommand.execute(templateType);

		actualContents = doc.getContents();
		expectedContents = reader.fileReader(templatePath);

		assertEquals(expectedContents, actualContents);
	}

}
