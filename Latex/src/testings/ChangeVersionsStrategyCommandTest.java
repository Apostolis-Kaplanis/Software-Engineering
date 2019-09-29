package testings;

import static org.junit.Assert.*;

import org.junit.Test;

import latex.controller.commands.ChangeVersionsStrategyCommand;
import latex.model.VersionsManager;
import latex.model.strategies.VersionsStrategy;
import latex.model.strategies.VersionsStrategyFactory;

public class ChangeVersionsStrategyCommandTest {
	private ChangeVersionsStrategyCommand changeVersionsStrategyCommand = new ChangeVersionsStrategyCommand();
	private Object[] argumentsOfCommand = new Object[3];

	private static String VOLATILE = "Volatile";
	private static String STABLE = "Stable";

	private static VersionsStrategyFactory strategyFactory = new VersionsStrategyFactory();
	private static VersionsStrategy volatileVersionsStrategy = strategyFactory.createStrategy(VOLATILE);
	private static VersionsStrategy stableVersionsStrategy = strategyFactory.createStrategy(STABLE);
	private static VersionsManager versionsManager;

	private VersionsStrategy expected;
	private VersionsStrategy actual;

	@Test
	public void volatileToStableTest() {
		versionsManager = new VersionsManager(volatileVersionsStrategy);

		expected = stableVersionsStrategy;

		argumentsOfCommand[0] = volatileVersionsStrategy;
		argumentsOfCommand[1] = versionsManager;
		argumentsOfCommand[2] = VOLATILE;

		versionsManager = (VersionsManager) changeVersionsStrategyCommand.execute(argumentsOfCommand);

		actual = versionsManager.getStrategy();

		assertNotEquals(expected, actual);
	}

	@Test
	public void stableToVolatileTest() {
		versionsManager = new VersionsManager(stableVersionsStrategy);

		expected = volatileVersionsStrategy;

		argumentsOfCommand[0] = stableVersionsStrategy;
		argumentsOfCommand[1] = versionsManager;
		argumentsOfCommand[2] = STABLE;

		versionsManager = (VersionsManager) changeVersionsStrategyCommand.execute(argumentsOfCommand);

		actual = versionsManager.getStrategy();

		assertNotEquals(expected, actual);
	}

}
