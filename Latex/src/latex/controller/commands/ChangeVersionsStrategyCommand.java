package latex.controller.commands;

import java.util.List;

import latex.model.Document;
import latex.model.VersionsManager;
import latex.model.strategies.VersionsStrategy;
import latex.model.strategies.VersionsStrategyFactory;

public class ChangeVersionsStrategyCommand implements Command {
	public ChangeVersionsStrategyCommand() {
	}

	public Object execute(Object[] argumentsOfCommand){
		VersionsStrategy versionsStrategy = (VersionsStrategy) argumentsOfCommand[0];
		VersionsManager versionsManager = (VersionsManager) argumentsOfCommand[1];
		String savingMethod = (String) argumentsOfCommand[2];

		VersionsStrategyFactory strategyFactory = new VersionsStrategyFactory();

		List<Document> docHistory = versionsStrategy.getEntireHistory();

		versionsStrategy = strategyFactory.createStrategy(savingMethod);

		versionsManager.setStrategy(versionsStrategy);

		versionsStrategy.setEntireHistory(docHistory);

		return versionsManager;
	}
}
