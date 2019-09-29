package latex.controller.commands;

public class CommandsFactory {
	public Command createCommand(String commandType){

		if(commandType.equals("CreateCommand")){
			return new CreateCommand();
		}
		else if(commandType.equals("AddLatexCommand")){
			return new AddLatexCommand();
		}
		else if(commandType.equals("RollbackToPreviousVersionCommand")){
			return new RollbackToPreviousVersionCommand();
		}
		else if(commandType.equals("EditCommand")){
			return new EditCommand();
		}
		else if(commandType.equals("LoadCommand")){
			return new LoadCommand();
		}
		else if(commandType.equals("SaveCommand")){
			return new SaveCommand();
		}
		else if(commandType.equals("EnableVersionsManagementCommand")){
			return new EnableVersionsManagementCommand();
		}
		else if(commandType.equals("DisableVersionsManagementCommand")){
			return new DisableVersionsManagementCommand();
		}
		else if(commandType.equals("ChangeVersionsStrategyCommand")){
			return new ChangeVersionsStrategyCommand();
		} else
			throw new IllegalArgumentException();
	}

}
