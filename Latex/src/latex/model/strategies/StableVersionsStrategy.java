package latex.model.strategies;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import latex.model.Document;

public class StableVersionsStrategy implements VersionsStrategy{
	private static String PATH = "src/latex/recources/stable_document_versions/";
	private static String FILENAME = "documents.txt";
	private List<Document> versionsHistory = new ArrayList<Document>();

	public StableVersionsStrategy(){
	}

	public void putVersion(Document doc){
		/* With Stable option we will save Document Object List on disk storage,
					by Serialization.
		*/
		doc = computeAndSetVersionID(doc);
		versionsHistory.add(doc.cloneDeep());

		try {
			writeObjectToFile(versionsHistory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Document getVersion(){
		versionsHistory = getEntireHistory();

		if(versionsHistory.size() == 0){
			//System.out.println("No rollbacks");
			return null;
		}

		int lastVersionIndex = versionsHistory.size() - 1;

		Document document = versionsHistory.get(lastVersionIndex);

		return document.cloneDeep();
	}

	public void setEntireHistory(List<Document> docsHistory){
		versionsHistory.addAll(docsHistory);
		try {
			writeObjectToFile(versionsHistory);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Document> getEntireHistory(){
		try {
			versionsHistory = readObjectFromFile();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		return versionsHistory;
	}

	public void removeVersion(){
		try {
			versionsHistory = readObjectFromFile();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}

		if(versionsHistory.size() == 0){
			//System.out.println("No rollbacks");
			return;
		}

		int lastVersionIndex = versionsHistory.size() - 1;
		versionsHistory.remove(lastVersionIndex);
		try {
			writeObjectToFile(versionsHistory);
		} catch (IOException e) {
			e.printStackTrace();
		}
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

	private void writeObjectToFile(List<Document> versionsHistory) throws IOException{
		FileOutputStream fos = new FileOutputStream(PATH + FILENAME);
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(versionsHistory);

		oos.close();
	}

	private List<Document> readObjectFromFile() throws IOException, ClassNotFoundException{
		FileInputStream fos = new FileInputStream(PATH + FILENAME);
		ObjectInputStream ois = new ObjectInputStream(fos);

		List<Document> versionsHistory = (ArrayList<Document>) ois.readObject();

		ois.close();

		return versionsHistory;
	}

	private void printList(List<Document> list){
		for(int i=0; i<list.size(); i++){
			System.out.println("List[" + i + "] = " + list.get(i).getVersionID() + "  | " + list.get(i).hashCode());
		}
	}

}