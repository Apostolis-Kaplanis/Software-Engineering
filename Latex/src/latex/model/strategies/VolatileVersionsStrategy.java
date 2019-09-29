package latex.model.strategies;

import java.util.ArrayList;
import java.util.List;

import latex.model.Document;

public class VolatileVersionsStrategy implements VersionsStrategy{
	private List<Document> versionsHistory = new ArrayList<Document>();
	public VolatileVersionsStrategy(){
	}

	public void putVersion(Document doc){
		// adds a new version to the history
		doc = computeAndSetVersionID(doc);
		versionsHistory.add(doc.cloneDeep());
	}

	public Document getVersion(){
		if(versionsHistory.size() == 0){
			//System.out.println("No rollbacks");
			return null;
		}
		int lastVersionIndex = versionsHistory.size() - 1;

		Document document = versionsHistory.get(lastVersionIndex);

		return document.cloneDeep();
	}

	public void setEntireHistory(List<Document> docsHistory){
		this.versionsHistory = docsHistory;
	}

	public List<Document> getEntireHistory(){
		return versionsHistory;
	}

	public void removeVersion(){
		// removes the last version that has been added to the history
		if(versionsHistory.size() == 0){
			System.out.println("No rollbacks");
			return;
		}
		int lastVersionIndex = versionsHistory.size() - 1;

		versionsHistory.remove(lastVersionIndex);
	}

	private Document computeAndSetVersionID(Document doc){
		String id = doc.getVersionID();

		String[] tmp = id.split("versionID ");

		int versionNumber = Integer.parseInt(tmp[1]);
		versionNumber++;

		id = "versionID" + " " + versionNumber;

		doc.setVersionID(id);

		return doc;
	}

/*	private void printList(List<Document> list){
		for(int i=0; i<list.size(); i++){
			System.out.println("List[" + i + "] = " + list.get(i).getVersionID() + "  | " + list.get(i).hashCode());
		}
	}
*/

}