package testings;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import latex.controller.commands.CreateCommand;
import latex.controller.commands.EditCommand;
import latex.controller.commands.EnableVersionsManagementCommand;
import latex.model.Document;
import latex.model.VersionsManager;
import latex.model.strategies.VersionsStrategy;
import latex.model.strategies.VersionsStrategyFactory;

public class EnableVersionsManagementCommandTest {
	private static String VOLATILE = "Volatile";
	private static String STABLE = "Stable";

	private EnableVersionsManagementCommand enableVersionsManagementCommand = new EnableVersionsManagementCommand();
	private static CreateCommand createCommand = new CreateCommand();
	private EditCommand editCommand = new EditCommand();
	private Object[] argumentsOfEnable = new Object[2];
	private Object[] argumentsOfEdit = new Object[3];
	private String[] strategyMethods = {VOLATILE, STABLE};		// Two methods to be tested
	private String strategyMethod;
	private VersionsStrategyFactory strategyFactory = new VersionsStrategyFactory();
	private static VersionsStrategy versionsStrategy;
	private static VersionsManager versionsManager;
	private static Document doc;
	private int next = 0;

	private static String expectedContents;

	@BeforeClass
	public static void initialization() {
		//Creating a document
		Object[] argumentsOfCreate = new Object[1];
		argumentsOfCreate[0] = new String("book");
		doc = (Document) createCommand.execute(argumentsOfCreate);

		expectedContents = doc.getContents();
	}

	@Before
	public void settings() {
		strategyMethod = strategyMethods[next];
		versionsStrategy = strategyFactory.createStrategy(strategyMethod);
		versionsManager = new VersionsManager(versionsStrategy);

		argumentsOfEnable[0] = versionsManager;
		argumentsOfEnable[1] = doc;

		next++;
	}

	@Test
	public void sameContentsAfterGetingPreviousVersionTest() {
		/* Testing both Volatile and Stable methods */
		enableVersionsManagementCommand.execute(argumentsOfEnable);

		String newContents = "Some new contents";
		Document document = theEditCommand(doc, newContents);

		document = versionsStrategy.getVersion();

		String actualContents = document.getContents();

		assertEquals(expectedContents, actualContents);
	}

	private Document theEditCommand(Document document, String newContents){
		argumentsOfEdit[0] = document;
		argumentsOfEdit[1] = newContents;
		argumentsOfEdit[2] = "";
		Document doc = (Document) editCommand.execute(argumentsOfEdit);

		return doc;
	}

}
