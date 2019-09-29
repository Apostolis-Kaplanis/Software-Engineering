package testings;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ AddLatexCommandTest.class, ChangeVersionsStrategyCommandTest.class, CreateCommandTest.class,
		DisableVersionsManagementCommandTest.class, EditCommandTest.class, EnableVersionsManagementCommandTest.class,
		LoadCommandTest.class, RollbackToPreviousVersionCommandTest.class, SaveCommandTest.class })

public class AllTests {

}
