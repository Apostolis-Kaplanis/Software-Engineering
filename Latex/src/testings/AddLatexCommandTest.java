package testings;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import latex.controller.commands.AddLatexCommand;
import latex.model.Document;

public class AddLatexCommandTest {

	private static AddLatexCommand addLatexCommand = new AddLatexCommand();
	private static Object[] argumentsOfCommand = new Object[10];
	private static String currentDocumentType;
	private static Document doc;
	private static String expectedContents;

	private String[] allCommands = {"\\chapter{...}", "\\section{}", "\\subsection{}",
			"\\subsubsection{}", "\\begin{itemize}", "\\item ...",
			"\\end{itemize}", "\\begin{enumerate}", "\\end{enumerate}",
			"\\begin{table}", "\\caption{....}\\label{...}", "\\begin{tabular}{|c|c|c|}",
			"\\hline", "... &...&...\\\\", "\\end{tabular}", "\\end{table}",
			"\\begin{figure}", "\\includegraphics[width=...,height=...]{...}",
			"\\caption{....}\\label{...}", "\\end{figure}"};
	private String latexCommand;
	private int next = 0;

	@BeforeClass
	public static void Initialization(){
		currentDocumentType = "book";
		expectedContents = "old contents";
		doc = new Document("author", "date", "copyright", "versionID", expectedContents);

		argumentsOfCommand[1] = currentDocumentType;
		argumentsOfCommand[2] = doc;
	}

	@Before
	public void settings(){
		latexCommand = allCommands[next];
		next++;

		argumentsOfCommand[0] = latexCommand;
	}

	@Test
	public void testCommandsCanBeAdded_LatexCommand() {
		doc = (Document) addLatexCommand.execute(argumentsOfCommand);
		String actualContents = doc.getContents();

		assertNotEquals(expectedContents, actualContents);
	}

	@After
	public void repeatTest(){
		while(next != allCommands.length){
			Initialization();
			settings();
			testCommandsCanBeAdded_LatexCommand();
		}
	}

}
