package latex.model.strategies;

import java.util.List;

import latex.model.Document;

public interface VersionsStrategy {

	public void putVersion(Document document);			// adds a new version to the history
	public Document getVersion();						// returns the last version that has been added to the history
	public void setEntireHistory(List<Document> doc);
	public List<Document> getEntireHistory();
	public void removeVersion();						// removes the last version that has been added to the history
}
