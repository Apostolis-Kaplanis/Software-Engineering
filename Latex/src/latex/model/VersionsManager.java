package latex.model;

import latex.model.strategies.VersionsStrategy;

public class VersionsManager {
	private boolean enabled;
	private VersionsStrategy strategy;
	public VersionsManager(VersionsStrategy strategy){
		this.strategy = strategy;
	}

	public boolean isEnabled(){
		return enabled;
	}

	public void enable(){
		enabled = true;
	}

	public void disable(){
		enabled = false;
	}

	public void setStrategy(VersionsStrategy strategy){
		this.strategy = strategy;
	}

	public VersionsStrategy getStrategy(){
		return this.strategy;
	}

	public void setCurrentVersion(Document newDocumentVersion){
		strategy.putVersion(newDocumentVersion);
	}

	public Document getPreviousVersion(){
		return strategy.getVersion();
	}

	public void rollbackToPreviousVersion(){
		strategy.removeVersion();
	}

}
