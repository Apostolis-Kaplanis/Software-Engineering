package latex.model.strategies;

public class VersionsStrategyFactory {
	private static String VOLATILE = "Volatile";
	private static String STABLE = "Stable";

	public VersionsStrategyFactory(){
	}

	public VersionsStrategy createStrategy(String strategyType){
		if (strategyType.equals(VOLATILE)){
			return new VolatileVersionsStrategy();
		}
		else if (strategyType.equals(STABLE)){
			return new StableVersionsStrategy();
		}
		else
			throw new IllegalArgumentException();
	}
}
